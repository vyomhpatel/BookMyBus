package b12app.vyom.com.bookmybus.view.home


import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import b12app.vyom.com.bookmybus.data.remote.RetrofitInstance
import b12app.vyom.com.bookmybus.data.remote.SearchBusAPI
import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.utils.Trie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_home2.*
import java.util.*

class HomePresenter(homeActivity: HomeActivity) {
    public var homeActivity: HomeActivity? = homeActivity
    var context = homeActivity
    var city: List<City.CityBean>? = null
    var adapter: ArrayAdapter<String>? = null
    var autoComplete: ArrayList<City.CityBean>? = null
    var startLocation: City.CityBean? = null
    var endLocation: City.CityBean? = null
    var flag = true
    var mPrefs:SharedPreferences ?= context.getSharedPreferences("bmb_shared",android.content.Context.MODE_PRIVATE)


    var trie: Trie<City.CityBean>


    fun setSearchView() {
        homeActivity!!.searchLocation.clearFocus()
        homeActivity!!.list_view.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                if (flag) {
                    startLocation = autoComplete?.get(i)
                    homeActivity!!.from_date.text = startLocation?.cityname
                } else {
                    endLocation = autoComplete?.get(i)
                    homeActivity!!.end.text = endLocation?.cityname
                    homeActivity!!.submit.setVisibility(View.VISIBLE)

                    homeActivity!!.letsGO(startLocation?.citylatitude,
                            startLocation?.citylongtitude,
                            endLocation?.citylatitude,
                            endLocation?.citylongtitude,
                            startLocation?.cityname,
                            endLocation?.cityname
                    )
                }
                homeActivity!!.searchLocation.clearFocus();
                homeActivity!!.searchLocation.setQuery("", false);
            }
        })
//        homeActivity!!.list_view.setTextFilterEnabled(true);
        homeActivity!!.searchLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(homeActivity, query, Toast.LENGTH_LONG).show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length > 0) {
                    autoComplete = trie.getWordsForPrefix(trie.root, newText)
                    if (autoComplete == null) {
                        adapter?.clear()
                        return false
                    }
                    val autoStringList = ArrayList<String>()
                    for (city: City.CityBean in autoComplete!!) {
                        city.cityname?.let { autoStringList.add(it) }
                    }
                    adapter = ArrayAdapter<String>(homeActivity, android.R.layout.simple_list_item_1, autoStringList)
                    homeActivity!!.list_view.setAdapter(adapter)
                    return true
                } else {
                    adapter?.clear()
                }
                return false
            }
        })
    }

    fun requestCities() {
        val retrofit = RetrofitInstance.getRetrofitInstance()
        val callback = retrofit!!.create(SearchBusAPI::class.java).getCity()
        callback.enqueue(object : Callback<City> {
            override fun onFailure(call: Call<City>?, t: Throwable?) {
                Log.i("Response", t?.message);
            }

            override fun onResponse(call: Call<City>, response: Response<City>) {
                city = response.body()!!.getCity()
                for (cityBean: City.CityBean in city!!) {
                    trie.insert(cityBean, cityBean.cityname!!)
                }
            }
        })
    }

    init {
//        var myComponent=DaggerMyComponent.builder().build()
//        myComponent.inject(homeActivity)
        trie = Trie()
        requestCities()
        setSearchView()
        setDatePicker()
    }


    fun setDatePicker() {
        var calendar = Calendar.getInstance()
        homeActivity!!.from_date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                DatePickerDialog(homeActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    homeActivity!!.departure_date.text = ((monthOfYear + 1).toString() + "-" + dayOfMonth + "-" + year.toString())
                    var editor = mPrefs!!.edit()
                    editor.putInt("start_month",monthOfYear+1)
                    editor.putInt("start_day",dayOfMonth)
                    editor.commit()
                    flag = true
                    homeActivity!!.searchLocation.requestFocus()
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })
        homeActivity!!.end.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                DatePickerDialog(homeActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    homeActivity!!.arrival_date.text = ((monthOfYear).toString() + "-" + dayOfMonth + '-' + year.toString())
                    flag = false
                    homeActivity!!.searchLocation.requestFocus()
                    var editor = mPrefs!!.edit()
                    editor.putInt("end_month",monthOfYear)
                    editor.putInt("end_day",dayOfMonth)
                    editor.commit()
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH + 1), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        })


    }
}