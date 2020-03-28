package com.example.weatherappmvpexcercise.mvp.base

interface IView {

    fun showLoader()

    fun hideLoader()

    fun handleError(error: String)

}