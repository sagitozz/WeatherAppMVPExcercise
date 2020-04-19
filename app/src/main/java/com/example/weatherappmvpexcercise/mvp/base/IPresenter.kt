package com.example.weatherappmvpexcercise.mvp.base

interface IPresenter<VIEW : IView> {

    fun attach(view: VIEW)

    fun detach()
}
