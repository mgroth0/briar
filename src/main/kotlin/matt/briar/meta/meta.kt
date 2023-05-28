package matt.briar.meta

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.serializer
import matt.prim.str.elementsToString
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

private const val NAMESPACE_MAIN = "http://www.nist.gov/briar/xml/media"
private const val NAMESPACE_FSTD = "http://standards.iso.org/iso-iec/39794/-5"
private const val PREFIX_FSTD = "fstd"
private const val NAMESPACE_VSTD = "http://standards.iso.org/iso-iec/30137/-4"
private const val PREFIX_VSTD = "vstd"
private const val NAMESPACE_MEDIA = NAMESPACE_MAIN
private const val PREFIX_MEDIA = "media"


@Serializable
@XmlSerialName("mediaAnnotation", NAMESPACE_MEDIA, PREFIX_MEDIA)
class MediaAnnotation(
    @XmlElement(true) val description: String? = null,
    @XmlElement(true) val mediaInfo: MediaInfo,
    @XmlElement(true) val modality: Modality,
    val subject: Subject,
    val sensorInfo: SensorInfo,
    val sensorToSubjectInfo: SensorToSubjectInfo,
    val environment: Environment,
    val detailedAnnotation: DetailedAnnotation
)

@Serializable
@XmlSerialName("modality", NAMESPACE_MAIN, "")
enum class Modality {
    wholeBody, face
}

@Serializable
@XmlSerialName("mediaInfo", NAMESPACE_MAIN, "")
data class MediaInfo(
    @XmlElement(true) val mediaPath: String,
    @XmlElement(true) val typeOfMedia: MediaType,
    @XmlElement(true) val colorSpace: ColorSpace,
    @XmlElement(true) val bitsPerPixel: Int,
    @XmlElement(true) val mediaFormat: MediaFormat,
    @XmlElement(true) val resolution: Resolution,
    @XmlElement(true) val collectionTimestamp_gmt: String,
    @XmlElement(true) val videoCodec: VideoCodec,
    @XmlElement(true) val videoFrameRate_fps: Int,
    @XmlElement(true) val videoNumFrames: Int,
    @XmlElement(true) val videoDuration_secs: Double,
)

@Serializable
@XmlSerialName("typeOfMedia", NAMESPACE_MAIN, "")
enum class MediaType {
    video
}

@Serializable
@XmlSerialName("colorSpace", NAMESPACE_MAIN, "")
enum class ColorSpace {
    yuvj420p, grayscale, rgb, yuv420p
}

@Serializable
@XmlSerialName("mediaFormat", NAMESPACE_MAIN, "")
enum class MediaFormat {
    mp4
}

@Serializable
@XmlSerialName("videoCodec", NAMESPACE_MAIN, "")
enum class VideoCodec {
    h264
}


@Serializable
@XmlSerialName("resolution", NAMESPACE_MAIN, "")
data class Resolution(
    @XmlSerialName(
        "width",
        NAMESPACE_FSTD,
        PREFIX_FSTD
    )
    @XmlElement(true) val width: Int,
    @XmlSerialName(
        "height",
        NAMESPACE_FSTD,
        PREFIX_FSTD
    )
    @XmlElement(true) val height: Int
)

@Serializable
@XmlSerialName("subject", NAMESPACE_MAIN, "")
data class Subject(
    @XmlElement(true) val id: SubjectID,
    @XmlElement(true) val subjectPersistentInfo: SubjectPersistentInfo,
    @XmlElement(true) val subjectImageSpecificInfo: SubjectImageSpecificInfo,
    val subjectActivity: SubjectActivity
)

@Serializable
@JvmInline
@XmlSerialName("id", NAMESPACE_MAIN, "")
value class SubjectID(val id: String) {
    override fun toString(): String {
        return id
    }
}


@Serializable
@XmlSerialName("subjectPersistentInfo", NAMESPACE_MAIN, "")
data class SubjectPersistentInfo(
    @XmlElement(true) val yearOfBirth: Int,
    @XmlElement(true) val sex: Sex,
    @XmlElement(true) val height_inches: Int,
    @XmlElement(true) val weight_lbs: Int,
    @XmlElement(true) val race: Race,
    val ethnicity: Ethnicity,
)

