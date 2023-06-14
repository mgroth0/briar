package matt.briar.orientation

import matt.briar.meta.extract.BinnedOrientation
import matt.briar.meta.extract.PitchBin
import matt.briar.meta.extract.YawBin

object OrientationBinner {
    const val YAW_RADIUS = 2.0
    const val PITCH_RADIUS = 2.0
    private const val YAW_MIDDLE_ANGLE = 0.0
    private const val YAW_SIDE_ANGLE = 45.0
    private const val YAW_VERY_SIDE_ANGLE = 90.0
    private const val PITCH_MIDDLE_ANGLE = 0.0
    private const val PITCH_UP_OR_DOWN_ANGLE = 30.0
    private val yawMiddle = (-YAW_RADIUS - YAW_MIDDLE_ANGLE)..(YAW_RADIUS - YAW_MIDDLE_ANGLE)
    private val yawLeft = (-YAW_RADIUS - YAW_SIDE_ANGLE)..(YAW_RADIUS - YAW_SIDE_ANGLE)
    private val yawRight = (-YAW_RADIUS + YAW_SIDE_ANGLE)..(YAW_RADIUS + YAW_SIDE_ANGLE)
    private val yawVeryLeft = (-YAW_RADIUS - YAW_VERY_SIDE_ANGLE)..(YAW_RADIUS - YAW_VERY_SIDE_ANGLE)
    private val yawVeryRight = (-YAW_RADIUS + YAW_VERY_SIDE_ANGLE)..(YAW_RADIUS + YAW_VERY_SIDE_ANGLE)
    private val pitchMiddle = (-PITCH_RADIUS - PITCH_MIDDLE_ANGLE)..(PITCH_RADIUS - PITCH_MIDDLE_ANGLE)
    private val pitchUp = (-PITCH_RADIUS + PITCH_UP_OR_DOWN_ANGLE)..(PITCH_RADIUS + PITCH_UP_OR_DOWN_ANGLE)
    private val pitchDown = (-PITCH_RADIUS - PITCH_UP_OR_DOWN_ANGLE)..(PITCH_RADIUS - PITCH_UP_OR_DOWN_ANGLE)

    fun bin(yaw: Double, pitch: Double): BinnedOrientation? {
        val pitchPart = when (pitch) {
            in pitchMiddle -> PitchBin(0)
            in pitchUp     -> PitchBin(PITCH_UP_OR_DOWN_ANGLE.toInt())
            in pitchDown   -> PitchBin(-PITCH_UP_OR_DOWN_ANGLE.toInt())
            else           -> return null
        }
        val yawPart = when (yaw) {
            in yawMiddle    -> YawBin(0)
            in yawLeft      -> YawBin(-YAW_SIDE_ANGLE.toInt())
            in yawVeryLeft  -> YawBin(-YAW_VERY_SIDE_ANGLE.toInt())
            in yawRight     -> YawBin(YAW_SIDE_ANGLE.toInt())
            in yawVeryRight -> YawBin(YAW_VERY_SIDE_ANGLE.toInt())
            else            -> return null
        }
        return BinnedOrientation(yawPart, pitchPart)
    }
}

