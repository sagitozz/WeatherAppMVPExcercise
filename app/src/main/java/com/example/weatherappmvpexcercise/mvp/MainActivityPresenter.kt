package com.example.weatherappmvpexcercise.mvp

import android.content.Context
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import com.example.weatherappmvpexcercise.GlobalApplication

import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(), MainActivityContract.Presenter {
    private val newsModel = Model()

    lateinit var dataItemList : List<DataItem>
    val context : Context = GlobalApplication.getAppContext()

    var latitude : Double = 0.0
    var longitude : Double = 0.0


    override fun loadData() {
            newsModel.modelGetWeather(latitude, longitude)?.enqueue( object : Callback<WeatherResponse?> {
        override fun onResponse(
            call: Call<WeatherResponse?>,
            response: Response<WeatherResponse?>
        ) {
            Log.d(Constants.LOG_TAG, "OnResponse презентера")
            updateUi(response)

        }
                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    fun updateUi (response: Response<WeatherResponse?>) {
        Log.d(Constants.LOG_TAG, "updateUI презентера")

        dataItemList = response.body()?.data ?: emptyList()
        val city : String = response.body()?.city_name.toString()
        view?.updateCity(city)
        view?.updateUi(dataItemList)
        view?.updateCoordinates(latitude, longitude)
    }

     fun getLastLocation() {
        val locationRequest = LocationRequest ()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest,  object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    LocationServices.getFusedLocationProviderClient(context)
                        .removeLocationUpdates(LocationCallback())
                    if (p0 != null) {
                        latitude = p0.lastLocation.latitude
                        longitude = p0.lastLocation.longitude

                    }
                }
            }, Looper.getMainLooper())
    }
}

