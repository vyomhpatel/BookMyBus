package b12app.vyom.com.bookmybus.view.routes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem

import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.MockData
import b12app.vyom.com.bookmybus.adapters.RoutesAdapter
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.*
import b12app.vyom.com.bookmybus.view.returnroute.ReturnRouteActivity
import com.google.android.gms.common.util.CollectionUtils.mutableListOf
import kotlinx.android.synthetic.main.activity_journey_list.*

class RoutesActivity : AppCompatActivity(), RoutesContract.IView {
    private var iPresenter: RoutesContract.IPresenter? = null
    private var businformationBeanList= mutableListOf<JBusByRoute.BusinformationBean>()
    private var startLat = ""
    private var startLong = ""
    private var endLat = ""
    private var endLong = ""
    private var startName = ""
    private var endName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey_list)

        iPresenter = RoutesPresenter(this)
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

    override fun initRecyclerView(incomeList: List<JBusByRoute.BusinformationBean>) {
        for(item in incomeList){
            businformationBeanList.add(item)
        }
        businformationBeanList.addAll(MockData.Mock())
        businformationBeanList.addAll(MockData.Mock())
        businformationBeanList.addAll(MockData.Mock())
        routesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val routesAdapter = RoutesAdapter(this, businformationBeanList)
        routesRecyclerView.adapter = routesAdapter
//        val animator=DefaultItemAnimator()
//        animator.addDuration=500
//        routesRecyclerView.itemAnimator=animator
        var isSlidingToLast=false
        routesRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                    if(dy>0){
                        isSlidingToLast=true
                    }else{
                        isSlidingToLast=false
                    }
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if(isSlidingToLast&&newState == RecyclerView.SCROLL_STATE_IDLE){
                        val manager = recyclerView!!.getLayoutManager() as LinearLayoutManager
                        val lastVisibleItem = manager.findLastCompletelyVisibleItemPosition()
                        val totalItemCount = manager.itemCount

                        // 判断是否滚动到底部，并且是向下滚动
                        if(lastVisibleItem == (totalItemCount - 1)){
                            Log.i("ScrollListener ", "down")
                            val previousSize=businformationBeanList.size
                            businformationBeanList.addAll(MockData.Mock())
                            val newSize =businformationBeanList.size
                            for(i in previousSize..newSize)
                                routesAdapter.notifyItemInserted(i)
                        }
                }
            }
        })
        routesAdapter.setMItemClickListener { v, position ->
            intent = Intent(this,ReturnRouteActivity::class.java)
            intent.putExtra(STARTLatitude,endLat)
            intent.putExtra(STARTLongitude,endLong)
            intent.putExtra(ENDLatitude,startLat)
            intent.putExtra(ENDLongitude,startLong)
            startActivity(intent)
        }

    }


//            dx : 水平滚动距离
//            dy : 垂直滚动距离
//            dy > 0 时为手指向上滚动,列表滚动显示下面的内容
//            dy < 0 时为手指向下滚动,列表滚动显示上面的内容


}
