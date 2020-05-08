import com.google.gson.annotations.SerializedName


data class CoordinatesResponse(
    @SerializedName("ip") val ip: String,
    @SerializedName("city") val city: City,
    @SerializedName("error") val error: String,
    @SerializedName("request") val request: Int,
    @SerializedName("created") val created: String,
    @SerializedName("timestamp") val timestamp: Int
)