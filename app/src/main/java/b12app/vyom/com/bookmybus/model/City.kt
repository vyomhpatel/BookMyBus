package b12app.vyom.com.bookmybus.model

class City {
    private var city: List<CityBean>? = null

    fun getCity(): List<CityBean>? {
        return city
    }

    fun setCity(city: List<CityBean>) {
        this.city = city
    }

    class CityBean {
        /**
         * cityname : Hyderabad
         * citylatitude : 17.387140
         * citylongtitude : 78.491684
         */
        var cityname: String? = null
        var citylatitude: String? = null
        var citylongtitude: String? = null
    }
}