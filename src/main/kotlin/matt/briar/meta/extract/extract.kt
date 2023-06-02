package matt.briar.meta.extract

import kotlinx.serialization.Serializable
import matt.briar.meta.Inches
import matt.briar.meta.Pounds
import matt.briar.meta.Sex
import matt.briar.meta.SubjectID

@Serializable
class ExtractedMetaData(
    val videos: List<ExtractedVideoMetaData>
)

@Serializable
class ExtractedVideoMetaData(
    val framesMetaDataFile: String,
    val subject: ExtractedSubject
)

@Serializable
class ExtractedFramesMetaData(
    val frames: List<ExtractedFrameMetaData>
)

@Serializable
class ExtractedSubject(
    val id: SubjectID,
    val sex: Sex,
    val height: Inches,
    val weight: Pounds
)

@Serializable
class ExtractedFrameMetaData(
    val index: Int,
    val body: Box?,
    val face: Box?,
    val faceOrientation: Orientation,
    val crop: Box?
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
    val pitch: Double
)

