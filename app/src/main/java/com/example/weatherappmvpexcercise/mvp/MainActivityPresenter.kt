package com.example.weatherappmvpexcercise.mvp

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.weatherappmvpexcercise.App
import com.example.weatherappmvpexcercise.Utils.Constants
import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.mvp.base.Model
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    private val dataModel = Model()
    private var recyclerItems: MutableList<WeatherDataItem> = mutableListOf()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private lateinit var locationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherResponseList: List<WeatherDataItem>
    private lateinit var cityFromIP: String

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    fun initializeLocationClientAndManager() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.applicationContext())
        locationManager =
            App.applicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun initializeLocationClientAndManagerWithDialog() {
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
            initializeLocationRequest()
        } else {
            showToastAboutIpGeopos()
        }
    }

    private fun showToastAboutIpGeopos() {
        getCoordinatesByIP()
        Toast.makeText(
            App.applicationContext(),
            "Weather will shown from IP geoposition",
            Toast.LENGTH_SHORT
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCurrentLocationFromButton() {
        if (locationManager.isLocationEnabled) {
            initializeLocationRequest()
        }
    }

    fun updateUi(weatherResponse: List<WeatherDataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI презентера")
        val city: String = cityFromIP
        view?.updateCity(city)
        prepareItemsForRecycler()
        view?.updateUi(recyclerItems, weatherResponse)
    }

    override fun loadWeatherData() {
        dataModel.modelGetWeather(latitude, longitude, language)
            .enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    Log.d(Constants.LOG_TAG, "loadWeatherData")
                    weatherResponseList = response.body()!!.data
                    updateUi(weatherResponseList)
                    view?.hideLoader()
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    private fun initializeLocationRequest() {
        locationManager =
            App.applicationContext()
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager
        view?.showLoader()
        val locationRequest = LocationRequest()
        locationRequest.interval = Constants.LOCATION_REQUEST_INTERVAL
        locationRequest.fastestInterval = Constants.LOCATION_REQUEST_FASTEST_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        Log.d(Constants.LOG_TAG, "работаем по GPS")
        LocationServices.getFusedLocationProviderClient(App.applicationContext())
            .requestLocationUpdates(locationRequest, locationCallBack(), Looper.getMainLooper())
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
                    getCityByIP()
                    loadWeatherData()
                }
            }
        }
        return result
    }

    private fun getCoordinatesByIP() {
        dataModel.modelGetCoordinatesByIp()
            .enqueue(object : Callback<CoordinatesResponse?> {
                override fun onResponse(
                    call: Call<CoordinatesResponse?>,
                    response: Response<CoordinatesResponse?>
                ) {
                    weatherResponseCallback(response)
                }

                override fun onFailure(call: Call<CoordinatesResponse?>, t: Throwable) {
                    Log.d(Constants.LOG_TAG, t.toString())
                }
            })
    }

    private fun weatherResponseCallback(response: Response<CoordinatesResponse?>) {
        cityFromIP = response.body()!!.city.nameRu
        latitude = response.body()!!.city.lat
        longitude = response.body()!!.city.lon
        loadWeatherData()
        Log.d(
            Constants.LOG_TAG,
            "сработало определение координат по сети GetCoordinatesByIP"
        )
    }

    private fun getCityByIP() {
        dataModel.modelGetCoordinatesByIp()
            .enqueue(object : Callback<CoordinatesResponse?> {
                override fun onResponse(
                    call: Call<CoordinatesResponse?>,
                    response: Response<CoordinatesResponse?>
                ) {
                    cityFromIP = response.body()!!.city.nameRu
                    Log.d(Constants.LOG_TAG, "сработал getCityByIP")
                }

                override fun onFailure(call: Call<CoordinatesResponse?>, t: Throwable) {
                    Log.d(Constants.LOG_TAG, t.toString())
                }
            })
    }

    private fun prepareItemsForRecycler() {
        recyclerItems.clear()
        for (item in weatherResponseList) {
            val elementIndex = weatherResponseList.indexOf(item)
            val result = elementIndex % Constants.DIVIDER_FOR_GETTING_NEXT_DAY
            if (result == 0) {
                recyclerItems.add(item)
            }
        }
    }

    companion object {
        private const val language: String = "ru"
    }
}

