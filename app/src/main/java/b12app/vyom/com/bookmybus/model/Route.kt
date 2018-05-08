package b12app.vyom.com.bookmybus.model

import com.google.gson.annotations.SerializedName

class Route{

    private var route: List<RouteBean>? = null

    fun getRoute(): List<RouteBean>? {
        return route
    }

    fun setRoute(route: List<RouteBean>) {
        this.route = route
    }

    class RouteBean {
        /**
         * id : 2
         * routename : Hyderabad-Bengaluru
         * route-startfrom : Hyderabad
         * route-destination : Bengaluru
         * route-startpoint-latitude : 17.387140
         * route-startpoint-longitude : 78.491684
         * route-endpoint-latitude : 12.972442
         * route-endpoint-longiude : 77.580643
         */

        var id: String? = null
        var routename: String? = null
        @SerializedName("route-startfrom")
        var routestartfrom: String? = null
        @SerializedName("route-destination")
        var routedestination: String? = null
        @SerializedName("route-startpoint-latitude")
        var routestartpointlatitude: String? = null
        @SerializedName("route-startpoint-longitude")
        var routestartpointlongitude: String? = null
        @SerializedName("route-endpoint-latitude")
        var routeendpointlatitude: String? = null
        @SerializedName("route-endpoint-longiude")
        var routeendpointlongiude: String? = null
    }
}