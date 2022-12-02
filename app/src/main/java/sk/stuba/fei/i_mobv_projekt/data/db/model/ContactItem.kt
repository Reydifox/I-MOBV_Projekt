package sk.stuba.fei.i_mobv_projekt.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class ContactItem (
    @PrimaryKey val uid: String,
    val name: String,
    val bar_id: String,
    val bar_name: String? = null,
    val time: String? = null,
    val bar_lat: String? = null,
    val bar_lon: String? = null
){

    override fun toString(): String {
        return "ContactItem(id='$uid', name='$name', bar_id='$bar_id', bar_name='$bar_name', time='$time', bar_lat='$bar_lat', bar_lon='$bar_lon')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ContactItem) return false

        if (uid != other.uid) return false
        if (name != other.name) return false
        if (bar_id != other.bar_id) return false
        if (bar_name != other.bar_name) return false
        if (time != other.time) return false
        if (bar_lat != other.bar_lat) return false
        if (bar_lon != other.bar_lon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uid.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + bar_id.hashCode()
        result = 31 * result + bar_name.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + bar_lat.hashCode()
        result = 31 * result + bar_lon.hashCode()
        return result
    }
}