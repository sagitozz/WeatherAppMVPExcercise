package com.example.weatherappmvpexcercise.network.dto

import com.google.gson.annotations.SerializedName

data class DataItem(

	@field:SerializedName("sunrise")
	val sunrise: String? = null,

	@field:SerializedName("pod")
	val pod: String? = null,

	@field:SerializedName("pres")
	val pres: Double? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("ob_time")
	val obTime: String? = null,

	@field:SerializedName("wind_cdir")
	val windCdir: String? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("wind_spd")
	val windSpd: Double? = null,

	@field:SerializedName("city_name")
	val cityName: String? = null,

	@field:SerializedName("h_angle")
	val hAngle: Double? = null,

	@field:SerializedName("datetime")
	val datetime: String? = null,

	@field:SerializedName("precip")
	val precip: Int? = null,

	@field:SerializedName("weather")
	val weather: Weather? = null,

	@field:SerializedName("station")
	val station: String? = null,

	@field:SerializedName("elev_angle")
	val elevAngle: Double? = null,

	@field:SerializedName("dni")
	val dni: Int? = null,

	@field:SerializedName("lat")
	val lat: Double? = null,

	@field:SerializedName("vis")
	val vis: Double? = null,

	@field:SerializedName("uv")
	val uv: Int? = null,

	@field:SerializedName("temp")
	val temp: Double? = null,

	@field:SerializedName("dhi")
	val dhi: Int? = null,

	@field:SerializedName("ghi")
	val ghi: Int? = null,

	@field:SerializedName("app_temp")
	val appTemp: Double? = null,

	@field:SerializedName("dewpt")
	val dewpt: Double? = null,

	@field:SerializedName("wind_dir")
	val windDir: Int? = null,

	@field:SerializedName("solar_rad")
	val solarRad: Int? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("rh")
	val rh: Int? = null,

	@field:SerializedName("slp")
	val slp: Double? = null,

	@field:SerializedName("snow")
	val snow: Int? = null,

	@field:SerializedName("sunset")
	val sunset: String? = null,

	@field:SerializedName("last_ob_time")
	val lastObTime: String? = null,

	@field:SerializedName("aqi")
	val aqi: Int? = null,

	@field:SerializedName("state_code")
	val stateCode: String? = null,

	@field:SerializedName("wind_cdir_full")
	val windCdirFull: String? = null,

	@field:SerializedName("ts")
	val ts: Int? = null
)