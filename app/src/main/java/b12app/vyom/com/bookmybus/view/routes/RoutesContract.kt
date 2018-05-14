package b12app.vyom.com.bookmybus.view.routes

import b12app.vyom.com.bookmybus.model.JBusByRoute

interface RoutesContract {

    interface IView {
        fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>,startCity:String,endCity:String)

    }

    interface IPresenter {


        fun getRouteId(startLatitude: String, startLongitude: String, endLatitude: String, endLongitude: String)
        fun getBusesForRoute(id: String,startCity:String,endCity:String)
    }
}
