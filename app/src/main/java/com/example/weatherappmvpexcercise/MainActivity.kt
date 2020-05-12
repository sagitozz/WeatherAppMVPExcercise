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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherappmvpexcercise.FutureForecastSetter.setFutureForecastText
import com.example.weatherappmvpexcercise.FutureForecastSetter.setFutureIconCode
import com.example.weatherappmvpexcercise.IconReturnerImpl.setIconIntoImageView
import com.example.weatherappmvpexcercise.R.layout
import com.example.weatherappmvpexcercise.R.string
import com.example.weatherappmvpexcercise.Utils.Constants
import com.example.weatherappmvpexcercise.Utils.TimeOfDay
import com.example.weatherappmvpexcercise.Utils.TimeUtils.getCurrentDate
import com.example.weatherappmvpexcercise.Utils.TimeUtils.getCurrentTimeOfDay
import com.example.weatherappmvpexcercise.Utils.TimeUtils.reformatAndSetDate
import com.example.weatherappmvpexcercise.adapters.WeatherRecyclerAdapter
import com.example.weatherappmvpexcercise.di.dataModule
import com.example.weatherappmvpexcercise.mvp.MainActivityContract
import com.example.weatherappmvpexcercise.mvp.MainActivityPresenter
import com.example.weatherappmvpexcercise.network.weatherdto.WeatherDataItem
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MainActivity : AppCompatActivity(), MainActivityContract.View {

    private val presenter : MainActivityPresenter by inject()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        presenter.attach(this)

        GPSbutton.setOnClickListener {
            presenter.initializeLocationClientAndManagerWithDialog()
            presenter.getCurrentLocationFromButton()
        }
        getLocationPermissionOrLoadLocation()
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            presenter.getCurrentLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun updateUi(
        listForRecycler: List<WeatherDataItem>,
        listForMainView: List<WeatherDataItem>
    ) {
        Log.d(Constants.LOG_TAG, "updateUI View")
        temperatureText.text =
            getString(string.temperature_view, listForMainView.first().temp.toInt().toString())
        pressureText.text = getString(
            string.pressure_view,
            ((listForRecycler.first().pres) / Constants.PRESSURE_DIVIDER).toInt().toString()
        )
        windText.text =
            getString(string.wind_view, listForMainView.first().windSpd.toInt().toString())
        dateText.text = getString(string.date_view, reformatAndSetDate(listForMainView))
        humidityText.text = getString(string.humidity_view, listForMainView.first().rh.toString())
        setCurrentWeatherIcon(listForMainView.first().weather.code.toString())
        recyclerInit(listForRecycler)
        currentTimeText.text = getCurrentDate()
        weatherDescriprionText.text = (listForMainView.first().weather.description.toString())
        settingFutureForecast(listForMainView, getCurrentTimeOfDay())
    }

    override fun updateCity(city_text: String) {
        cityText.text = city_text
    }

    private fun getLocationPermissionOrLoadLocation() {
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
                presenter.initializeLocationClientAndManager()
            }
        }
    }

    override fun buildGpsAlertDialog() {
        val builder =
            AlertDialog.Builder(this@MainActivity)
        builder.setTitle(string.gps_not_enabled)
            .setMessage(string.open_location_settings)
            .setPositiveButton(
                string.yes, fun(_, _) {
                    openNewActivity()
                }
            )
            .setNegativeButton(
                string.cancel, fun(dialog, _) {
                    showToastAndClose(dialog)
                }
            )
        val alert = builder.create()
        alert.show()
    }

    override fun setCurrentWeatherIcon(string: String) {
        setIconIntoImageView(string, mainWeatherIcon)
    }

    override fun onError() {
        temperatureText.text = getString(string.on_error)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        inline_screen.visibility = View.GONE
        Log.d(Constants.LOG_TAG, "Show loader")
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        inline_screen.visibility = View.VISIBLE
        Log.d(Constants.LOG_TAG, "Hide loader")
    }

    private fun recyclerInit(items: List<WeatherDataItem>) {
        recyclerView.adapter = WeatherRecyclerAdapter(items)
    }

    private fun openNewActivity() {
        startActivity(
            Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
        )
    }

    private fun showToastAndClose(dialog: DialogInterface) {
        Toast.makeText(this, "Weather will shown from IP geoposition", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }

    private fun settingFutureForecast(list: List<WeatherDataItem>, timeOfDay: TimeOfDay) {
        firstTimeOfDay.text = setFutureForecastText(list, timeOfDay)[0]
        secondTimeOfDay.text = setFutureForecastText(list, timeOfDay)[1]
        setIconIntoImageView(setFutureIconCode(list).first(), firstFutureIcon)
        setIconIntoImageView(setFutureIconCode(list)[1], secondFutureIcon)
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }
}

