package b12app.vyom.com.bookmybus.data.remote

import b12app.vyom.com.bookmybus.utils.baseUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    var retrofit:Retrofit?=null


    fun getRetrofitInstance(): Retrofit? {
        if(retrofit ==null){

            retrofit =Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit
    }

}