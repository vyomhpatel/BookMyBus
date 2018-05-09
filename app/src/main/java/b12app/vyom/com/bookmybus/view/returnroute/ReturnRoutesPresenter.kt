package b12app.vyom.com.bookmybus.view.returnroute

import android.content.Context

import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.data.remote.*
import b12app.vyom.com.bookmybus.utils.VolleyHelper
import b12app.vyom.com.bookmybus.view.routes.GetIDListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReturnRoutesPresenter(private val returnRouteActivity: ReturnRouteActivity) : ReturnRoutesContract.IPresenter, GetIDListener {

    private val iView: ReturnRoutesContract.IView
    private val context: Context
    private val jApiService: JApiService


    init {
        this.context = returnRouteActivity
        this.iView = returnRouteActivity
        jApiService = JRetrofitInstance.getRetrofitInstance().create(JApiService::class.java)

    }

    override fun getRouteId(startLatitude: String, startLongitude: String, endLatitude: String, endLongitude: String) {
        VolleyHelper(returnRouteActivity).startRequest(startLatitude, startLongitude, endLatitude, endLongitude, this)
    }

    override fun getBusesForRoute(id: String) {

        jApiService.getUser(id).enqueue(object : Callback<JBusByRoute> {
            override fun onResponse(call: Call<JBusByRoute>, response: Response<JBusByRoute>) {

                val jBusByRoute = response.body()
                iView.initRecyclerView(jBusByRoute!!.businformation)

            }

            override fun onFailure(call: Call<JBusByRoute>, t: Throwable) {

            }
        })

    }

    override fun getId(id: String) {
        getBusesForRoute(id)
    }
}
