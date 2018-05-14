package b12app.vyom.com.bookmybus.view.seatpick

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.SeatAdapter
import b12app.vyom.com.bookmybus.view.bookticket.BookTicketFragment
import kotlinx.android.synthetic.main.activity_seat_pick.*

class FragmentSeatPicker: android.support.v4.app.DialogFragment() {

    private var seats:GridView? = null
    private var bookTicketFragment:BookTicketFragment?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        var view = inflater!!.inflate(R.layout.activity_seat_pick,container,false)
        val seatState= listOf(0,0,0,0,0,1,1,1,1,2,2,2,0,0,0,0,0,0,0)
        var seatAdapter= SeatAdapter(seatState,context!!)
        seatAdapter.setFragmentRequest(this)
        seats = view!!.findViewById(R.id.seats)
        seats!!.setAdapter(seatAdapter)


        return view
    }

    fun requestSeat(seatNo: String) {
        bookTicketFragment!!.getSeatDetails(seatNo)
        this.dismiss()

    }

    fun getSeatNo(bookTicketFragment: BookTicketFragment) {

        this.bookTicketFragment = bookTicketFragment

    }





}