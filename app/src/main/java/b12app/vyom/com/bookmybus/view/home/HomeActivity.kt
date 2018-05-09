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
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import android.content.DialogInterface
import android.graphics.Typeface
import android.graphics.drawable.Drawable


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
        TapTargetView.showFor(this, // `this` is an Activity
                TapTarget.forView(findViewById(R.id.start), "startLocation", "click to have a travel!")
                        // All options below are optional
                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.txtBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40), // Specify the target radius (in dp)
                object : TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)      // This call is optional
                        //doSomething()
                    }
                })
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
    fun whereYouStart(){
        TapTargetView.showFor(this, // `this` is an Activity
                TapTarget.forView(findViewById(R.id.searchLocation), "input some place", "")
                        // All options below are optional
                        .outerCircleColor(R.color.blue)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.red)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.txtBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40), // Specify the target radius (in dp)
                object : TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)      // This call is optional
                        //doSomething()
                    }
                })
    }
    fun chooseDestination(){
        TapTargetView.showFor(this, // `this` is an Activity
                TapTarget.forView(findViewById(R.id.end), "let's choose your destination", "")
                        // All options below are optional
                        .outerCircleColor(R.color.red)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.blue)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.txtBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40), // Specify the target radius (in dp)
                object : TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)      // This call is optional
                        //doSomething()
                    }
                })
    }
    fun letsGO(){
        TapTargetView.showFor(this, // `this` is an Activity
                TapTarget.forView(findViewById(R.id.submit), "let's Go!", "")
                        // All options below are optional
                        .outerCircleColor(R.color.blue)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(18)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
                        .textColor(R.color.red)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.txtBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(40), // Specify the target radius (in dp)
                object : TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    override fun onTargetClick(view: TapTargetView) {
                        super.onTargetClick(view)      // This call is optional
                        //doSomething()
                    }
                })
    }
}

