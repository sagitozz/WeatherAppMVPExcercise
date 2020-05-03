package com.example.weatherappmvpexcercise

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.R.string
import com.example.weatherappmvpexcercise.adapters.WeatherRecyclerAdapter
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.constants.TimeOfDay.*
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private val mainActivityPresenter = MainActivityPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        mainActivityPresenter.attach(this)

        getLocation()
    }

    override fun onResume() {
        super.onResume()
        mainActivityPresenter.getCurrentLocation()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.detach()
    }

    override fun updateUi(list: List<DataItem>, dataItems: List<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")
        temperatureText.text =
            resources.getString(string.temperature_view, list.first().temp.toInt().toString())
        pressureText.text = resources.getString(
            string.pressure_view,
            ((list.first().pres) / Constants.PRESSURE_DIVIDER).toInt().toString()
        )
        windText.text =
            resources.getString(string.wind_view, list.first().windSpd.toInt().toString())
        reformatAndSetDate(list.first().datetime)
        humidityText.text = resources.getString(string.humidity_view, list.first().rh.toString())
        setCurrentWeatherIcon(list.first().weather.code.toString())
        recyclerInit(list)
        currentTimeText.text = getCurrentDate()
        settingFuturePrognose(dataItems)
    }

    override fun updateCoordinates(latitude: Double, longitude: Double) {
        latText.text = latitude.toString()
        lonText.text = longitude.toString()
    }

    override fun updateCity(city_text: String) {
        cityText.text = city_text
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                mainActivityPresenter.checkLocationAndLoad()
            }
        }
    }

    override fun buildGpsAlertDialog() {
        val builder =
            AlertDialog.Builder(this@MainActivity)
        builder.setTitle(string.gps_not_enabled)
            .setMessage(string.open_location_settings)
            .setPositiveButton(
                string.yes, fun(_: DialogInterface, _: Int) {
                    openNewActivity()
                }
            )
            .setNegativeButton(
                string.cancel, fun(dialog: DialogInterface, _: Int) {
                    showToastAndClose(dialog)
                }
            )
        val alert = builder.create()
        alert.show()
    }

    override fun setCurrentWeatherIcon(string: String) {
        when (string) {
            resources.getString(R.string.broken_clouds) -> {
                glidingIntoCurrent(R.drawable.clouds)
            }
            resources.getString(R.string.overcast_clouds) -> {
                glidingIntoCurrent(R.drawable.clouds)
            }
            resources.getString(R.string.few_clouds) -> {
                glidingIntoCurrent(R.drawable.clouds)
            }
            resources.getString(R.string.clear_sky) -> {
                glidingIntoCurrent(R.drawable.sunny)
            }
            resources.getString(R.string.local_clouds) -> {
                glidingIntoCurrent(R.drawable.clouds)
            }
        }
    }

    override fun setFirstFutureWeatherIcon(string: String) {
        when (string) {
            resources.getString(R.string.broken_clouds) -> {
                glidingIntoFutureFirst(R.drawable.clouds)
            }
            resources.getString(R.string.overcast_clouds) -> {
                glidingIntoFutureFirst(R.drawable.clouds)
            }
            resources.getString(R.string.few_clouds) -> {
                glidingIntoFutureFirst(R.drawable.clouds)
            }
            resources.getString(R.string.clear_sky) -> {
                glidingIntoFutureFirst(R.drawable.sunny)
            }
            resources.getString(R.string.local_clouds) -> {
                glidingIntoFutureFirst(R.drawable.clouds)
            }
        }
    }

    override fun setSecondFutureWeatherIcon(string: String) {
        when (string) {
            resources.getString(R.string.broken_clouds) -> {
                glidingIntoFutureSecond(R.drawable.clouds)
            }
            resources.getString(R.string.overcast_clouds) -> {
                glidingIntoFutureSecond(R.drawable.clouds)
            }
            resources.getString(R.string.few_clouds) -> {
                glidingIntoFutureSecond(R.drawable.clouds)
            }
            resources.getString(R.string.clear_sky) -> {
                glidingIntoFutureSecond(R.drawable.sunny)
            }
            resources.getString(R.string.local_clouds) -> {
                glidingIntoFutureSecond(R.drawable.clouds)
            }
        }
    }
    override fun onError() {
        temperatureText.text = getString(string.on_error)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    private fun recyclerInit(items: List<DataItem>) {
        val weatherRecyclerAdapter = WeatherRecyclerAdapter(items)
        recyclerView.adapter = weatherRecyclerAdapter
    }

    private fun openNewActivity() {
        startActivity(
            Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
        )
    }

    private fun glidingIntoCurrent(drawable: Int) {
        Glide
            .with(this)
            .load(drawable)
            .into(weatherLogo)
    }

    private fun glidingIntoFutureFirst(drawable: Int) {
        Glide
            .with(this)
            .load(drawable)
            .into(firstFutureIcon)

    }
    private fun glidingIntoFutureSecond(drawable: Int) {
        Glide
            .with(this)
            .load(drawable)
            .into(secondFutureIcon)

    }

    private fun showToastAndClose(dialog: DialogInterface) {
        Toast.makeText(this, "PLEASE ENABLE GPS, MTHFCKR", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }

    private fun getCurrentDate () : String {
        val date = Date()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeText = timeFormat.format(date)
        return timeText
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun reformatAndSetDate(date: String) {
        val inputDateFormat =
            SimpleDateFormat(Constants.INPUT_DATE_FORMAT, Locale.ENGLISH)
        val publishedDate: String = date
        val dateNew: Date = inputDateFormat.parse(publishedDate)
        val outputDateFormat =
            SimpleDateFormat(Constants.OUTPUT_DATE_FORMAT, Locale.getDefault())
        val output = outputDateFormat.format(dateNew)
        dateText.text = resources.getString(string.date_view, output)
    }

    private fun settingFuturePrognose(list: List<DataItem>) {
       if (MORNING.array.contains(getCurrentDate().substringBefore(':'))) {
            firstTimeOfDay.text = resources.getString(string.morning_future_temperature_view, list[2].temp.toInt().toString())
            setFirstFutureWeatherIcon((list[2].weather.code.toString()))
            secondTimeOfDay.text = resources.getString(string.evening_future_temperature_view, list[4].temp.toInt().toString())
            setSecondFutureWeatherIcon((list[4].weather.code.toString()))
        }
       if (DAYTIME.array.contains(getCurrentDate().substringBefore(':'))) {
            firstTimeOfDay.text = resources.getString(string.evening_future_temperature_view, list[2].temp.toInt().toString())
            setFirstFutureWeatherIcon((list[2].weather.code.toString()))
            secondTimeOfDay.text = resources.getString(string.night_future_temperature_view, list[4].temp.toInt().toString())
            setSecondFutureWeatherIcon((list[4].weather.code.toString()))
        }
       if (EVENING.array.contains(getCurrentDate().substringBefore(':'))) {
           firstTimeOfDay.text = resources.getString(string.night_future_temperature_view, list[2].temp.toInt().toString())
           setFirstFutureWeatherIcon((list[2].weather.code.toString()))
           secondTimeOfDay.text = resources.getString(string.morning_future_temperature_view, list[4].temp.toInt().toString())
           setSecondFutureWeatherIcon((list[4].weather.code.toString()))
        }
        if (NIGHT.array.contains(getCurrentDate().substringBefore(':'))) {
            firstTimeOfDay.text = resources.getString(string.morning_future_temperature_view, list[2].temp.toInt().toString())
            setFirstFutureWeatherIcon((list[2].weather.code.toString()))
            secondTimeOfDay.text = resources.getString(string.daytime_future_temperature_view, list[4].temp.toInt().toString())
            setSecondFutureWeatherIcon((list[4].weather.code.toString()))
        }

    }

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }

//    fun getLastKnownLoaction(
//        enabledProvidersOnly: Boolean,
//        context: Context
//    ): Location? {
//        val manager =
//            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        var utilLocation: Location? = null
//        val providers =
//            manager.getProviders(enabledProvidersOnly)
//        for (provider in providers) {
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//
//            }
//            utilLocation = manager.getLastKnownLocation(provider)
//            if (utilLocation != null) return utilLocation
//        }
//        return null
//    }


//    fun getLatestLocation () {
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener(
//                this
//            ) { location ->
//                // Got last known location. In some rare situations this can be null.
//                if (location != null) {
//                    longitudeText.text = location.longitude.toString()
//                }
//                else {longitudeText.text = "где-то обосрался"}
//            }
//    }
}