@Serializable
@XmlSerialName("sex", NAMESPACE_MAIN, "")
enum class Sex {
    female, male
}

@Serializable
@XmlSerialName("race", NAMESPACE_MAIN, "")
data class Race(
    @XmlElement(true) val label: String,
    @XmlElement(true) val additionalInfo: String? = null,
)

@Serializable
@XmlSerialName("ethnicity", NAMESPACE_MAIN, "")
enum class Ethnicity {
    no, yes
}


@Serializable
@XmlSerialName("subjectImageSpecificInfo", NAMESPACE_MAIN, "")
data class SubjectImageSpecificInfo(
    @XmlElement(true) val attire: Attire,
    val facemask: Facemask? = null,
    val glasses: Glasses? = null,
    val moustache: Moustache? = null,
    val beard: Beard? = null,
    val headCovering: HeadCovering? = null
)

@Serializable
@XmlSerialName("attire", NAMESPACE_MAIN, "")
data class Attire(
    @XmlElement(true) val clothingSet: ClothingSet,
    @XmlElement(true) val description: String? = null,
)

@Serializable
@XmlSerialName("clothingSet", NAMESPACE_MAIN, "")
enum class ClothingSet {
    set1, set2
}


@Serializable
@XmlSerialName("facemask", NAMESPACE_MAIN, "")
class Facemask

@Serializable
@XmlSerialName("glasses", NAMESPACE_MAIN, "")
class Glasses

@Serializable
@XmlSerialName("moustache", NAMESPACE_MAIN, "")
class Moustache

@Serializable
@XmlSerialName("beard", NAMESPACE_MAIN, "")
class Beard

@Serializable
@XmlSerialName("headCovering", NAMESPACE_MAIN, "")
class HeadCovering


@Serializable
@XmlSerialName("subjectActivity", NAMESPACE_MAIN, "")
class SubjectActivity(
    val actvitiy: Activity
)


@Serializable
@XmlSerialName("activity", NAMESPACE_MAIN, "")
enum class Activity {
    randomWalk, structuredWalk, standing
}


@Serializable
@XmlSerialName("sensorInfo", NAMESPACE_MAIN, "")
data class SensorInfo(
    @XmlElement(true) val id: String,
    @XmlElement(true) val type: SensorType,
    @XmlElement(true) val model: String,
    @XmlElement(true) val manufacturer: String,
    @XmlElement(true) val captureSpectrum: String,
    @XmlElement(true) val focalLength: FocalLength,
    @XmlElement(true) val sensorInfoFile: String? = null,
)

@Serializable
@XmlSerialName("type", NAMESPACE_MAIN, "")
enum class SensorType {
    surveillance, specialized, aerial
}

@Serializable
@XmlSerialName("focalLength", NAMESPACE_MAIN, "")
class FocalLength(
    @XmlSerialName(
        "MinLength",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true) val MinLength: Double,
    @XmlSerialName(
        "MaxLength",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true) val MaxLength: Double
)

///*A primitive written as TEXT will be text content only, but nate that there are only few cases where this is valid.*/
//@SeeURL("https://github.com/pdvrieze/xmlutil")
//interface UsesXmlTextContentAsProperty {
//    val TEXT: String
//}
//
//@Serializable
//@XmlSerialName("sensorInfoFile", NAMESPACE_MAIN, "")
//data class SensorInfoFile(
//    override val TEXT: String = ""
//) : UsesXmlTextContentAsProperty


@Serializable
@XmlSerialName("sensorToSubjectInfo", NAMESPACE_MAIN, "")
data class SensorToSubjectInfo(
    @XmlElement(true) val sensorToSubjectDistance_meters: Int? = null,
    @XmlElement(true) val sensorYawAngle: Int? = null,
    @XmlElement(true) val sensorElevation: Double? = null,
)


@Serializable
@XmlSerialName("environment", NAMESPACE_MAIN, "")
data class Environment(
    @XmlElement(true) val collectionId: String,
    val location: Location,
    val weatherCondition: WeatherCondition,
    val atmosphericCondition: AtmosphericCondition
)


@Serializable
@XmlSerialName("location", NAMESPACE_MAIN, "")
data class Location(
    @XmlElement(true) val name: String,
    val venue: LocationVenue,
    @XmlElement(true) val venueDescription: String
)

