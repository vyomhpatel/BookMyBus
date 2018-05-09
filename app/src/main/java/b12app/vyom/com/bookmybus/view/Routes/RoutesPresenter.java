package b12app.vyom.com.bookmybus.view.Routes;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import b12app.vyom.com.bookmybus.model.JBusByRoute;
import b12app.vyom.com.bookmybus.data.remote.*;
import b12app.vyom.com.bookmybus.utils.VolleyHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesPresenter implements RoutesContract.IPresenter,GetIDListener {

    private RoutesContract.IView iView;
    private Context context;
    private RoutesActivity routesActivity;
    private JApiService jApiService;


    public RoutesPresenter(RoutesActivity routesActivity) {
        this.routesActivity = routesActivity;
        this.context = routesActivity;
        this.iView = routesActivity;
        jApiService = JRetrofitInstance.getRetrofitInstance().create(JApiService.class);
//        new VolleyHelper(routesActivity).startRequest(1,1,1,1,this);
    }

    @Override
    public void getBusesForRoute(String id) {

        jApiService.getUser("1").enqueue(new Callback<JBusByRoute>() {
            @Override
            public void onResponse(Call<JBusByRoute> call, Response<JBusByRoute> response) {

                JBusByRoute jBusByRoute = response.body();
                iView.initRecyclerView(jBusByRoute.getBusinformation());

            }

            @Override
            public void onFailure(Call<JBusByRoute> call, Throwable t) {

            }
        });

    }

    @Override
    public void getId(@NotNull String id) {
        getBusesForRoute(id);
    }
}
