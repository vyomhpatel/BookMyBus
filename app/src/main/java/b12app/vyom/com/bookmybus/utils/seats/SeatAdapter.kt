package b12app.vyom.com.bookmybus.utils.seats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import b12app.vyom.com.bookmybus.R

class SeatAdapter(private var list: List<Int>, private val context: Context) : BaseAdapter() {
    var seatLine=2
    var seatEnd=4
    var index=0
    override fun getItem(position: Int): Int {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(position%seatEnd!=0 && position%seatLine==0 && index<list.size-seatEnd)
            return View(context)
        var holder=Holder()
        var v:View?=null
        if(convertView==null){
            v= LayoutInflater.from(context).inflate(R.layout.seat_item,parent,false);
            holder.numberTv=v.findViewById(R.id.number)
            holder.seatImg=v.findViewById(R.id.seat)
            v.tag=holder

        }else{
            v=convertView
            holder= convertView.tag as Holder
        }

        holder.numberTv!!.text=index.toString()
        holder.seatImg!!.setImageResource(R.drawable.seat)
        index++
        return v!!
    }

    class Holder{
        var numberTv:TextView?=null
        var seatImg:ImageView?=null
    }
    fun update(list:List<Int>){
        this.list=list
        notifyDataSetChanged()
    }

}