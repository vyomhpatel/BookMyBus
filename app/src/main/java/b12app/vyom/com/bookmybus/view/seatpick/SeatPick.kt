package b12app.vyom.com.bookmybus.view.seatpick

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.adapters.SeatAdapter
import kotlinx.android.synthetic.main.activity_seat_pick.*

class SeatPick : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val seatState= listOf(0,0,0,0,0,1,1,1,1,2,2,2,0,0,0,0,0,0,0)
        setContentView(R.layout.activity_seat_pick)
        var seatAdapter=SeatAdapter(seatState,this)
        seats.setAdapter(seatAdapter)
    }
}
