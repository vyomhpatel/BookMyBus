package b12app.vyom.com.bookmybus.view.routes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import java.util.ArrayList

import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.RoutesAdapter
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.*
import b12app.vyom.com.bookmybus.view.returnroute.ReturnRouteActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import kotlinx.android.synthetic.main.activity_journey_list.*
import kotlinx.android.synthetic.main.content_drawer.*

class RoutesActivity : AppCompatActivity(), RoutesContract.IView {

    private var unbinder: Unbinder? = null
    private val busByRoute: JBusByRoute? = null
    private var iPresenter: RoutesContract.IPresenter? = null
    private var businformationBeanList: List<JBusByRoute.BusinformationBean> ? = null
    private var startLat = ""
    private var startLong = ""
    private var endLat = ""
    private var endLong = ""
    private var startName = ""
    private var endName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_list)

        unbinder = ButterKnife.bind(this)
        iPresenter = RoutesPresenter(this)
        businformationBeanList = ArrayList<JBusByRoute.BusinformationBean>()

         startLat= intent.getStringExtra(STARTLatitude)
         startLong = intent.getStringExtra(STARTLongitude)
         endLat = intent.getStringExtra(ENDLatitude)
         endLong = intent.getStringExtra(ENDLongitude)
        startName=intent.getStringExtra(STARTName)
        endName=intent.getStringExtra(ENDName)
        iPresenter!!.getRouteId(startLat, startLong, endLat, endLong)
        initToolbar()
    }

    private fun initToolbar() {
        tbRoutes!!.setNavigationIcon(R.drawable.back_ic)
        setSupportActionBar(tbRoutes)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true);
        supportActionBar!!.title = startName+" - "+endName
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStop() {
        super.onStop()
        unbinder!!.unbind()
    }

    override fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>) {
        //        JBusByRoute.BusinformationBean businformationBean = new JBusByRoute.BusinformationBean("1","111",
        //                "AC","9:00","2 HR","$ 150","8:30 PM","11:00 PM");
        //        JBusByRoute.BusinformationBean businformationBean2 = new JBusByRoute.BusinformationBean("2","111",
        //                "AC","10:00","2 HR","$ 150","8:30 PM","11:00 PM");
        //
        //
        //        businformationBeanList.add(businformationBean);
        //        businformationBeanList.add(businformationBean2);

        //        busByRoute = new JBusByRoute();
        //        busByRoute.setBusinformation(businformationBeanList);

        routesRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val routesAdapter = RoutesAdapter(this, businformationBeanList)
        routesRecyclerView!!.adapter = routesAdapter

        routesAdapter.setMItemClickListener { v, position ->
            intent = Intent(this,ReturnRouteActivity::class.java)
            intent.putExtra(STARTLatitude,endLat)
            intent.putExtra(STARTLongitude,endLong)
            intent.putExtra(ENDLatitude,startLat)
            intent.putExtra(ENDLongitude,startLong)
            startActivity(intent)
        }

    }
}
