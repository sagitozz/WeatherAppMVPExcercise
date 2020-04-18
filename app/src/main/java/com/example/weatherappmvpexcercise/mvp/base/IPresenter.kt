package com.example.weatherappmvpexcercise.mvp.base
//todo убирай импорт и чаще кликай cntrl+O и cntrl + L
import android.view.View

interface IPresenter<VIEW : IView> {

    fun attach(view: VIEW)

    fun detach()
}
