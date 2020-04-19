package com.example.weatherappmvpexcercise.mvp

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
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.example.weatherappmvpexcercise.network.dto.WeatherResponse
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    private val newsModel = Model()
    private lateinit var dataItemList: List<DataItem>
    private var recyclerItems: MutableList<DataItem> = arrayListOf()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    lateinit var loationManager: LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.P)
    fun checkLocationAndLoad() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(App.applicationContext())
        loationManager =
            App.applicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (loationManager.isLocationEnabled) {
          getCurrentLocation()
        } else {
            view?.buildGpsAlertDialog()
        }
    }

    fun getCurrentLocation() {
        loationManager =
            App.applicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        view?.showLoader()
        val locationRequest = LocationRequest()
        locationRequest.interval = Constants.LOCATION_REQUEST_INTERVAL
        locationRequest.fastestInterval = Constants.LOCATION_REQUEST_FASTEST_INTERVAL
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.getFusedLocationProviderClient(App.applicationContext())
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                //todo
                // и вообще вынеси все это в отдельный метод(Все что от object : LocationCallback() и до , Looper.getMainLooper())
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(App.applicationContext())
                        .removeLocationUpdates(LocationCallback())
                    if (locationResult != null) {
                        latitude = locationResult.lastLocation.latitude
                        longitude = locationResult.lastLocation.longitude
                        loadData()
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun loadData() {
        newsModel.modelGetWeather(latitude, longitude)
            ?.enqueue(object : Callback<WeatherResponse?> {
                override fun onResponse(
                    call: Call<WeatherResponse?>,
                    response: Response<WeatherResponse?>
                ) {
                    Log.d(Constants.LOG_TAG, "OnResponse презентера")
                    updateUi(response)
                    view?.hideLoader()
                }

                override fun onFailure(call: Call<WeatherResponse?>, t: Throwable) {
                    view?.onError()
                }
            })
    }

    fun updateUi(response: Response<WeatherResponse?>) {
        Log.d(Constants.LOG_TAG, "updateUI презентера")
        dataItemList = response.body()?.data!!
        val city: String = response.body()?.city_name.toString()
        view?.updateCity(city)
        prepareItemsForRecycler()
        view?.updateUi(recyclerItems)
        view?.updateCoordinates(latitude, longitude)
    }

    private fun prepareItemsForRecycler() {
        for (item in dataItemList) {
            val elementIndex = dataItemList.indexOf(item)
            val result = elementIndex % Constants.DIVIDER_FOR_GETTING_NEXT_DAY
            if (result == 0) {
                recyclerItems.add(item)
            }
        }
    }
}

