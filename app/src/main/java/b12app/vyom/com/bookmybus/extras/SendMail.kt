package b12app.vyom.com.bookmybus.extras

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import javax.mail.AuthenticationFailedException
import javax.mail.MessagingException

import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.utils.Mail

class SendMail : Fragment() {

    var v: View? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        v = inflater.inflate(R.layout.mail_test, container, false)
        sendMessage()
        return v
    }

    private fun sendMessage() {
        val recipients = arrayOf("mr.vyompatel@gmail.com")
        val sendEmailAsyncTask = SendEmailAsyncTask()
        sendEmailAsyncTask.activity = activity
        sendEmailAsyncTask.mail = Mail("vyomhpatel@gmail.com", "WelcomeVyom")
        sendEmailAsyncTask.mail!!._from = "vyomhpatel@gmail.com"
        sendEmailAsyncTask.mail!!.body = "Mail 5"
        sendEmailAsyncTask.mail!!._to = recipients
        sendEmailAsyncTask.mail!!._subject = "Test Subject"
        sendEmailAsyncTask.execute()
    }

    fun displayMessage(message: String) {
        Snackbar.make(v!!.findViewById(R.id.test_mail_container), message, Snackbar.LENGTH_LONG)
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
