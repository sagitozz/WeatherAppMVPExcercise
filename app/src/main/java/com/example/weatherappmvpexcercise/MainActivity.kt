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
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import kotlinx.android.synthetic.main.activity_main.*

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

    override fun updateUi(list: List<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")
        temperatureText.text =
            resources.getString(string.temperature_view, list.first().temp.toInt().toString())
        pressureText.text = resources.getString(
            string.pressure_view,
            ((list.first().pres) / Constants.PRESSURE_DIVIDER).toInt().toString()
        )
        windText.text =
            resources.getString(string.wind_view, list.first().windSpd.toInt().toString())
        dateText.text = resources.getString(string.date_view, list.first().datetime)
        humidityText.text = resources.getString(string.humidity_view, list.first().rh.toString())
        setWeatherIcon(list.first().weather.code.toString())
        recyclerInit(list)
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

    override fun setWeatherIcon(string : String) {
        when (string) {
            resources.getString(R.string.broken_clouds) -> {
                glidingInto(R.drawable.clouds)
            }
            resources.getString(R.string.overcast_clouds) -> {
                glidingInto(R.drawable.clouds)
            }
            resources.getString(R.string.few_clouds) -> {
                glidingInto(R.drawable.clouds)
            }
            resources.getString(R.string.clear_sky) -> {
                glidingInto(R.drawable.sunny)
            }
            resources.getString(R.string.local_clouds) -> {
                glidingInto(R.drawable.clouds)
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

    private fun glidingInto (drawable : Int) {
        Glide
            .with(this)
            .load(drawable)
            .into(weatherLogo)
    }

    private fun showToastAndClose(dialog: DialogInterface) {
        Toast.makeText(this, "PLEASE ENABLE GPS, MTHFCKR", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
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

