package b12app.vyom.com.bookmybus.adapters

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.utils.isBooked
import b12app.vyom.com.bookmybus.utils.isReserved

class SeatAdapter(private var states: List<Int>,val context: Context) : BaseAdapter() {
    //should empty 2,7,12
    var seatLine=2
    //4,9,14
    var seatEnd=4
    override fun getItem(position: Int): Int {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        val plus=(states.size-seatLine)/(seatEnd+1)+1
        return states.size+plus
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val index=getIndexByPosition(position)
        //if seat is in pathway, put nothing
        if((position-seatLine)%(seatEnd+1)==0)
            return View(context)
        if(index>=states.size) return View(context)
        val holder:Holder
        val v:View
        if(convertView==null){
            v= LayoutInflater.from(context).inflate(R.layout.seat_item,parent,false);
            holder= Holder(v)
            v.tag=holder
        }else{
            v=convertView
            holder= v.tag as Holder
        }
//        if(seatNumber>=(states.size-seatLine)/(seatEnd+1)-seatLine-1) seatNumber++

            holder.numberTv.text=index.toString()
            holder.seatImg.setImageResource(R.drawable.seat)
            holder.state=states[index]
            holder.setClick()

        return v
    }

    private fun getIndexByPosition(position: Int): Int {
        var subtract=(position-seatLine)/(seatEnd+1)+1
        if(position<seatLine) subtract=0
        return position-subtract
    }

    class Holder(v:View){

        var numberTv:TextView
        var seatImg:ImageView
        var isClicked=false
        var state=0
        var seatItem:CoordinatorLayout
        init {
            numberTv=v.findViewById(R.id.number)
            seatImg=v.findViewById(R.id.seat)
            seatItem=v.findViewById(R.id.seatItem)
        }
        fun setClick() {
            seatItem.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {
                    if(state== isBooked){
                        Log.i("SeatAdpater isBooked",numberTv.text.toString());
                        return
                    }
                    if(state== isReserved){
                        Log.i("SeatAdpater isReserved",numberTv.text.toString());
                        return
                    }
                    if(isClicked){
                        //then cancel
                        isClicked=false
                        Log.i("SeatAdpater cancel",numberTv.text.toString());
                    }else{
                        //then click
                        isClicked=true
                        Log.i("SeatAdpater click", numberTv.text.toString());
                    }
                }
            })
        }
    }
    fun update(states:List<Int>){
        this.states=states
        notifyDataSetChanged()
    }

}