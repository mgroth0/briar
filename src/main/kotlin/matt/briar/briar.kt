package matt.briar

import kotlinx.serialization.Serializable
import matt.briar.meta.ColorSpace.grayscale
import matt.briar.meta.MediaAnnotation
import matt.briar.meta.Modality.wholeBody
import matt.briar.meta.SensorType.surveillance
import matt.briar.meta.SubjectID
import matt.briar.meta.extract.ExtractedFrameMetaData
import matt.briar.orientation.OrientationBinner
import matt.file.MFile
import matt.file.context.ComputeContext
import matt.file.context.ComputeContextFiles
import matt.lang.require.requireEquals
import matt.lang.require.requireNull
import matt.model.data.orientation.BinnedOrientation
import matt.model.data.orientation.PitchBin
import matt.model.data.orientation.YawBin

const val QUERY_DIST = 100
const val GALLERY_DIST = 10

@Serializable
sealed interface BriarExtraction {
    val computeContext: ComputeContext
    val extractName: String
    val briarExtractFolder: MFile get() = computeContext.files.briarExtractsFolder[extractName]
    val briarExtractDataFolder get() = briarExtractFolder["data"]
    val briarExtractMetadataFile get() = briarExtractFolder[ComputeContextFiles.BRIAR_EXTRACT_METADATA_FILE_NAME]
    val cacheFolder get() = computeContext.files.cacheFolder["briar_extracts"][extractName]
    fun shouldInclude(
        video: MediaAnnotation,
        similarityMatrix: SimilarityMatrix?
    ): Boolean

    fun framesToExtract(
        video: MediaAnnotation,
        frames: List<ExtractedFrameMetaData>,
    ): List<ExtractedFrameMetaData>
}

private fun MediaAnnotation.baseVideoFilter() =
    mediaInfo.colorSpace != grayscale
            && sensorInfo.type == surveillance
            && modality == wholeBody
            && (sensorToSubjectInfo.sensorElevation?.let { it < 2.0 } ?: false) /*so image_viewer task is no ambiguous*/

private fun MediaAnnotation.baseFrameFilter(frames: List<ExtractedFrameMetaData>) =
    if (frames.size <= mediaInfo.videoFrameRate_fps) sequenceOf() else
        frames.asSequence().filter {
            it.body != null && it.face != null && it.faceOrientation != null
        }

fun allExtractions(computeContext: ComputeContext) = listOf(
    DNNExtraction(computeContext),
    OnlineExpExtraction(computeContext)
)

@Serializable
class DNNExtraction(override val computeContext: ComputeContext) : BriarExtraction {
    override val extractName = "BRS1_extract"

    override fun shouldInclude(
        video: MediaAnnotation,
        similarityMatrix: SimilarityMatrix?
    ): Boolean {
        requireNull(similarityMatrix)
        return video.baseVideoFilter()
    }


    override fun framesToExtract(
        video: MediaAnnotation,
        frames: List<ExtractedFrameMetaData>,
    ) = video.baseFrameFilter(frames).toList()
    /*accidentally left that 1000 throttle on for the last open mind extraction*/
    /*.take(1000)*/ /*take only first 1000 while I deal with http://ffmpeg.org/pipermail/ffmpeg-user/2023-June/056456.html*/
}

val TARGET_ORIENTATION = BinnedOrientation(YawBin(0), PitchBin(0))

@Serializable
class OnlineExpExtraction(override val computeContext: ComputeContext) : BriarExtraction {
    override val extractName = "BRS1_extract_for_oexp"

    private val distancesToUse = listOf(GALLERY_DIST, QUERY_DIST)
    private val targetOrientation = TARGET_ORIENTATION

    override fun shouldInclude(
        video: MediaAnnotation,
        similarityMatrix: SimilarityMatrix?
    ) = video.baseVideoFilter()
            && video.sensorToSubjectInfo.sensorToSubjectDistance_meters in distancesToUse
            && video.subject.id.id in similarityMatrix!!.ids

    /*removing the camera filter for now, because I'm having trouble finding the frames I need*/
    /*&& video.sensorInfo.model == "Q6215-LE"*/ /*there are only two options for 100, and the other one seems to always be tilted*/

    override fun framesToExtract(
        video: MediaAnnotation,
        frames: List<ExtractedFrameMetaData>,
    ) = video.baseFrameFilter(frames)
        .filter {
            it.faceOrientation!!.confident
                    && OrientationBinner.bin(
                yaw = it.faceOrientation.yaw,
                pitch = it.faceOrientation.pitch
            ) == targetOrientation
        }
        .toList()
}


class SimilarityMatrix(
    val ids: List<String>,
    val corrmat: List<List<Double>>,
    val sets: List<String> /*Clothing sets. I think this doesn't matter since he only checked faces*/
) {
    init {
        requireEquals(ids.size, 157)
        requireEquals(corrmat.size, 157)
        requireEquals(sets.size, 157)
    }
}

@Serializable
class TrialConfiguration(
    val query: SubjectID,
    val distractors: List<SubjectID>
) {
    init {
        requireEquals(distractors.size, 4) {
            "there should be 4 distractors, not ${distractors.size}"
        }
    }
}


interface StimuliSelectionContext {
    val distractorImagesNeeded: Map<SubjectID, Int>
}





