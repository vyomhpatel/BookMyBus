package b12app.vyom.com.bookmybus.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JRetrofitInstance {

    private static final String BASE_URL = "http://rjtmobile.com/aamir/otr/android-app/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))      //gson
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //Rxjava
                    .baseUrl(BASE_URL)
                    .build();
        }


        return retrofit;
    }

    public static JApiService apiService() {
        return getRetrofitInstance().create(JApiService.class);
    }
}
