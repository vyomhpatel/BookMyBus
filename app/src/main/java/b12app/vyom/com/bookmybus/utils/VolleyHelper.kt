package b12app.vyom.com.bookmybus.utils

import android.content.Context
import b12app.vyom.com.bookmybus.view.routes.GetIDListener
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class VolleyHelper(private val context: Context){
        fun startRequest(startLatitude:String, startLongitude:String, endLatitude:String,endLongitude:String, idListener:GetIDListener){
            var queue= Volley.newRequestQueue(context)
            val url="http://rjtmobile.com/aamir/otr/android-app/routeinfo.php?" +
                    "route-startpoint-latitude=" + startLatitude +
                    "&route-startpoint-longitude=" + startLongitude +
                    "&route-endpoint-latitude=" + endLatitude +
                    "&route-endpoint-longiude=" + endLongitude
            var jsonObject= JsonObjectRequest(Request.Method.GET,url,null, Response.Listener<JSONObject> {
                response ->
                try{
                    var jsonArray=response.getJSONArray("route")
                    var jsonObject=jsonArray.get(0) as JSONObject
                    var id=jsonObject.get("id") as String
                    idListener.getId(id)
                }catch (e: JSONException){
                    e.printStackTrace()
                    idListener.getId("1")
                }

            },null)
            queue.add(jsonObject)
        }

}