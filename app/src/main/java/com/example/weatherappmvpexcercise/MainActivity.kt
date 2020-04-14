package com.example.weatherappmvpexcercise

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private val REQUEST_LOCATION_PERMISSION = 1


    lateinit var latitudetext : TextView
    lateinit var longitudeText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        latitudetext = findViewById(R.id.lat)
        longitudeText = findViewById(R.id.lon)
        val mainActivityPresenter = MainActivityPresenter()
        mainActivityPresenter.attach(this)
        mainActivityPresenter.loadData()

        getLocationButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (ContextCompat.checkSelfPermission(
                        applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        Array<String>(2) { android.Manifest.permission.ACCESS_FINE_LOCATION },
                        REQUEST_LOCATION_PERMISSION
                    )
                } else {
                    getCurrentLocation ()
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
            else Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
       var locationRequest = LocationRequest ()
        locationRequest.interval = 1000
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

        temperature.text = list[6].appTemp.toString() + "C"
        pressure.text = list[6].pres.toString()
        wind.text = list[6]?.windSpd.toString()
        date.text = list[6]?.datetime
        humidity.text = list[6].rh.toString()
    }

    override fun updateCity(city_text : String) {
        city.text = city_text
    }

    override fun onError() {
        temperature.text= "ON ERROR"
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
}

