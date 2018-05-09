package b12app.vyom.com.bookmybus.view.returnroute

import b12app.vyom.com.bookmybus.model.JBusByRoute

interface ReturnRoutesContract {

    interface IView {
        fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>)

    }

    interface IPresenter {


        fun getRouteId(startLatitude: String, startLongitude: String, endLatitude: String, endLongitude: String)
        fun getBusesForRoute(id: String)
    }
}
