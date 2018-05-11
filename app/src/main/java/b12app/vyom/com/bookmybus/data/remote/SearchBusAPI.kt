package b12app.vyom.com.bookmybus.data.remote

import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.utils.city
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

public interface SearchBusAPI {
    @GET(city)
    fun getCity(): Call<City>;

}