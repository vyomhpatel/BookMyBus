package b12app.vyom.com.bookmybus.data.remote

import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.model.Route
import b12app.vyom.com.bookmybus.utils.city
import b12app.vyom.com.bookmybus.utils.routeinfo
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

public interface SearchBusAPI {
    @GET(city)
    fun getCity(): Call<City>;

    @GET(routeinfo)
    fun getRouteinfo(@Query("route-startpoint-latitude") startLatitude: String,
                         @Query("route-startpoint-longitude") startLongituede: String,
                         @Query("route-endpoint-latitude") endLatitude: String,
                         @Query("route-endpoint-longiude") endLongitude: String): Call<Route>


}