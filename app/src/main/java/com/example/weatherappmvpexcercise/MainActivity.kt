package com.example.weatherappmvpexcercise

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.adapters.WeatherRecyclerAdapter
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.View {


    val mainActivityPresenter = MainActivityPresenter()
    private val REQUEST_LOCATION_PERMISSION = 1
    lateinit var loationManager : LocationManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    lateinit var latitudetext : TextView
    lateinit var longitudeText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        latitudetext = findViewById(R.id.lat)
        longitudeText = findViewById(R.id.lon)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(baseContext)
        Log.d (Constants.LOG_TAG, "создан массив для ресайклера")

        loationManager =
            baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mainActivityPresenter.attach(this)
        onLoadGetLocation()
        mainActivityPresenter.getCurrentLocation()



    }

    private fun recyclerInit(items : MutableList<DataItem>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        val weatherRecyclerAdapter = WeatherRecyclerAdapter (items)
        Log.d (Constants.LOG_TAG, "создан адаптер с массивом")
        recyclerView.adapter = weatherRecyclerAdapter

    }

    @SuppressLint("SetTextI18n")
    override fun updateUi(list: MutableList<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")
        temperature.text = list[0].appTemp.toInt().toString() + "°"
        pressure.text = list[0].pres.toInt().toString()
        wind.text = list[0].windSpd.toInt().toString() + " км/ч"
        date.text = list[0].timestamp
        humidity.text = list[0].rh.toString()
        recyclerInit(list)
    }

    override fun updateCoordinates(latitude: Double, longitude: Double) {
        latitudetext.text = latitude.toString()
        longitudeText.text = longitude.toString()
    }

    override fun updateCity(city_text : String) {
        city.text = city_text
    }

    @SuppressLint("NewApi")
    fun onLoadGetLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_LOCATION_PERMISSION
                )
                //  updateCity("ОЧЕЧЛО")
            }
            if (loationManager.isLocationEnabled) {mainActivityPresenter.getCurrentLocation()}
            else {
                getLatestLocation()
                val builder =
                    AlertDialog.Builder(this@MainActivity)
                builder.setTitle(R.string.gps_not_enabled)
                    .setMessage(R.string.open_location_settings)
                    .setPositiveButton(R.string.yes,
                        DialogInterface.OnClickListener { dialog, id ->
                            startActivity(
                                Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                                )
                            )
                        })
                    .setNegativeButton(R.string.cancel,
                        DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                val alert = builder.create()
                alert.show()}
        getLatestLocation()
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults : IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!(requestCode != REQUEST_LOCATION_PERMISSION && grantResults.isEmpty())) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainActivityPresenter.getCurrentLocation()
            }
            else {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
                Log.d(Constants.LOG_TAG, "OnRequestPermissionREsult FALSE")
            }
        }
    }

    fun getLatestLocation () {
        fusedLocationClient.lastLocation
            .addOnSuccessListener(
                this
            ) { location ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    longitudeText.text = location.longitude.toString()
                }
                else {longitudeText.text = "где-то обосрался"}
            }
    }

    override fun onError() {
        temperature.text= getString(R.string.on_error)
    }

    override fun showLoader() {
        TODO("Not yet implemented")
    }

    override fun hideLoader() {
        TODO("Not yet implemented")
    }

    override fun handleError(error: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.detach()
    }
    override fun onResume() {
        super.onResume()
        mainActivityPresenter.getCurrentLocation()
    }



}

