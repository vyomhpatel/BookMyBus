package b12app.vyom.com.bookmybus.view.confirmedticket


import android.content.ContentValues
import android.content.Context
import android.content.Intent
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
import android.widget.ScrollView
import b12app.vyom.com.bookmybus.utils.Mail
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import kotlinx.android.synthetic.main.confirmed_ticket.*
import java.sql.Time

import java.util.*
import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException
import kotlin.collections.ArrayList

class ConfirmedTicketFragment : Fragment() {

//    internal var tvConfirmedTicketNo: TextView? = null
//    internal var ivQRCode: ImageView? = null
//    internal var tvPersonName: TextView? = null
//    internal var tvTicketMonth: TextView? = null
//    internal var tvTicketDate: TextView? = null
//    internal var tvTicketDeparture: TextView? = null
//    internal var tvTicketArrival: TextView? = null
//    internal var tvTicketDuration: TextView? = null
//    internal var tvTicketAmt: TextView? = null
//    internal var tvTicketTS: TextView? = null
    var ticketConfirmedView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ticketConfirmedView = inflater.inflate(R.layout.confirmed_ticket, container, false)

        val datetime = Calendar.getInstance()
        val currenttime = datetime.time.toString()
        Log.i(TAG, "confirmed ticket: $currenttime")
        val multiFormatWriter = MultiFormatWriter()

        val tvperson = ticketConfirmedView!!.findViewById(R.id.tvPersonName) as TextView
        val ivQR = ticketConfirmedView!!.findViewById(R.id.ivQRCode) as ImageView
        tvperson.text="Vyomkumar Patel"

        try {
            val bitMatrix = multiFormatWriter.encode(currenttime, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)

            ivQR.setImageBitmap(bitmap)
            sendMessage()
            setCalenderEvent()
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return ticketConfirmedView
    }


    private fun setCalenderEvent() {



       val  intent =  Intent(Intent.ACTION_INSERT)
        intent.setData(CalendarContract.Events.CONTENT_URI)
        intent.putExtra(CalendarContract.Events.TITLE,"Calender Test")
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Ahemdabad")
        val start = GregorianCalendar(2018,5,8)
        val end = GregorianCalendar(2018,5,8)
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
        sendEmailAsyncTask.mail!!.body = getString(R.string.mail_msg)
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


}
