package sk.stuba.fei.i_mobv_projekt.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import sk.stuba.fei.i_mobv_projekt.parser.PubExtension
import sk.stuba.fei.i_mobv_projekt.parser.PubModel
import sk.stuba.fei.i_mobv_projekt.parser.PubTags

@Entity(tableName = "pub_list")
data class PubModelDatabase(
    @PrimaryKey
    val id: String,
    val latitude: String,
    val longitude: String,
    var amenity: String?,
    var name: String?,
    var opening_hours: String?,
    var operator: String?,
    var website: String?,
    var phone: String?
)

fun List<PubModelDatabase>.asDomainModel(): PubExtension {
    val list = map {
        PubModel(
            id = it.id,
            lat = it.latitude,
            lon = it.longitude,
            tags = PubTags(
                amenity = it.amenity,
                name = it.name,
                opening_hours = it.opening_hours,
                operator = it.operator,
                website = it.website,
                phone = it.phone
            )
        )
    }
    return PubExtension(list as ArrayList<PubModel>)
}