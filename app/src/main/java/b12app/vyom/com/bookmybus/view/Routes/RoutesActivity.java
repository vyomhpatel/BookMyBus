package b12app.vyom.com.bookmybus.view.Routes;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.bookmybus.R;
import b12app.vyom.com.bookmybus.adapters.RoutesAdapter;
import b12app.vyom.com.bookmybus.model.JBusByRoute;
import b12app.vyom.com.bookmybus.model.JRoutes;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.internal.connection.RouteException;

public class RoutesActivity extends AppCompatActivity implements RoutesContract.IView {

    @BindView(R.id.routesRecyclerView)
    RecyclerView routesRecyclerView;

    @BindView(R.id.tbRoutes)
    Toolbar tbRoutes;



    private Unbinder  unbinder;
    private JBusByRoute busByRoute;
    private RoutesContract.IPresenter iPresenter;
    private List<JBusByRoute.BusinformationBean> businformationBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_list);

        unbinder = ButterKnife.bind(this);
        iPresenter = new RoutesPresenter(this);
        businformationBeanList = new ArrayList<>();
        iPresenter.getBusesForRoute();


        initToolbar();



    }

    private void initToolbar() {
        tbRoutes.setNavigationIcon(R.drawable.back_ic);
        setSupportActionBar(tbRoutes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vadodara - Bombay");

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initRecyclerView(List<JBusByRoute.BusinformationBean> businformationBeanList) {
//        JBusByRoute.BusinformationBean businformationBean = new JBusByRoute.BusinformationBean("1","111",
//                "AC","9:00","2 HR","$ 150","8:30 PM","11:00 PM");
//        JBusByRoute.BusinformationBean businformationBean2 = new JBusByRoute.BusinformationBean("2","111",
//                "AC","10:00","2 HR","$ 150","8:30 PM","11:00 PM");
//
//
//        businformationBeanList.add(businformationBean);
//        businformationBeanList.add(businformationBean2);

//        busByRoute = new JBusByRoute();
//        busByRoute.setBusinformation(businformationBeanList);

        routesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RoutesAdapter routesAdapter = new RoutesAdapter(this,businformationBeanList);
        routesRecyclerView.setAdapter(routesAdapter);

    }
}
