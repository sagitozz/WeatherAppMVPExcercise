package com.example.weatherappmvpexcercise.mvp

import CoordinatesResponse
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherappmvpexcercise.App
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    private val dataModel = Model()
    private lateinit var dataItemList: List<WeatherDataItem>
    private var recyclerItems: MutableList<WeatherDataItem> = arrayListOf()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val language: String = "ru"
    lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherResponse: Response<WeatherResponse?>
    private lateinit var coordinatesResponse: Response<CoordinatesResponse?>

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    fun checkLocationAndLoad() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.applicationContext())
        locationManager =
            App.applicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isLocationEnabled) {
            view?.buildGpsAlertDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCurrentLocation() {

        if (locationManager.isLocationEnabled) {
            locationManager =
                App.applicationContext()
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            view?.showLoader()
            val locationRequest = LocationRequest()
            locationRequest.interval = Constants.LOCATION_REQUEST_INTERVAL
            locationRequest.fastestInterval = Constants.LOCATION_REQUEST_FASTEST_INTERVAL
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            LocationServices.getFusedLocationProviderClient(App.applicationContext())
                .requestLocationUpdates(locationRequest, locationCallBack(), Looper.getMainLooper())
        }

        else {
            dataModel.modelGetCoordinatesByIp()
                ?.enqueue(object : Callback<CoordinatesResponse?>{
                    override fun onResponse(
                        call: Call<CoordinatesResponse?>,
                        response: Response<CoordinatesResponse?>
                    ) {
                        coordinatesResponse = response
                        latitude = coordinatesResponse.body()!!.latitude
                        longitude = coordinatesResponse.body()!!.longitude
                        loadWeatherData()
                    }

                    override fun onFailure(call: Call<CoordinatesResponse?>, t: Throwable) {
                        Log.d(Constants.LOG_TAG, "COORDINATES RESPONSE ON FAILUREы")
                    }
                })
        }
    }

    override fun loadWeatherData() {
        dataModel.modelGetWeather(latitude, longitude, language)
            ?.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    Log.d(Constants.LOG_TAG, "OnResponse презентера")
                    weatherResponse = response
                    updateUi(weatherResponse, coordinatesResponse)
                        view?.hideLoader()
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    private fun locationCallBack(): LocationCallback {
        val result = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                LocationServices.getFusedLocationProviderClient(App.applicationContext())
                    .removeLocationUpdates(LocationCallback())
                if (locationResult != null) {
                    latitude = locationResult.lastLocation.latitude
                    longitude = locationResult.lastLocation.longitude
                    loadWeatherData()
                }
            }
        }
        return result
    }

    fun updateUi(weatherResponse: Response<WeatherResponse?>, coordinatesResponse: Response<CoordinatesResponse?> ) {
        Log.d(Constants.LOG_TAG, "updateUI презентера")
        dataItemList = weatherResponse.body()?.data!!
        val city: String = coordinatesResponse.body()?.city.toString()
        view?.updateCity(city)
        prepareItemsForRecycler()
        view?.updateUi(recyclerItems, dataItemList)
    }

    private fun prepareItemsForRecycler() {
        recyclerItems.clear()
        for (item in dataItemList) {
            val elementIndex = dataItemList.indexOf(item)
            val result = elementIndex % Constants.DIVIDER_FOR_GETTING_NEXT_DAY
            if (result == 0) {
                recyclerItems.add(item)
            }
        }
    }
}

