package b12app.vyom.com.bookmybus.view.routes

import b12app.vyom.com.bookmybus.model.JBusByRoute

interface RoutesContract {

    interface IView {
        fun initRecyclerView(incomeList: List<JBusByRoute.BusinformationBean>)

    }

    interface IPresenter {


        fun getRouteId(startLatitude: String, startLongitude: String, endLatitude: String, endLongitude: String)
        fun getBusesForRoute(id: String)
    }
}