@Serializable
@XmlSerialName("venue", NAMESPACE_MAIN, "")
enum class LocationVenue {
    outdoor
}

@Serializable
@XmlSerialName("weatherCondition", NAMESPACE_MAIN, "")
enum class WeatherCondition {
    unknown
}


class NullableIntSerializer : KSerializer<Int?> {
    override val descriptor: SerialDescriptor
        get() = Int::class.serializer().descriptor.nullable

    override fun deserialize(decoder: Decoder): Int? {
        return decoder.decodeString().takeIf { it.isNotEmpty() }?.toInt()
    }

    override fun serialize(encoder: Encoder, value: Int?) {
        encoder.encodeString(value?.toString() ?: "")
    }

}

class NullableDoubleSerializer : KSerializer<Double?> {
    override val descriptor: SerialDescriptor
        get() = Double::class.serializer().descriptor.nullable

    override fun deserialize(decoder: Decoder): Double? {
        return decoder.decodeString().takeIf { it.isNotEmpty() }?.toDouble()
    }

    override fun serialize(encoder: Encoder, value: Double?) {
        encoder.encodeString(value?.toString() ?: "")
    }

}


@Serializable
@XmlSerialName("atmosphericCondition", NAMESPACE_MAIN, "")
class AtmosphericCondition(
    @XmlElement(true) val cn2: CN2? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val temperature: Double? = null,
    @Serializable(with = NullableIntSerializer::class)
    @XmlElement(true) private val windChill: Int? = null,
    @Serializable(with = NullableIntSerializer::class)
    @XmlElement(true) private val heatIndex: Int? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val dewPoint: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val relHumidity: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val windSpeedInstant: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val windDirInstant: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val barometricPress: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val precipitation: Double? = null,
    @Serializable(with = NullableDoubleSerializer::class)
    @XmlElement(true) val solarLoading: Double? = null,
)

@JvmInline
@Serializable
@XmlSerialName("cn2", NAMESPACE_MAIN, "")
value class CN2(val raw: String)


@Serializable
@XmlSerialName("detailedAnnotation", NAMESPACE_MEDIA, PREFIX_MEDIA)
class DetailedAnnotation(
    val completeAnnotation: CompleteAnnotation
)

@Serializable
@XmlSerialName("completeAnnotation", NAMESPACE_MEDIA, PREFIX_MEDIA)
class CompleteAnnotation(
    val description: AnnotationDescription,
    val track: Track
)

@Serializable
@XmlSerialName("Description", NAMESPACE_VSTD, PREFIX_VSTD)
enum class AnnotationDescription {
    WB_tracked_face_merged, WB_face_raw_detections
}


