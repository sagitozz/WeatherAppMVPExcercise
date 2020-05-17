package com.example.weatherappmvpexcercise.mvp

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.example.weatherappmvpexcercise.App
import com.example.weatherappmvpexcercise.Utils.Constants
import com.example.weatherappmvpexcercise.Utils.Logger.log
import com.example.weatherappmvpexcercise.di.DataService
import com.example.weatherappmvpexcercise.mvp.base.BasePresenter
import com.example.weatherappmvpexcercise.network.coordinatesdto.CoordinatesResponse
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherResponse
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(private val dataService: DataService) :
    BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    private var recyclerItems: MutableList<WeatherDataItem> = mutableListOf()
    private var maxDayTempRecyclerItems: MutableList<Double> = mutableListOf()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val locationManager: LocationManager =
        App.applicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var weatherResponseList: List<WeatherDataItem>
    private lateinit var cityFromIP: String

    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.P)
    fun initializeLocationClientAndManager() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.applicationContext())
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun initializeLocationClientAndManagerWithDialog() {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(App.applicationContext())

        if (!locationManager.isLocationEnabled) {
            view?.buildGpsAlertDialog()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCurrentLocationWithPermission() {
        if (locationManager.isLocationEnabled) {
            initializeLocationRequest()
        } else {
            showToastAboutIpGeolocation()
        }
    }

    fun getForecastWithoutPermission() {
        view?.showLoader()
        getCoordinatesByIP()
    }

    private fun showToastAboutIpGeolocation() {
        getCoordinatesByIP()
        view?.showToastAboutIpGeolocation()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getCurrentLocationFromButton() {
        if (locationManager.isLocationEnabled) {
            initializeLocationRequest()
        }
    }

    fun updateUi(weatherResponse: List<WeatherDataItem>) {
        log("updateUI презентера")
        view?.updateCity(cityFromIP)
        prepareItemsForRecycler()
        view?.updateUi(recyclerItems, weatherResponse)
    }

    override fun loadWeatherData() {
        dataService.getWeather(latitude, longitude, language)
            .enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    log("loadWeatherData")
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
        view?.showLoader()
        val locationRequest = LocationRequest()
        locationRequest.interval = Constants.LOCATION_REQUEST_INTERVAL
        locationRequest.fastestInterval = Constants.LOCATION_REQUEST_FASTEST_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        log("работаем по GPS")
        LocationServices.getFusedLocationProviderClient(App.applicationContext())
            .requestLocationUpdates(locationRequest, locationCallBack(), Looper.getMainLooper())
    }

    private fun locationCallBack(): LocationCallback {
        return object : LocationCallback() {
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
    }

    private fun getCoordinatesByIP() {
        dataService.modelGetCoordinatesByIp()
            .enqueue(object : Callback<CoordinatesResponse> {
                override fun onResponse(
                    call: Call<CoordinatesResponse?>,
                    response: Response<CoordinatesResponse>
                ) {
                    coordinatesResponseCallback(response)
                }

                override fun onFailure(call: Call<CoordinatesResponse>, t: Throwable) {
                    log(t.toString())
                }
            })
    }

    private fun coordinatesResponseCallback(response: Response<CoordinatesResponse>) {
        cityFromIP = response.body()!!.city.nameRu
        latitude = response.body()!!.city.lat
        longitude = response.body()!!.city.lon
        loadWeatherData()
        log("сработало определение координат по сети GetCoordinatesByIP")
    }

    private fun getCityByIP() {
        dataService.modelGetCoordinatesByIp()
            .enqueue(object : Callback<CoordinatesResponse> {
                override fun onResponse(
                    call: Call<CoordinatesResponse>,
                    response: Response<CoordinatesResponse>
                ) {
                    cityFromIP = response.body()!!.city.nameRu
                    log("сработал getCityByIP")
                }

                override fun onFailure(call: Call<CoordinatesResponse>, t: Throwable) {
                    log(t.toString())
                }
            })
    }

    private fun prepareItemsForRecycler() {
        recyclerItems.clear()
        maxDayTempRecyclerItems.clear()
        var i = 0
        var maxTemp1: Double = -60.0
        var maxTemp2: Double = -60.0
        var maxTemp3: Double = -60.0
        var maxTemp4: Double = -60.0
        var maxTemp5: Double = -60.0
        val day1 = weatherResponseList.subList(0, 7)
        val day2 = weatherResponseList.subList(8, 15)
        val day3 = weatherResponseList.subList(16, 23)
        val day4 = weatherResponseList.subList(24, 31)
        val day5 = weatherResponseList.subList(32, 39)
        for (item in day1) {
            if (item.appTemp > maxTemp1)
                maxTemp1 = item.appTemp
        }
        for (item in day2) {
            if (item.appTemp > maxTemp2)
                maxTemp2 = item.appTemp
        }
        for (item in day3) {
            if (item.appTemp > maxTemp3)
                maxTemp3 = item.appTemp
        }
        for (item in day4) {
            if (item.appTemp > maxTemp4)
                maxTemp4 = item.appTemp
        }
        for (item in day5) {
            if (item.appTemp > maxTemp5)
                maxTemp5 = item.appTemp
        }
        maxDayTempRecyclerItems = mutableListOf(maxTemp1, maxTemp2, maxTemp3, maxTemp4, maxTemp5)

        for (item in weatherResponseList) {

            val elementIndex = weatherResponseList.indexOf(item)
            val result = elementIndex % Constants.DIVIDER_FOR_GETTING_NEXT_DAY
            if (result == 0) {
                recyclerItems.add(item)
            }
        }
        for (item in recyclerItems) {
            item.appTemp = maxDayTempRecyclerItems[i]
            i += 1
        }
    }

    companion object {
        private const val language: String = "ru"
    }
}

