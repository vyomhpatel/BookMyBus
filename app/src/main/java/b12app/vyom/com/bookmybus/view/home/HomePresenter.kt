package b12app.vyom.com.bookmybus.view.home


import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import b12app.vyom.com.bookmybus.data.remote.RetrofitInstance
import b12app.vyom.com.bookmybus.data.remote.SearchBusAPI
import b12app.vyom.com.bookmybus.model.City
import b12app.vyom.com.bookmybus.utils.dictionaryTree.Trie
import kotlinx.android.synthetic.main.content_drawer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.widget.AdapterView


class HomePresenter(homeActivity: HomeActivity) {
    var homeActivity: HomeActivity? = homeActivity
    var city:List<City.CityBean>?=null
    var adapter:ArrayAdapter<String>?=null
    var trie:Trie= Trie()
    fun setSearchView() {
        homeActivity!!.list_view.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(homeActivity, i, Toast.LENGTH_SHORT).show()
            }
        })
        homeActivity!!.list_view.setTextFilterEnabled(true);
        homeActivity!!.startLocation.setIconifiedByDefault(false); //直接显示搜索框，不隐藏
        homeActivity!!.startLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>0){
                    var autoComplete=trie.getWordsForPrefix(trie.root,newText)
                    adapter=ArrayAdapter<String>(homeActivity,android.R.layout.simple_list_item_1,autoComplete)
                    homeActivity!!.list_view.setAdapter(adapter)
                    return true
                }
                else{
                    adapter!!.clear()
                }
                return false
            }
        })

    }

    fun requestCities(){
        var retrofit=RetrofitInstance.getRetrofitInstance()
        var callback=retrofit!!.create(SearchBusAPI::class.java).getCity()
        callback.enqueue(object :Callback<City> {
            override fun onFailure(call: Call<City>?, t: Throwable?) {
                Log.i("Response", t?.message);
            }
            override fun onResponse(call:Call<City>, response: Response<City>) {
                city=response.body()!!.getCity()
                for(cityBean : City.CityBean in city!!){
                    trie.insert(cityBean)
                }
           }
       })
    }
    companion object {
        fun XXX(){
        }
    }
    init {
        trie = Trie()
        requestCities()
        setSearchView()
    }
}