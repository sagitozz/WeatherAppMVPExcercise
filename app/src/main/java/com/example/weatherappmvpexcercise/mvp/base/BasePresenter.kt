package com.example.weatherappmvpexcercise.mvp.base

abstract class BasePresenter<VIEW : IView> : IPresenter<VIEW> {

    var view: VIEW? = null;

    override fun attach(view: VIEW) {
        this.view = view
    }

    //todo тут должно быть view = null.
    // Еще тут отписываешься от всяки подписко (compositeDisposable, корутины и т.д.)
    override fun detach() {

    }

//todo пробелы убирай все лишние!!!
}
