package com.example.weatherappmvpexcercise.mvp

import android.content.Context
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

class MainActivityPresenter : BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    private val newsModel = Model()
    private lateinit var dataItemList: List<DataItem>
    //todo он тебе не нужен!!! Обращайся напрямую там где над
    val context: Context = GlobalApplication.getAppContext()
    private var recyclerItems: MutableList<DataItem> = arrayListOf()
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    fun getCurrentLocation() {
        val locationRequest = LocationRequest()
        //todo что такое 10000? В константу! 3000 аналогично!
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, object : LocationCallback() {
                //todo
                // и вообще вынеси все это в отдельный метод(Все что от object : LocationCallback() и до , Looper.getMainLooper())
                override fun onLocationResult(locationResult: LocationResult?) {
                    super.onLocationResult(locationResult)
                    LocationServices.getFusedLocationProviderClient(context)
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

