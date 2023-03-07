package com.example.kite.base.network.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


open class ResponseData<T> : ResponseWrapper<T>() {

    @SerializedName("data")
    @Expose
    var data: T? = null

    override fun toString(): String {
        return "com.example.kite.base.network.model.ResponseWrapper{" +
                "data=" + data.toString()
    }

}
