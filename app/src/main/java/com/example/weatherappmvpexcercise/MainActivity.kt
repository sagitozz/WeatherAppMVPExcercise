package com.example.weatherappmvpexcercise

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherappmvpexcercise.R.layout
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

        onLoadGetLocation()
    }

    @RequiresApi(Build.VERSION_CODES.P)
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
        temperature.text =
            resources.getString(R.string.temperature_view, list.first().appTemp.toInt().toString())
        pressure.text = resources.getString(
            R.string.pressure_view,
            ((list.first().pres) / Constants.PRESSURE_DIVIDER).toInt().toString()
        )
        wind.text = resources.getString(R.string.wind_view, list.first().windSpd.toInt().toString())
        date.text = resources.getString(R.string.date_view, list.first().timestamp)
        humidity.text = resources.getString(R.string.humidity_view, list.first().rh.toString())
        weatherDescription.text = list.first().weather.description
        recyclerInit(list)
    }

    override fun updateCoordinates(latitude: Double, longitude: Double) {
        latText.text = latitude.toString()
        lonText.text = longitude.toString()
    }

    override fun updateCity(city_text: String) {
        city.text = city_text
    }

    @SuppressLint("NewApi")
    private fun onLoadGetLocation() {
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
            mainActivityPresenter.checkLocationAndLoad()
        }
    }

    override fun buildGpsAlertDialog() {
        val builder =
            AlertDialog.Builder(this@MainActivity)
        builder.setTitle(R.string.gps_not_enabled)
            .setMessage(R.string.open_location_settings)
            .setPositiveButton(
                R.string.yes, fun(dialog: DialogInterface, id: Int) {
                    startActivity(
                        Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                    )
                }
            )
            .setNegativeButton(
                R.string.cancel, fun(dialog: DialogInterface, id: Int) {
                    Toast.makeText(this, "PLEASE ENABLE GPS, MTHFCKR", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            )
        val alert = builder.create()
        alert.show()
    }

    override fun onError() {
        temperature.text = getString(R.string.on_error)
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

