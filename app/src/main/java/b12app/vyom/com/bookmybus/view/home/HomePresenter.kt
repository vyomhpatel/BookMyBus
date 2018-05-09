package b12app.vyom.com.bookmybus.view.home


import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import b12app.vyom.com.bookmybus.data.remote.RetrofitInstance
import b12app.vyom.com.bookmybus.data.remote.SearchBusAPI
import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.utils.Trie
import kotlinx.android.synthetic.main.content_drawer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.widget.AdapterView
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.utils.ENDLatitude
import b12app.vyom.com.bookmybus.utils.ENDLongitude
import b12app.vyom.com.bookmybus.utils.STARTLatitude
import b12app.vyom.com.bookmybus.utils.STARTLongitude
import b12app.vyom.com.bookmybus.view.Routes.RoutesActivity
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetView
import java.util.*
import android.graphics.drawable.Drawable
import android.graphics.Typeface




class HomePresenter(homeActivity: HomeActivity) {
    var homeActivity: HomeActivity? = homeActivity
    var city:List<City.CityBean>?=null
    var adapter:ArrayAdapter<String>?=null
    var autoComplete:ArrayList<City.CityBean>?=null
    var trie: Trie<City.CityBean>
    var startLocation:City.CityBean?=null
    var endLocation:City.CityBean?=null
    var flag=true

    fun setSearchView() {
        homeActivity!!.searchLocation.clearFocus()
        homeActivity!!.list_view.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if(flag){
                    startLocation=autoComplete?.get(i)
                    homeActivity!!.start.text=startLocation?.cityname
                    homeActivity!!.chooseDestination()
                }else{
                    endLocation=autoComplete?.get(i)
                    homeActivity!!.end.text=endLocation?.cityname
                    homeActivity!!.letsGO()
                }
                homeActivity!!.searchLocation.clearFocus();
                homeActivity!!.searchLocation.setQuery("",false);
            }
        })
//        homeActivity!!.list_view.setTextFilterEnabled(true);
        homeActivity!!.searchLocation.setIconifiedByDefault(true); //直接显示搜索框，不隐藏
        homeActivity!!.searchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(homeActivity,query,Toast.LENGTH_LONG).show()
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>0){
                    autoComplete=trie.getWordsForPrefix(trie.root,newText)
                    if(autoComplete==null){
                        adapter?.clear()
                        return false
                    }
                    val autoStringList=ArrayList<String>()
                    for(city:City.CityBean in autoComplete!!){
                        city.cityname?.let { autoStringList.add(it) }
                    }
                    adapter=ArrayAdapter<String>(homeActivity,android.R.layout.simple_list_item_1,autoStringList)
                    homeActivity!!.list_view.setAdapter(adapter)
                    return true
                }
                else{
                    adapter?.clear()
                }
                return false
            }
        })
//        homeActivity!!.swithLocation.setOnClickListener(object :View.OnClickListener{
//            override fun onClick(v: View?) {
//                //swap location
//                val tempCity=startLocation
//                startLocation=endLocation
//                endLocation=tempCity
//                homeActivity!!.start.text=startLocation?.cityname
//                homeActivity!!.end.text=endLocation?.cityname
//            }
//        })

    }

    fun requestCities(){
        val retrofit=RetrofitInstance.getRetrofitInstance()
        val callback=retrofit!!.create(SearchBusAPI::class.java).getCity()
        callback.enqueue(object :Callback<City> {
            override fun onFailure(call: Call<City>?, t: Throwable?) {
                Log.i("Response", t?.message);
            }
            override fun onResponse(call:Call<City>, response: Response<City>) {
                city=response.body()!!.getCity()
                for(cityBean : City.CityBean in city!!){
                    trie.insert(cityBean,cityBean.cityname!!)
                }
           }
       })
    }

    init {
        trie = Trie()
        requestCities()
        setSearchView()
        setDatePicker()

    homeActivity.submit.setOnClickListener(object :View.OnClickListener{
        override fun onClick(v: View?) {
            if(startLocation!=null && endLocation!==null){
                val intent=Intent(homeActivity,RoutesActivity::class.java)
                intent.putExtra(STARTLatitude,startLocation!!.citylatitude!!)
                intent.putExtra(STARTLongitude,startLocation!!.citylongtitude!!)
                intent.putExtra(ENDLatitude,endLocation!!.citylatitude!!)
                intent.putExtra(ENDLongitude,endLocation!!.citylongtitude!!)
                homeActivity.startActivity(intent)
            }
        }
    })
    }

    fun setDatePicker(){
        var calendar= Calendar.getInstance()
        homeActivity!!.start.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var dialog=DatePickerDialog(homeActivity, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                    homeActivity!!.stareDate.text= ((monthOfYear + 1).toString() +"-"+ dayOfMonth + "-" +year.toString() )
                    flag=true
                    homeActivity!!.whereYouStart()
                    homeActivity!!.searchLocation.requestFocus()
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

                dialog.show()
            }
        })
        homeActivity!!.end.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                DatePickerDialog(homeActivity, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth ->
                    homeActivity!!.endDate.text=((monthOfYear + 1).toString() + "-" + dayOfMonth+'-'+year.toString())
                    flag=false
                    homeActivity!!.searchLocation.requestFocus()
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH+1), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
    }
}