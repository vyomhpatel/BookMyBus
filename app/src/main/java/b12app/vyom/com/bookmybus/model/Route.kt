package b12app.vyom.com.bookmybus.model


data class Route(
    val id: String,
    val route_name: String,
    val route_startfrom: String,
    val route_destination: String,
    val route_startpoint_latitude: String,
    val route_startpoint_longitude: String,
    val route_endpoint_latitude: String,
    val route_endpoint_longiude: String
)