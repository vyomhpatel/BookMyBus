package b12app.vyom.com.bookmybus.view.Routes;

import android.content.Context;

import java.util.List;

import b12app.vyom.com.bookmybus.model.JBusByRoute;

public interface RoutesContract {

    interface IView{
      void  initRecyclerView(List<JBusByRoute.BusinformationBean> businformationBeanList);
      
    }

    interface IPresenter{



        void getBusesForRoute();
    }
}
