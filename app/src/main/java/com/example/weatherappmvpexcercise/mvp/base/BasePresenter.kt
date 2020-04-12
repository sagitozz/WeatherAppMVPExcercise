package com.example.weatherappmvpexcercise.mvp.base

abstract class BasePresenter <VIEW : IView> : IPresenter<VIEW>{

    protected var view : VIEW? = null;

    override fun attach(view: VIEW) {
        this.view = view
    }

    override fun detach() {

    }



}