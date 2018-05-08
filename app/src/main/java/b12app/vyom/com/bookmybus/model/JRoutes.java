package b12app.vyom.com.bookmybus.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JRoutes {


    private List<RouteBean> route;



    public List<RouteBean> getRoutes() {
        return route;
    }

    public void setRoutes(List<RouteBean> route) {
        this.route = route;
    }

    public static class RouteBean {
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

        private String id;
        private String routename;
        @SerializedName("route-startfrom")
        private String routestartfrom;
        @SerializedName("route-destination")
        private String routedestination;
        @SerializedName("route-startpoint-latitude")
        private String routestartpointlatitude;
        @SerializedName("route-startpoint-longitude")
        private String routestartpointlongitude;
        @SerializedName("route-endpoint-latitude")
        private String routeendpointlatitude;
        @SerializedName("route-endpoint-longiude")
        private String routeendpointlongiude;

        public RouteBean(String id, String routename, String routestartfrom, String routedestination, String routestartpointlatitude, String routestartpointlongitude, String routeendpointlatitude, String routeendpointlongiude) {
            this.id = id;
            this.routename = routename;
            this.routestartfrom = routestartfrom;
            this.routedestination = routedestination;
            this.routestartpointlatitude = routestartpointlatitude;
            this.routestartpointlongitude = routestartpointlongitude;
            this.routeendpointlatitude = routeendpointlatitude;
            this.routeendpointlongiude = routeendpointlongiude;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRoutename() {
            return routename;
        }

        public void setRoutename(String routename) {
            this.routename = routename;
        }

        public String getRoutestartfrom() {
            return routestartfrom;
        }

        public void setRoutestartfrom(String routestartfrom) {
            this.routestartfrom = routestartfrom;
        }

        public String getRoutedestination() {
            return routedestination;
        }

        public void setRoutedestination(String routedestination) {
            this.routedestination = routedestination;
        }

        public String getRoutestartpointlatitude() {
            return routestartpointlatitude;
        }

        public void setRoutestartpointlatitude(String routestartpointlatitude) {
            this.routestartpointlatitude = routestartpointlatitude;
        }

        public String getRoutestartpointlongitude() {
            return routestartpointlongitude;
        }

        public void setRoutestartpointlongitude(String routestartpointlongitude) {
            this.routestartpointlongitude = routestartpointlongitude;
        }

        public String getRouteendpointlatitude() {
            return routeendpointlatitude;
        }

        public void setRouteendpointlatitude(String routeendpointlatitude) {
            this.routeendpointlatitude = routeendpointlatitude;
        }

        public String getRouteendpointlongiude() {
            return routeendpointlongiude;
        }

        public void setRouteendpointlongiude(String routeendpointlongiude) {
            this.routeendpointlongiude = routeendpointlongiude;
        }
    }
}
