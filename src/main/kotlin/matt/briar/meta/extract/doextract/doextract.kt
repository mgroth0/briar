package matt.briar.meta.extract.doextract

import matt.briar.meta.FrameAnnotation
import matt.briar.meta.extract.Box
import matt.briar.meta.extract.ExtractedFrameMetaData
import matt.briar.meta.extract.Orientation


fun FrameAnnotation.extractedFrameMetadata() = ExtractedFrameMetaData(
    index = frameIndex,
    body = bodyAnnotation?.let {
        Box(
            x = it.boundingBox.boundingBoxCoordinates.leftTopCoordinates.x,
            y = it.boundingBox.boundingBoxCoordinates.leftTopCoordinates.y,
            width = it.boundingBox.boundingBoxCoordinates.width,
            height = it.boundingBox.boundingBoxCoordinates.height
        )
    },
    face = faceAnnotation?.let {
        Box(
            x = it.boundingBox.boundingBoxCoordinates.leftTopCoordinates.x,
            y = it.boundingBox.boundingBoxCoordinates.leftTopCoordinates.y,
            width = it.boundingBox.boundingBoxCoordinates.width,
            height = it.boundingBox.boundingBoxCoordinates.height
        )
    },
    faceOrientation = faceAnnotation?.let {
        Orientation(
            yaw = it.poseAngle!!.pose.yawAngle.angleValue,
            pitch = it.poseAngle.pose.pitchAngle.angleValue
        )
    },
    crop = null
)