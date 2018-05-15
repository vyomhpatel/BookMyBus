package b12app.vyom.com.bookmybus.view.home

import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import b12app.vyom.com.bookmybus.R

import kotlinx.android.synthetic.main.activity_home2.*
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence
import com.getkeepsafe.taptargetview.TapTargetView
import android.content.Intent
import android.graphics.Typeface
import android.support.v4.app.FragmentActivity
import b12app.vyom.com.bookmybus.utils.*
import b12app.vyom.com.bookmybus.view.routes.RoutesActivity
import com.special.ResideMenu.ResideMenu
import android.view.MotionEvent
import android.widget.Toast
import b12app.vyom.com.bookmybus.R.id.submit
import com.github.amlcurran.showcaseview.ShowcaseView
import com.github.amlcurran.showcaseview.targets.ViewTarget
import com.special.ResideMenu.ResideMenuItem
import android.graphics.drawable.Drawable


class HomeActivity : FragmentActivity(), View.OnClickListener {


    private var resideMenu: ResideMenu? = null
    var home: ResideMenuItem? = null
    var profile: ResideMenuItem? = null
    var settings: ResideMenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        var intent=getIntent()
        var uri=intent.data
        
        initDraw()
        val homePresenter = HomePresenter(this)
        homePresenter.requestCities()


    }

    fun initDraw() {
        resideMenu = ResideMenu(this);
        resideMenu!!.setBackground(R.drawable.bmb_ic)
        resideMenu!!.attachToActivity(this)

        home = ResideMenuItem(this, R.drawable.home, "Home");
        profile = ResideMenuItem(this, R.drawable.profile, "Profile");
        settings = ResideMenuItem(this, R.drawable.setting, "Setting");


        home!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@HomeActivity, "home", Toast.LENGTH_LONG).show()
                resideMenu!!.closeMenu()
            }
        })
        profile!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@HomeActivity, "profile", Toast.LENGTH_LONG).show()
                val technology = Technology()
                technology.show(getSupportFragmentManager(), "technology")
            }
        })
        settings!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@HomeActivity, "setting", Toast.LENGTH_LONG).show()

                resideMenu!!.closeMenu()
            }
        })

        resideMenu!!.addMenuItem(home, ResideMenu.DIRECTION_LEFT) // or  ResideMenu.DIRECTION_RIGHT
        resideMenu!!.addMenuItem(profile, ResideMenu.DIRECTION_LEFT)
        resideMenu!!.addMenuItem(settings, ResideMenu.DIRECTION_LEFT)

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return resideMenu!!.dispatchTouchEvent(ev)
    }


    fun letsGO(sla: String?, slong: String?, ela: String?, elong: String?, sname: String?, ename: String?) {
        submit.visibility=View.VISIBLE
        submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (sla != null && slong != null && ela != null && elong != null) {
                    submit.setVisibility(View.GONE)
                    val intent = Intent(this@HomeActivity, RoutesActivity::class.java)
                    intent.putExtra(STARTLatitude, sla)
                    intent.putExtra(STARTLongitude, slong)
                    intent.putExtra(ENDLatitude, ela)
                    intent.putExtra(ENDLongitude, elong)
                    intent.putExtra(STARTName, sname)
                    intent.putExtra(ENDName, ename)
                    startActivity(intent)
                }
            }
        })
    }

    fun whereYouStart() {

    }

    override fun onClick(v: View?) {

    }
}

