package b12app.vyom.com.bookmybus.adapters

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.R.id.seatItem
import b12app.vyom.com.bookmybus.utils.isBooked
import b12app.vyom.com.bookmybus.utils.isReserved
import b12app.vyom.com.bookmybus.view.seatpick.FragmentSeatPicker
import java.util.*
import kotlin.collections.ArrayList

class SeatAdapter(private var states: List<Int>,private val context: Context, private val howManySeats:Int) : BaseAdapter() {
    //should empty 2,7,12


    var fragmentSeatPicker:FragmentSeatPicker?=null
    var seatLine = 2
    //4,9,14
    var seatEnd = 5
    var queue= LinkedList<Holder>()
    override fun getItem(position: Int): Int {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        val plus = (states.size - seatLine) / (seatEnd) + 1
        return states.size + plus
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val index = getIndexByPosition(position)
        //if seat is in pathway, put nothing
        if ((position - seatLine) % (seatEnd) == 0)
            return View(context)
        if (index >= states.size) return View(context)
        val holder: Holder
        val v: View
        if (convertView == null) {
            v = LayoutInflater.from(context).inflate(R.layout.seat_item, parent, false);
            holder = Holder(v)
            v.tag = holder
        } else {
            v = convertView
            holder = v.tag as Holder
        }
//        if(seatNumber>=(states.size-seatLine)/(seatEnd+1)-seatLine-1) seatNumber++
        holder.numberTv.text = index.toString()

        holder.state = states[index]
        if (holder.state == isBooked) {
            holder.seatImg.setImageResource(R.drawable.seat_normal_selected)
        } else if (holder.state == isReserved) {
            holder.seatImg.setImageResource(R.drawable.seat_normal_booked)
        } else {
            holder.seatImg.setImageResource(R.drawable.seat_normal)
        }

        v.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (holder.state == isBooked) {
                    Log.i("SeatAdpater isBooked", holder.numberTv.text.toString());
                    Toast.makeText(context,context.getString(R.string.booked),Toast.LENGTH_SHORT).show()
                    return
                }
                if (holder.state == isReserved) {
                    Log.i("SeatAdpater isReserved", holder.numberTv.text.toString());
                    Toast.makeText(context,context.getString(R.string.reserved),Toast.LENGTH_SHORT).show()
                    return
                }
                if (holder.isClicked) {
                    //then cancel
                    holder.isClicked = false
                    queue.remove(holder)
                    holder.seatImg.setImageResource(R.drawable.seat_normal)
                    Log.i("SeatAdpater cancel", holder.numberTv.text.toString());
                    callbackQueuq()
                } else {
                    //then click
                    holder.isClicked = true
                    while(queue.size>=howManySeats){
                        val temp=queue.pop()
                        temp.seatImg.setImageResource(R.drawable.seat_normal)
                        temp.isClicked=false
                    }
                    queue.add(holder)
                    holder.seatImg.setImageResource(R.drawable.seat_normal_selected)
                    Log.i("SeatAdpater click", holder.numberTv.text.toString());
                    callbackQueuq()
                }

                Log.i("queue ",queue.size.toString())
            }
        })
        return v
    }

    fun callbackQueuq(){
        val resultSeats=mutableListOf<String>()
        for(i in queue){
            resultSeats.add(i.numberTv.text.toString())
        }
        fragmentSeatPicker!!.requestSeat(resultSeats)
    }

    private fun getIndexByPosition(position: Int): Int {
        var subtract = (position - seatLine) / (seatEnd) + 1
        if (position < seatLine) subtract = 0
        return position - subtract
    }

    class Holder(v: View) {
        var numberTv: TextView
        var seatImg: ImageView
        var isClicked = false
        var state = 0
        var seatItem: CoordinatorLayout

        init {
            numberTv = v.findViewById(R.id.number)
            seatImg = v.findViewById(R.id.seat)
            seatItem = v.findViewById(R.id.seatItem)
        }


    }

    fun update(states: List<Int>) {
        this.states = states
        notifyDataSetChanged()
    }

    fun setFragmentRequest(fragmentP :FragmentSeatPicker){
        this.fragmentSeatPicker=fragmentP
    }

}