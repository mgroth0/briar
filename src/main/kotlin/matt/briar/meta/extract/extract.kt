package matt.briar.meta.extract

import kotlinx.serialization.Serializable
import matt.briar.meta.MediaAnnotation
import matt.prim.str.prependZeros

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
data class BinnedOrientation(
    val yawBin: YawBin,
    val pitchBin: PitchBin
) : Comparable<BinnedOrientation> {
    companion object {
        private val comparator = compareBy<BinnedOrientation> { it.yawBin }.thenBy { it.pitchBin }
    }

    override fun toString() = "$yawBin$pitchBin"
    override fun compareTo(other: BinnedOrientation): Int {
        return comparator.compare(this, other)
    }
}

private const val ORIENTATION_BIN_NUM_DIGITS = 2

@JvmInline
@Serializable
value class YawBin(val angle: Int) : Comparable<YawBin> {
    private val num get() = angle.prependZeros(ORIENTATION_BIN_NUM_DIGITS)
    override fun toString(): String {
        return when {
            angle == 0 -> "C$num"
            angle > 0  -> "R$num"
            else       -> "L$num"
        }
    }

    override fun compareTo(other: YawBin): Int {
        return angle.compareTo(other.angle)
    }
}

@JvmInline
@Serializable
value class PitchBin( val angle: Int) : Comparable<PitchBin> {
    private val num get() = angle.prependZeros(ORIENTATION_BIN_NUM_DIGITS)
    override fun toString(): String {
        return when {
            angle == 0 -> "C$num"
            angle > 0  -> "U$num"
            else       -> "D$num"
        }
    }

    override fun compareTo(other: PitchBin): Int {
        return angle.compareTo(other.angle)
    }
}


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

