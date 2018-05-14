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
import com.google.gson.Gson
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences





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
    private var mPrefs:SharedPreferences?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_list)

        iPresenter = RoutesPresenter(this)
        businformationBeanList = ArrayList<JBusByRoute.BusinformationBean>()

        mPrefs = this.getSharedPreferences(getString(b12app.vyom.com.bookmybus.R.string.shared_pref_title),android.content.Context.MODE_PRIVATE)

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
    }

    override fun initRecyclerView(businformationBeanList: List<JBusByRoute.BusinformationBean>,startCity:String,endCity:String) {

        routesRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val routesAdapter = RoutesAdapter(this, businformationBeanList)
        routesRecyclerView!!.adapter = routesAdapter

        routesAdapter.setMItemClickListener { v, position ->
            intent = Intent(this,ReturnRouteActivity::class.java)
            intent.putExtra(STARTLatitude,endLat)
            intent.putExtra(STARTLongitude,endLong)
            intent.putExtra(ENDLatitude,startLat)
            intent.putExtra(ENDLongitude,startLong)
            intent.putExtra(STARTName,endCity)
            intent.putExtra(ENDName,startCity)
//            var bundle = Bundle()
//            bundle.putSerializable("to_route",businformationBeanList.get(position))
//            intent.putExtras(bundle)

            val prefsEditor = mPrefs!!.edit()
            val gson = Gson()
            val json = gson.toJson(businformationBeanList.get(position))
            prefsEditor.putString("route", json)
            prefsEditor.putString("from_city",startCity)
            prefsEditor.putString("to_city",endCity)
            prefsEditor.commit()
            startActivity(intent)
        }

    }
}
