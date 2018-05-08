package b12app.vyom.com.bookmybus.data.remote;

import b12app.vyom.com.bookmybus.model.JBusByRoute;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JApiService {

    @GET("businfo.php")
    Call<JBusByRoute> getUser(@Query("routeid") String routeid);



}
