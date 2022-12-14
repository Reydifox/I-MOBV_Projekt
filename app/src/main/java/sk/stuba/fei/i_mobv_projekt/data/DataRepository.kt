package sk.stuba.fei.i_mobv_projekt.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.Navigation
import org.mindrot.jbcrypt.BCrypt
import sk.stuba.fei.i_mobv_projekt.data.api.*
import sk.stuba.fei.i_mobv_projekt.data.db.LocalCache
import sk.stuba.fei.i_mobv_projekt.data.db.model.BarItem
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem
import sk.stuba.fei.i_mobv_projekt.helpers.Config
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.Contact
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.MyLocation
import sk.stuba.fei.i_mobv_projekt.ui.viewmodels.data.NearbyBar
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DataRepository private constructor(
    private val service: RestApi,
    private val cache: LocalCache
){
    private lateinit var contacts: List<ContactItem>

    suspend fun apiUserCreate(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val pwHash = BCrypt.hashpw(password, Config.SALT)
            val resp = service.userCreate(UserCreateRequest(name = name, password = pwHash))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == "-1"){
                        onStatus(null)
                        onError("Name already exists. Choose another.")
                    }else {
                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to sign up, try again later.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Sign up failed, check internet connection")
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Sign up failed, error.")
            onStatus(null)
        }
    }

    suspend fun apiUserLogin(
        name: String,
        password: String,
        onError: (error: String) -> Unit,
        onStatus: (success: UserResponse?) -> Unit
    ) {
        try {
            val pwHash = BCrypt.hashpw(password, Config.SALT)
            val resp = service.userLogin(UserLoginRequest(name = name, password = pwHash))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    if (user.uid == "-1"){
                        onStatus(null)
                        onError("Wrong name or password.")
                    }else {
                        onStatus(user)
                    }
                }
            } else {
                onError("Failed to login, try again later.")
                onStatus(null)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Login failed, check internet connection")
            onStatus(null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Login in failed, error.")
            onStatus(null)
        }
    }

    suspend fun apiBarCheckin(
        bar: NearbyBar,
        onError: (error: String) -> Unit,
        onSuccess: (success: Boolean) -> Unit
    ) {
        try {
            val resp = service.barMessage(BarMessageRequest(bar.id.toString(),bar.name,bar.type,bar.lat,bar.lon))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    onSuccess(true)
                }
            } else {
                onError("Failed to login, try again later.")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Login failed, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Login in failed, error.")
        }
    }

    suspend fun apiBarList(
        onError: (error: String) -> Unit
    ) {
        try {
            val resp = service.barList()
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->

                    val b = bars.map {
                        BarItem(
                            it.bar_id,
                            it.bar_name,
                            it.bar_type,
                            it.lat,
                            it.lon,
                            it.users
                        )
                    }
                    cache.deleteBars()
                    cache.insertBars(b)
                } ?: onError("Failed to load bars")
            } else {
                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load bars, error.")
        }
    }

    suspend fun apiNearbyBars(
        lat: Double, lon: Double,
        onError: (error: String) -> Unit
    ) : List<NearbyBar> {
        var nearby = listOf<NearbyBar>()
        try {
            val q = "[out:json];node(around:250,$lat,$lon);(node(around:250)[\"amenity\"~\"^pub$|^bar$|^restaurant$|^cafe$|^fast_food$|^stripclub$|^nightclub$\"];);out body;>;out skel;"
            val resp = service.barNearby(q)
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->
                    nearby = bars.elements.map {
                        NearbyBar(it.id,it.tags.getOrDefault("name",""), it.tags.getOrDefault("amenity",""),it.lat,it.lon,it.tags).apply {
                            distance = distanceTo(MyLocation(lat,lon))
                        }
                    }
                    nearby = nearby.filter { it.name.isNotBlank() }.sortedBy { it.distance }
                } ?: onError("Failed to load bars")
            } else {
                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load bars, error.")
        }
        return nearby
    }

    suspend fun apiBarDetail(
        id: String,
        onError: (error: String) -> Unit
    ) : NearbyBar? {
        var nearby:NearbyBar? = null
        try {
            val q = "[out:json];node($id);out body;>;out skel;"
            val resp = service.barDetail(q)
            if (resp.isSuccessful) {
                resp.body()?.let { bars ->
                    if (bars.elements.isNotEmpty()) {
                        val b = bars.elements.get(0)
                        nearby = NearbyBar(
                            b.id,
                            b.tags.getOrDefault("name", ""),
                            b.tags.getOrDefault("amenity", ""),
                            b.lat,
                            b.lon,
                            b.tags
                        )
                    }
                } ?: onError("Failed to load bars")
            } else {
                onError("Failed to read bars")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load bars, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load bars, error.")
        }
        return nearby
    }

    fun dbBarsByName() : LiveData<List<BarItem>?> {
        return cache.getBarsByName()
    }

    fun dbBarsByCount() : LiveData<List<BarItem>?> {
        return cache.getBarsByCount()
    }

    suspend fun apiContactList(
        onError: (error: String) -> Unit
    ) {
        try {
            val resp = service.contactList()
            if (resp.isSuccessful) {
                resp.body()?.let { _contacts ->

                    contacts = _contacts.map {
                        ContactItem(
                            it.user_id,
                            it.user_name,
                            it.bar_id,
                            it.bar_name,
                            it.time,
                            it.bar_lat,
                            it.bar_lon
                        )
                    }
                } ?: onError("Failed to load contacts")
            } else {
                onError("Failed to read contacts")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Failed to load contacts, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Failed to load contacts, error.")
        }
    }

    fun dbContacts() : List<ContactItem> {
        return contacts
    }

    suspend fun apiContactAdd(
        contact: Contact,
        onFinish: (finish: String) -> Unit
    ) {
        try {
            val resp = service.contactAdd(ContactRequest(contact.contact.toString()))
            if (resp.isSuccessful) {
                onFinish("${contact.contact} has been successfully added to the friend list")
            } else {
                onFinish("Failed to add new contact, try again later.")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            onFinish("Contact add in failed, error.")
        }
    }

    suspend fun apiContactDelete(
        contact: Contact,
        onError: (error: String) -> Unit,
        onSuccess: (success: Boolean) -> Unit
    ) {
        try {
            val resp = service.contactDelete(ContactRequest(contact.contact.toString()))
            if (resp.isSuccessful) {
                resp.body()?.let { user ->
                    onSuccess(true)
                }
            } else {
                onError("Failed to delete existing contact, try again later.")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            onError("Contact delete failed, check internet connection")
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError("Contact delete in failed, error.")
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(service: RestApi, cache: LocalCache): DataRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: DataRepository(service, cache).also { INSTANCE = it }
            }

        @SuppressLint("SimpleDateFormat")
        fun dateToTimeStamp(date: String): Long {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date)?.time ?: 0L
        }

        @SuppressLint("SimpleDateFormat")
        fun timestampToDate(time: Long): String{
            val netDate = Date(time*1000)
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(netDate)
        }
    }
}