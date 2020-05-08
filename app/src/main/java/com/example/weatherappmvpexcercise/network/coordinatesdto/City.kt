import com.google.gson.annotations.SerializedName

data class City(

    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("name_ru") val name_ru: String,
    @SerializedName("name_en") val name_en: String
)