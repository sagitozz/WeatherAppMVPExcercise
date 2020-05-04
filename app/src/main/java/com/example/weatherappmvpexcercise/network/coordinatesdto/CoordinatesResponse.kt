
import com.google.gson.annotations.SerializedName


data class CoordinatesResponse (

	@SerializedName("ip") val ip : String,
	@SerializedName("type") val type : String,
	@SerializedName("city") val city : String,
	@SerializedName("latitude") val latitude : Double,
	@SerializedName("longitude") val longitude : Double
)