package com.example.weatherappmvpexcercise

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.constants.Constants
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.dto.DataItem
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.View {


    val mainActivityPresenter = MainActivityPresenter()
    private val REQUEST_LOCATION_PERMISSION = 1


    lateinit var latitudetext : TextView
    lateinit var longitudeText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val locationManagerForGPS  =
            baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        latitudetext = findViewById(R.id.lat)
        longitudeText = findViewById(R.id.lon)

        mainActivityPresenter.attach(this)
        mainActivityPresenter.getLastLocation()
        mainActivityPresenter.loadData()

        getLocationButton.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.P)
            override fun onClick(v: View?) {

                if (ContextCompat.checkSelfPermission(
                        applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_LOCATION_PERMISSION
                    )

                } else if (locationManagerForGPS.isLocationEnabled)   {

                    getCurrentLocation ()
                    updateCity("ОЧКО")


                }
                else {
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
            }
        })
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults : IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!(requestCode != REQUEST_LOCATION_PERMISSION && grantResults.isEmpty())) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }

            else Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
       var locationRequest = LocationRequest ()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 3000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.getFusedLocationProviderClient(baseContext)
            .requestLocationUpdates(locationRequest,  object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult?) {
                    super.onLocationResult(p0)
                    LocationServices.getFusedLocationProviderClient(baseContext)
                        .removeLocationUpdates(LocationCallback())
                    if (p0 != null) {
                        var latitude = p0.lastLocation.latitude
                        var longitude = p0.lastLocation.longitude


                        latitudetext.text = latitude.toString()
                        longitudeText.text = longitude.toString()


                    }
                }
            }, Looper.getMainLooper())
    }

    override fun updateUi(list: List<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")

        temperature.text = list[6].appTemp.toInt().toString() + "°"
        pressure.text = list[6].pres.toString()
        wind.text = list[6]?.windSpd.toString()
        date.text = list[6]?.datetime
        humidity.text = list[6].rh.toString()


    }

    override fun updateCoordinates(latitude: Double, longitude: Double) {
        latitudetext.text = latitude.toString()
        longitudeText.text = longitude.toString()
    }

    override fun updateCity(city_text : String) {
        city.text = city_text
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
}

