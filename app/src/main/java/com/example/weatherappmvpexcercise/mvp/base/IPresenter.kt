package com.example.weatherappmvpexcercise.mvp.base

import android.view.View

interface IPresenter<VIEW : IView> {

    fun attach(view : VIEW)

    fun detach()
}