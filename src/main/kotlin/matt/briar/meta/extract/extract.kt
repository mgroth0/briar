package matt.briar.meta.extract

import kotlinx.serialization.Serializable
import matt.briar.meta.MediaAnnotation
import matt.model.data.orientation.BinnedOrientation

@Serializable
class ExtractedMetaData(
    val videos: List<ExtractedVideoMetaData>
)

@Serializable
class ExtractedVideoMetaData(
    val vidFile: String,
    val framesMetaDataFile: String?,
    val metadata: MediaAnnotation
)

@Serializable
class ExtractedFramesMetaData(
    val frames: List<ExtractedFrameMetaData>
)


@Serializable
class ExtractedFrameMetaData(
    val index: Int,
    val body: Box?,
    val face: Box?,
    val faceOrientation: Orientation?,
    var crop: Box?
) {
    fun requireHasBothBoundingBoxes() = ExtractedFrameMetaDataWithBothBoundingBoxes(
        index = index,
        body = body!!,
        face = face!!,
        faceOrientation = faceOrientation!!,
        crop = crop
    )
}

class ExtractedFrameMetaDataWithBothBoundingBoxes(
    val index: Int,
    val body: Box,
    val face: Box,
    val faceOrientation: Orientation,
    var crop: Box?
)

data class ProcessedFrameMetadata(
    val orientation: BinnedOrientation?,
    val vid: MediaAnnotation
)


@Serializable
class Box(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

@Serializable
data class Orientation(
    val yaw: Double,
    val pitch: Double,
    var confident: Boolean = true
)

