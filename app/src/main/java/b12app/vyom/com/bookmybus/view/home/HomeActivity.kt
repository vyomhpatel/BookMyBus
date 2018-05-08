package b12app.vyom.com.bookmybus.view.home

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import b12app.vyom.com.bookmybus.R

import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.content_drawer.*

class HomeActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private var mDrawerToggle: ActionBarDrawerToggle? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initDraw()
        var homePresenter=HomePresenter(this)
//        homePresenter.requestCities()
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

    fun initDraw(){
        mDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close)
        drawerLayout.addDrawerListener(mDrawerToggle!!)
        mDrawerToggle!!.syncState()
        navigation_view.setNavigationItemSelectedListener(this);
//        var drawerMenu =navigation_view.menu;
//        var menuItem =drawerMenu.getItem()0
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return mDrawerToggle!!.onOptionsItemSelected(item)||super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.login){
            Toast.makeText(this,"item1",Toast.LENGTH_LONG).show()
        }
        return true
    }
}

