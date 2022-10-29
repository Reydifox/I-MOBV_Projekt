package sk.stuba.fei.i_mobv_projekt.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.stuba.fei.i_mobv_projekt.parser.PubModel
import sk.stuba.fei.i_mobv_projekt.parser.PubTags

@Entity(tableName = "pub_list")
data class PubModelDatabase(
    @PrimaryKey
    val id: String,
    val name: String?,
    val latitude: String,
    val longitude: String,
    val tags: PubTagsDatabase
)

data class PubTagsDatabase (
    var amenity: String,
    var name: String,
    var opening_hours: String,
    var operator: String,
    var website: String,
    var phone: String
)