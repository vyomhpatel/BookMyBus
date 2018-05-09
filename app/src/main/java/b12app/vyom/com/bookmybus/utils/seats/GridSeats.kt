package b12app.vyom.com.bookmybus.utils.seats

import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.widget.GridView

class GridSeats(context: Context, private var list:List<Int>) : GridView(context) {
    var seatAdapter=SeatAdapter(list,context)


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 5, View.MeasureSpec.AT_MOST)
        super.onMeasure(expandSpec, heightSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
    fun drawSeat(){
        setAdapter(seatAdapter)
    }
    fun updateSeat(list:List<Int>){
        seatAdapter.update(list)
    }
}