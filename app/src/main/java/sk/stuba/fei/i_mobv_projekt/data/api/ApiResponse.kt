package sk.stuba.fei.i_mobv_projekt.data.api

data class UserResponse(
    val uid: String,
    val access: String,
    val refresh: String
)

data class BarListResponse(
    val bar_id: String,
    val bar_name: String,
    val bar_type: String,
    val lat: Double,
    var lon: Double,
    var users: Int
)

data class BarDetailItemResponse(
    val type: String,
    val id: String,
    val lat: Double,
    val lon: Double,
    val tags: Map<String, String>
)

data class BarDetailResponse(
    val elements: List<BarDetailItemResponse>
)

data class ContactListResponse(
    val user_id: String,
    val user_name: String,
    val bar_id: String,
    val bar_name: String,
    val time: String,
    val bar_lat: String,
    val bar_lon: String
)