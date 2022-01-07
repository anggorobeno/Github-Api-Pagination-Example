package com.example.anggorobenolukito.utils

data class Resource2<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource2<T> {
            return Resource2(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource2<T> {
            return Resource2(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource2<T> {
            return Resource2(Status.LOADING, data, null)
        }

    }

}