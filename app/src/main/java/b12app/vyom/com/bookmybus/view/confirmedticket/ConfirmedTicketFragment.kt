package b12app.vyom.com.bookmybus.view.confirmedticket


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

import b12app.vyom.com.bookmybus.R
import butterknife.BindView
import butterknife.ButterKnife

import android.support.constraint.Constraints.TAG
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.ScrollView
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.Mail
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import com.google.gson.Gson
import kotlinx.android.synthetic.main.confirmed_ticket.*
import java.sql.Time
import java.text.DateFormatSymbols

import java.util.*
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException
import kotlin.collections.ArrayList

class ConfirmedTicketFragment : Fragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        setCalenderEvent()
    }

    var tvConfirmedTicketNo: TextView? = null
    var ivQRCode: ImageView? = null
    var tvPersonName: TextView? = null
    var tvTicketMonth: TextView? = null
    var tvOBDate: TextView? = null
    var tvIBDate: TextView? = null
    var tvOBMonth: TextView? = null
    var tvIBMonth: TextView? = null
    var tvOBDeparture: TextView? = null
    var tvOBArrival: TextView? = null
    var tvOBDuration: TextView? = null
    var tvIBDeparture: TextView? = null
    var tvIBArrival: TextView? = null
    var tvIBDuration: TextView? = null
    var tvTicketAmt: TextView? = null
    var tvTicketTS: TextView? = null
    var ticketConfirmedView: View? = null
    var event_container1:LinearLayout?=null
    var event_container2:LinearLayout?=null
    var random:Random ?= null
    var mPrefs:SharedPreferences?=null
    var start_month = 0
    var end_month = 0
    var start_day = 0
    var end_day = 0
    var year = 2018
    private var toBusInfo: JBusByRoute.BusinformationBean? = null
    private var fromBusInfo: JBusByRoute.BusinformationBean? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ticketConfirmedView = inflater.inflate(R.layout.confirmed_ticket, container, false)

        val datetime = Calendar.getInstance()
        val currenttime = datetime.time.toString()
        Log.i(TAG, "confirmed ticket: $currenttime")
        val multiFormatWriter = MultiFormatWriter()

        mPrefs = activity!!.getSharedPreferences("bmb_shared",Context.MODE_PRIVATE)

        tvPersonName = ticketConfirmedView!!.findViewById(R.id.tvPersonName) as TextView
        ivQRCode = ticketConfirmedView!!.findViewById(R.id.ivQRCode) as ImageView
        event_container1 = ticketConfirmedView!!.findViewById(R.id.event_container1)
        event_container2 = ticketConfirmedView!!.findViewById(R.id.event_container2)
        tvConfirmedTicketNo = ticketConfirmedView!!.findViewById(R.id.tvConfirmedTicketNo)
        tvTicketAmt = ticketConfirmedView!!.findViewById(R.id.tvTicketAmt)
        tvOBArrival = ticketConfirmedView!!.findViewById(R.id.tvOBArrival)
        tvOBDeparture = ticketConfirmedView!!.findViewById(R.id.tvOBDeparture)
        tvOBDate = ticketConfirmedView!!.findViewById(R.id.tvOBDate)
        tvOBMonth = ticketConfirmedView!!.findViewById(R.id.tvOBMonth)
        tvIBMonth = ticketConfirmedView!!.findViewById(R.id.tvIBMonth)
        tvOBDuration = ticketConfirmedView!!.findViewById(R.id.tvOBDuration)
        tvIBDate = ticketConfirmedView!!.findViewById(R.id.tvIBDate)
        tvIBArrival = ticketConfirmedView!!.findViewById(R.id.tvIBArrival)
        tvIBDeparture = ticketConfirmedView!!.findViewById(R.id.tvIBDeparture)
        tvIBDuration = ticketConfirmedView!!.findViewById(R.id.tvIBDuration)
        tvTicketTS = ticketConfirmedView!!.findViewById(R.id.tvTicketTS)
        tvTicketAmt = ticketConfirmedView!!.findViewById(R.id.tvTicketAmt)



        start_day = mPrefs!!.getInt("start_day",1)
        tvOBDate!!.text = start_day.toString()

        end_day = mPrefs!!.getInt("end_day",1)
        tvIBDate!!.text = end_day.toString()

        start_month = mPrefs!!.getInt("start_month",1)
        tvOBMonth!!.text = getMonthForInt(start_month)
        start_day = mPrefs!!.getInt("end_month",1)
        tvIBMonth!!.text = getMonthForInt(mPrefs!!.getInt("end_month",6))


        setData()
        setBundleData()

        event_container1!!.setOnClickListener(this)
        event_container2!!.setOnClickListener(this)

        tvPersonName!!.text="Vyomkumar Patel"

        try {
            val bitMatrix = multiFormatWriter.encode(currenttime+" for "+tvPersonName!!.text.toString(), BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)

            ivQRCode!!.setImageBitmap(bitmap)
            tvTicketTS!!.text = currenttime



            notifyUser(bitmap)

            sendMessage()
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return ticketConfirmedView
    }

    private fun setBundleData() {
        var bundle = arguments
        tvTicketAmt!!.text = "₹ "+ bundle!!.getString("amount","")
    }

    private fun notifyUser(bitmap: Bitmap?) {

        val notification = NotificationCompat.Builder(activity!!.applicationContext)
                .setContentTitle("Wear Notification")
                .setSmallIcon(R.drawable.event_ic)
                .setContentText("Ticket Confirmed")
                .extend(NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true))
                .build()
        val notificationManager = activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val notificationId = 1
        notificationManager!!.notify(notificationId, notification)

    }

    private fun setData() {

        val gson = Gson()
        val route = mPrefs!!.getString("route", "")
        val return_route = mPrefs!!.getString("return_route", "")
        toBusInfo = gson.fromJson<JBusByRoute.BusinformationBean>(route, JBusByRoute.BusinformationBean::class.java)
        fromBusInfo = gson.fromJson<JBusByRoute.BusinformationBean>(return_route, JBusByRoute.BusinformationBean::class.java)

        if (toBusInfo != null && fromBusInfo != null) {
            Log.i("gson", toBusInfo!!.boardingtime.toString())
            tvOBDeparture!!.text = toBusInfo!!.boardingtime.toString()
            tvOBArrival!!.text = toBusInfo!!.dropingtime.toString()
            val duration1 = toBusInfo!!.journyduration.toString()
            tvOBDuration!!.text = duration1.substring(1, 8) + " HR"

            tvIBDeparture!!.text = fromBusInfo!!.boardingtime.toString()
            tvIBArrival!!.text = fromBusInfo!!.dropingtime.toString()
            val duration2 = fromBusInfo!!.journyduration.toString()
            tvOBDuration!!.text = duration2.substring(1, 8) + " HR"


            //clearing sharedpreferences for the next user.
            clearSharedPreference()
        }



    }

    private fun clearSharedPreference() {
        var editor = mPrefs!!.edit()
        editor.clear().commit()
    }


    private fun setCalenderEvent() {



       val  intent =  Intent(Intent.ACTION_INSERT)
        intent.setData(CalendarContract.Events.CONTENT_URI)
        intent.putExtra(CalendarContract.Events.TITLE,"Calender Test")
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Ahemdabad")
        val start = GregorianCalendar(2018,start_month,start_day)
        val end = GregorianCalendar(2018,end_month,end_day)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,start.timeInMillis)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,end.timeInMillis)
        startActivity(intent)


    }

    private fun sendMessage() {
        val recipients = arrayOf(getString(R.string.recipient))
        val sendEmailAsyncTask = SendEmailAsyncTask()
        sendEmailAsyncTask.activity = activity
        sendEmailAsyncTask.mail = Mail(getString(R.string.mail_from_username), getString(R.string.mail_from_p))
        sendEmailAsyncTask.mail!!._from = getString(R.string.mail_from_username)
        sendEmailAsyncTask.mail!!.body = getString(R.string.mail_msg)+" "+getMonthForInt(start_month)+" "+start_day+", 2018 from "+mPrefs!!.getString("from_city","") +" to " +mPrefs!!.getString("to_city","") +" "+
                getString(R.string.greeting)
        sendEmailAsyncTask.mail!!._to = recipients
        sendEmailAsyncTask.mail!!._subject = getString(R.string.mail_sub)
        sendEmailAsyncTask.execute()
    }

    fun displayMessage(message: String) {
        Snackbar.make(ticketConfirmedView!!.findViewById(R.id.cofirmedTicketContainer), message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    internal inner class SendEmailAsyncTask : AsyncTask<Void, Void, Boolean>() {
        var mail: Mail? = null
        var activity: Context? = null

        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                if (mail!!.send()) {
                    displayMessage("Email sent.")
                } else {
                    displayMessage("Email failed to send.")
                }

                return true
            } catch (e: AuthenticationFailedException) {
                Log.e(SendEmailAsyncTask::class.java.name, "Bad account details")
                e.printStackTrace()
                displayMessage("Authentication failed.")
                return false
            } catch (e: MessagingException) {
                Log.e(SendEmailAsyncTask::class.java.name, "Email failed")
                e.printStackTrace()
                displayMessage("Email failed to send.")
                return false
            } catch (e: Exception) {
                e.printStackTrace()
                displayMessage("Unexpected error occured.")
                return false
            }

        }
    }

    fun getMonthForInt(m: Int): String {
        var month = "invalid"
        val dfs = DateFormatSymbols()
        val months = dfs.getMonths()
        if (m >= 0 && m <= 11) {
            month = months[m]
        }
        return month
    }


}
