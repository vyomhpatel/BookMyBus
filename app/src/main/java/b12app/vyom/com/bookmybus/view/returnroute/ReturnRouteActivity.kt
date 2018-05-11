package b12app.vyom.com.bookmybus.view.returnroute

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.RoutesAdapter
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.ENDLatitude
import b12app.vyom.com.bookmybus.utils.ENDLongitude
import b12app.vyom.com.bookmybus.utils.STARTLatitude
import b12app.vyom.com.bookmybus.utils.STARTLongitude
import b12app.vyom.com.bookmybus.view.TestActivity
import kotlinx.android.synthetic.main.activity_journey_list.*
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
        initToolbar()
        iPresenter = ReturnRoutesPresenter(this)
        returnStartLat = intent.getStringExtra(STARTLatitude)
        returnStartLong = intent.getStringExtra(STARTLongitude)
        returnEndLat = intent.getStringExtra(ENDLatitude)
        returnEndLat = intent.getStringExtra(ENDLongitude)
        iPresenter!!.getRouteId(returnStartLat,returnStartLong,returnEndLat,returnEndLong)


    }
    private fun initToolbar() {
        tbreturnRoutes!!.setNavigationIcon(R.drawable.back_ic)
        setSupportActionBar(tbreturnRoutes)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true);
        supportActionBar!!.title = "Vadodara - Bombay"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>) {

       var routesAdapter = RoutesAdapter(this,businformationBeanList)
        returnRoutesRecyclerView.adapter = routesAdapter
        returnRoutesRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        routesAdapter.setMItemClickListener { v, position ->
            startActivity(Intent(this@ReturnRouteActivity,TestActivity::class.java))
        }

    }
}
