package sk.stuba.fei.i_mobv_projekt.parser

import com.google.gson.annotations.SerializedName
import sk.stuba.fei.i_mobv_projekt.database.PubModelDatabase

data class PubExtension (
    @SerializedName("documents")
    val elements : ArrayList<PubModel>
){
    fun remove(position : Int){
        elements.removeAt(position)
    }
}

data class PubModel (
    @SerializedName("_id")
    val id : String,
    @SerializedName("lat")
    val lat : String,
    @SerializedName("lon")
    val lon : String,
    @SerializedName("tags")
    val tags : PubTags
)

data class PubTags (
    @SerializedName("amenity")
    var amenity: String?,
    @SerializedName("name")
    var name: String? = "Unknown",
    @SerializedName("opening_hours", alternate = ["opening_hours:covid19"])
    var opening_hours: String? = "none",
    @SerializedName("operator")
    var operator: String? = "no operator",
    @SerializedName("website")
    var website: String? = "no website",
    @SerializedName("phone")
    var phone: String? = "no phone"
)

fun PubExtension.asDatabaseModel(): List<PubModelDatabase> {
    return elements.map {
        PubModelDatabase(
            id = it.id,
            latitude = it.lat,
            longitude = it.lon,
            amenity = it.tags.amenity,
            name = it.tags.name,
            opening_hours = it.tags.opening_hours,
            operator = it.tags.operator,
            website = it.tags.website,
            phone = it.tags.phone
        )
    }
}