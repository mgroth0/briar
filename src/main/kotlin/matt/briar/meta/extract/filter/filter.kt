package matt.briar.meta.extract.filter

import matt.briar.meta.ColorSpace.grayscale
import matt.briar.meta.FrameAnnotation
import matt.briar.meta.MediaAnnotation
import matt.briar.meta.Modality.wholeBody
import matt.briar.meta.SensorType.surveillance

fun MediaAnnotation.include(): Boolean {
    return mediaInfo.colorSpace != grayscale
            && sensorInfo.type == surveillance
            && modality == wholeBody
}

fun MediaAnnotation.framesToExtract(): List<FrameAnnotation> {
    val frameAnnotations = detailedAnnotation.completeAnnotation.track.frameAnnotations
    return frameAnnotations.filter {
        it.numModalitiesDetected == 2
    }
        .take(1000) /*take only first 1000 while I deal with http://ffmpeg.org/pipermail/ffmpeg-user/2023-June/056456.html*/
}