@Serializable
@XmlSerialName("Track", NAMESPACE_VSTD, PREFIX_VSTD)
class Track(
    @XmlSerialName(
        "TrackBegin",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true) val begin: Int,
    @XmlSerialName(
        "TrackEnd",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val end: Int,
    val annotationSemantics: AnnotationSemantics,
    val objectInfo: ObjectInfo,
    val frameAnnotations: List<FrameAnnotation>
)

@Serializable
@XmlSerialName(
    "AnnotationSemantics",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class AnnotationSemantics {
    oneFrame
}


@Serializable
@XmlSerialName(
    "ObjectInfo",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class ObjectInfo(
    val role: Role,
    val cooperation: Cooperation,
    val abnormality: Abnormality
)


@Serializable
@XmlSerialName(
    "Role",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class Role {
    targetObject
}


@Serializable
@XmlSerialName(
    "Cooperation",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class Cooperation {
    cooperative
}

@Serializable
@XmlSerialName(
    "Abnormality",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class Abnormality {
    normal
}

@Serializable
@XmlSerialName(
    "FrameAnnotation",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class FrameAnnotation(
    @XmlSerialName(
        "FrameIndex",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val frameIndex: Int,
    @XmlSerialName(
        "NumDetections",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val numDetections: Int,
    val objectAnnotations: List<ObjectAnnotation>
) {
    init {
        require(objectAnnotations.size == numDetections)
        require(numDetections in 0..2)
    }

    val bodyAnnotation by lazy {
        objectAnnotations.firstOrNull { it.modality == matt.briar.meta.BiometricModality.BODY } ?: error(
            "no body modality. Only modalities are: ${
                objectAnnotations.map { it.modality }.elementsToString()
            }"
        )
    }
    val faceAnnotation by lazy {
        objectAnnotations.firstOrNull { it.modality == matt.briar.meta.BiometricModality.FACE } ?: error(
            "no face modality. Only modalities are: ${
                objectAnnotations.map { it.modality }.elementsToString()
            }"
        )
    }

    val numModalitiesDetected get() = objectAnnotations.map { it.modality }.toSet().size

}

@Serializable
@XmlSerialName(
    "ObjectAnnotation",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class ObjectAnnotation(
    @XmlSerialName(
        "ID",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val id: SubjectID,
    @XmlElement(true) val modality: BiometricModality,
    val boundingBox: BoundingBox,
    val poseAngle: PoseAngle? = null
)


@Serializable
@XmlSerialName(
    "BiometricModality",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
@JvmInline
value class BiometricModality(private val value: Int) {
    companion object {
        val BODY = BiometricModality(15)
        val FACE = BiometricModality(1)
//        private val VALUES = listOf(BODY, FACE)
    }

    init {
        require(value == 1 || value == 15)
    }
}

interface UsesMethodAndAlgorithm {
    val method: LocalisationMethod
    val algorithm: LocalisationAlgorithm
}

@Serializable
@XmlSerialName(
    "BoundingBox",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class BoundingBox(
    val boundingBoxCoordinates: BoundingBoxCoordinates,
    val localisationMethod: LocalisationMethod,
    val localisationAlgorithm: LocalisationAlgorithm
) : UsesMethodAndAlgorithm {
    override val method get() = localisationMethod
    override val algorithm get() = localisationAlgorithm
}

@Serializable
@XmlSerialName(
    "boundingBoxCoordinates",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class BoundingBoxCoordinates(
    val leftTopCoordinates: LeftTopCoordinates,
    @XmlSerialName(
        "boxWidth",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val width: Int,
    @XmlSerialName(
        "boxHeight",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val height: Int
)

@Serializable
@XmlSerialName(
    "leftTopCoordinates",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class LeftTopCoordinates(
    @XmlSerialName(
        "x",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val x: Int,
    @XmlSerialName(
        "y",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    @XmlElement(true)
    val y: Int
)


@Serializable
@XmlSerialName(
    "localisationMethod",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class LocalisationMethod {
    automatic
}

@Serializable
@XmlSerialName(
    "localisationAlgorithm",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
enum class LocalisationAlgorithm {
    `BRIAR-TE`
}


@Serializable
@XmlSerialName(
    "PoseAngle",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class PoseAngle(
    val pose: Pose,
    @XmlSerialName(
        "annotationMethod",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    val annotationMethod: LocalisationMethod,
    @XmlSerialName(
        "annotationAlgorithm",
        NAMESPACE_VSTD,
        PREFIX_VSTD
    )
    val annotationAlgorithm: LocalisationAlgorithm
) : UsesMethodAndAlgorithm {
    override val method get() = annotationMethod
    override val algorithm get() = annotationAlgorithm
}


@Serializable
@XmlSerialName(
    "pose",
    NAMESPACE_VSTD,
    PREFIX_VSTD
)
class Pose(
    val yawAngle: YawAngle,
    val pitchAngle: PitchAngle
)

interface AngleBlock {
    val angleValue: Double
}

@Serializable
@XmlSerialName(
    "yawAngleBlock",
    NAMESPACE_FSTD,
    PREFIX_FSTD
)
class YawAngle(
    @XmlSerialName(
        "angleValue",
        NAMESPACE_FSTD,
        PREFIX_FSTD
    )
    @XmlElement(true)
    override val angleValue: Double,
) : AngleBlock


@Serializable
@XmlSerialName(
    "pitchAngleBlock",
    NAMESPACE_FSTD,
    PREFIX_FSTD
)
class PitchAngle(
    @XmlSerialName(
        "angleValue",
        NAMESPACE_FSTD,
        PREFIX_FSTD
    )
    @XmlElement(true)
    override val angleValue: Double,
) : AngleBlock




