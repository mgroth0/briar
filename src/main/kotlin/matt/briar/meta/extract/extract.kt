package matt.briar.meta.extract

import matt.briar.meta.SubjectID
import kotlinx.serialization.Serializable

@Serializable
class ExtractedMetaData(
    val videoMetadataFiles: List<String>
)


@Serializable
class AllExtractedMetaData(
    val videos: List<AllVidMetadata>
)

@Serializable
class AllVidMetadata(
    val file: String,
    val metadata: ExtractedVidMetaData
)

@Serializable
class ExtractedVidMetaData(
    val subject: SubjectID,
    val frames: List<ExtractedFrameMetaData>
)

@Serializable
class ExtractedFrameMetaData(
    val index: Int,
    val body: BoundingBox?,
    val face: BoundingBox?,
    val faceOrientation: Orientation,
)

@Serializable
class BoundingBox(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
)

@Serializable
data class Orientation(
    val yaw: Double,
    val pitch: Double
)

