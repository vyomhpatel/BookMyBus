package b12app.vyom.com.bookmybus.view.returnroute

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.RoutesAdapter
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.ENDLatitude
import b12app.vyom.com.bookmybus.utils.ENDLongitude
import b12app.vyom.com.bookmybus.utils.STARTLatitude
import b12app.vyom.com.bookmybus.utils.STARTLongitude
import kotlinx.android.synthetic.main.activity_return_route.*

class ReturnRouteActivity : AppCompatActivity(), ReturnRoutesContract.IView {

    private var returnStartLat = ""
    private var returnStartLong = ""
    private var returnEndLat = ""
    private var returnEndLong = ""
    private var iPresenter: ReturnRoutesContract.IPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_route)

        iPresenter = ReturnRoutesPresenter(this)
        returnStartLat = intent.getStringExtra(STARTLatitude)
        returnStartLong = intent.getStringExtra(STARTLongitude)
        returnEndLat = intent.getStringExtra(ENDLatitude)
        returnEndLat = intent.getStringExtra(ENDLongitude)
        iPresenter!!.getRouteId(returnStartLat,returnStartLong,returnEndLat,returnEndLong)


    }

    override fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>) {

       var routesAdapter = RoutesAdapter(this,businformationBeanList)
        returnRoutesRecyclerView.adapter = routesAdapter
        routesAdapter.setMItemClickListener { v, position ->

        }

    }
}
