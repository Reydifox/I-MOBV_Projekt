package sk.stuba.fei.i_mobv_projekt.parser

import com.google.gson.annotations.SerializedName

data class PubExtension (
    @SerializedName("elements")
    val elements : ArrayList<PubModel>
)

data class PubModel (
    @SerializedName("id")
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
    var amenity: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("opening_hours", alternate = ["opening_hours:covid19"])
    var opening_hours: String,
    @SerializedName("operator", alternate = ["operator:no operator"])
    var operator: String,
    @SerializedName("website", alternate = ["website:no website"])
    var website: String,
    @SerializedName("phone", alternate = ["phone:no phone"])
    var phone: String
)