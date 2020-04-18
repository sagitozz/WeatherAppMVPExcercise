package com.example.weatherappmvpexcercise

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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

    //todo private
    val mainActivityPresenter = MainActivityPresenter()

    //todo вынести в companion object
    private val REQUEST_LOCATION_PERMISSION = 1

    //todo этого тут быть не должно
    lateinit var loationManager: LocationManager

    //todo этого тоже
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    //todo убрать и использовать синтетики. https://antonioleiva.com/kotlin-android-extensions/
    lateinit var latitudetext: TextView
    lateinit var longitudeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        latitudetext = findViewById(R.id.lat)
        longitudeText = findViewById(R.id.lon)
        showLoader()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(baseContext)
        Log.d(Constants.LOG_TAG, "создан массив для ресайклера")

        loationManager =
            baseContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mainActivityPresenter.attach(this)
        onLoadGetLocation()
        mainActivityPresenter.getCurrentLocation()
        //todo Этого быть не должно во вью. Это вообще лабуда какая-то, если у тебя
        // getCurrentLocation будет грузить 5 секунд, то лоадер пропалет вообще сразу.
        // Должно быть так: 2 метода во View hildeLoader и showLoader или один с булевой переменной.
        // В презентере перед стартом ты вызваешь метод showLoader после загрузки в колбеке или в цепочке РХ вызываешь hideLoader
        hideLoader()
    }

    private fun recyclerInit(items: MutableList<DataItem>) {
        //todo это можно вынести в xml если тебе не надо его никак настроить
        recyclerView.layoutManager = LinearLayoutManager(this)
        val weatherRecyclerAdapter = WeatherRecyclerAdapter(items)
        Log.d(Constants.LOG_TAG, "создан адаптер с массивом")
        recyclerView.adapter = weatherRecyclerAdapter
    }

    // todo убрать и сделать по человечески=) Внизу описал как
    @SuppressLint("SetTextI18n")
    override fun updateUi(list: MutableList<DataItem>) {
        Log.d(Constants.LOG_TAG, "updateUI View")
        //todo вынести это в стринговые ресурсы. Не должно быть строк во view.
        // А что если ты захочешь сделать локализацию на другой язык?
        // http://androiddevcorner.blogspot.com/2014/08/localized-getstring-with-parameters.html
        // list[0] можно заменить на list.first(), но это уже мелочи.
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

    override fun updateCity(city_text: String) {
        city.text = city_text
    }

    //todo сперва методы жизненного цикла, потом методы ovveride потом приватные! Этот кстати должен быть приватным!!!
    @SuppressLint("NewApi")
    fun onLoadGetLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this@MainActivity,
                Array<String>(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                REQUEST_LOCATION_PERMISSION
            )
        }
        //todo вот этот if тут быть не должен! В презентер
        if (loationManager.isLocationEnabled) {
            mainActivityPresenter.getCurrentLocation()
        } else {
            val builder =
                AlertDialog.Builder(this@MainActivity)
            builder.setTitle(R.string.gps_not_enabled)
                .setMessage(R.string.open_location_settings)
                .setPositiveButton(R.string.yes,
                    //todo обрати внимание на подсказки студии
                    DialogInterface.OnClickListener { dialog, id ->
                        startActivity(
                            Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS
                            )
                        )
                    })
                    //todo обрати внимание на подсказки студии
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            val alert = builder.create()
            alert.show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!(requestCode != REQUEST_LOCATION_PERMISSION && grantResults.isEmpty())) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mainActivityPresenter.getCurrentLocation()
            } else {
                //todo в стринговые ресурсы все выноси!!!
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show()
                Log.d(Constants.LOG_TAG, "OnRequestPermissionREsult FALSE")
            }
        }
    }

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

    override fun onError() {
        temperature.text = getString(R.string.on_error)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    //todo тут упадет! И вообще у тебя немного странно. 2 метода onError, ты уверен что тебе оба нужны?
    override fun handleError(error: String) {
        TODO("Not yet implemented")
    }

    //todo методы жизненного цикла view лучше выносить перед всеми другими методами и в порядке их вызова
    override fun onDestroy() {
        super.onDestroy()
        mainActivityPresenter.detach()
    }

    override fun onResume() {
        super.onResume()
        mainActivityPresenter.getCurrentLocation()
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
//                // TODO: Consider calling
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

}

