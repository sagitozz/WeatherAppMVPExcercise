import com.google.gson.annotations.SerializedName

class City(

    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("name_ru") val nameRu: String,
    @SerializedName("name_en") val nameEn: String
)