package b12app.vyom.com.bookmybus.view.bookticket

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentsClient

import b12app.vyom.com.bookmybus.R
import b12app.vyom.com.bookmybus.model.JBusByRoute
import b12app.vyom.com.bookmybus.utils.PaymentsUtil
import b12app.vyom.com.bookmybus.view.confirmedticket.ConfirmedTicketFragment
import b12app.vyom.com.bookmybus.view.seatpick.FragmentSeatPicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.confirmed_ticket.*
import kotlinx.android.synthetic.main.ticket_checkout.*
import org.w3c.dom.Text
import java.text.DateFormatSymbols

class BookTicketFragment : Fragment(), View.OnClickListener {

    private var mPaymentsClient: PaymentsClient? = null

    private var mGooglePayButton: View? = null

    private var seatNo: TextView? = null

    private var fragmentSeatPicker: FragmentSeatPicker? = null

    private var mPrefs: SharedPreferences? = null

    private var toBusInfo: JBusByRoute.BusinformationBean? = null
    private var fromBusInfo: JBusByRoute.BusinformationBean? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val bookTicketView = inflater.inflate(R.layout.ticket_checkout, container, false)
        var tvCartTotal = bookTicketView.findViewById(R.id.tvCartTotal) as TextView
        var tvCheckIBDuration = bookTicketView.findViewById(R.id.tvCheckIBDuration) as TextView
        var tvCheckIBArrival = bookTicketView.findViewById(R.id.tvCheckIBArrival) as TextView
        var tvCheckIBDeparture = bookTicketView.findViewById(R.id.tvCheckIBDeparture) as TextView
        var tvCheckOBArrival = bookTicketView.findViewById(R.id.tvCheckOBArrival) as TextView
        var tvCheckOBDuration = bookTicketView.findViewById(R.id.tvCheckOBDuration) as TextView
        var tvCheckOBDeparture = bookTicketView.findViewById(R.id.tvCheckOBDeparture) as TextView
        var tvCheckOBDate = bookTicketView.findViewById(R.id.tvCheckOBDate) as TextView
        var tvCheckIBDate = bookTicketView.findViewById(R.id.tvCheckIBDate) as TextView
        var tvCheckOBMonth = bookTicketView.findViewById(R.id.tvCheckOBMonth) as TextView
        var tvCheckIBMonth = bookTicketView.findViewById(R.id.tvCheckIBMonth) as TextView
        var etCheckTotalNotax:TextView = bookTicketView.findViewById(R.id.etCheckTotalNotax)

        var btnBookTicket = bookTicketView.findViewById(R.id.btnBookTicket) as Button
        btnBookTicket.setOnClickListener(this)


        //getting the values from shared-preferences and displaying it to the user.
        mPrefs = activity!!.getSharedPreferences(getString(b12app.vyom.com.bookmybus.R.string.shared_pref_title), android.content.Context.MODE_PRIVATE)

        tvCheckOBDate.text = mPrefs!!.getInt("start_day",1).toString()
        tvCheckOBMonth.text = getMonthForInt(mPrefs!!.getInt("start_month",6))
        tvCheckIBDate.text = mPrefs!!.getInt("end_day",1).toString()
        tvCheckIBMonth.text = getMonthForInt(mPrefs!!.getInt("end_month",6))

        val gson = Gson()
        val route = mPrefs!!.getString("route", "")
        val return_route = mPrefs!!.getString("return_route", "")
        toBusInfo = gson.fromJson<JBusByRoute.BusinformationBean>(route, JBusByRoute.BusinformationBean::class.java)
        fromBusInfo = gson.fromJson<JBusByRoute.BusinformationBean>(return_route, JBusByRoute.BusinformationBean::class.java)

        if (toBusInfo != null && fromBusInfo != null) {
            Log.i("gson", toBusInfo!!.boardingtime.toString())
            tvCheckOBDeparture.text = toBusInfo!!.boardingtime.toString()
            tvCheckOBArrival.text = toBusInfo!!.dropingtime.toString()
            val duration1 = toBusInfo!!.journyduration.toString()

            tvCheckOBDuration.text = duration1.substring(1, 8) + " HR"

            tvCheckIBDeparture.text = fromBusInfo!!.boardingtime.toString()
            tvCheckIBArrival.text = fromBusInfo!!.dropingtime.toString()
            val duration2 = fromBusInfo!!.journyduration.toString()
            tvCheckIBDuration.text =  duration2.substring(1, 8) + " HR"
        }
        val total = Integer.valueOf(toBusInfo!!.fare) + Integer.valueOf(fromBusInfo!!.fare)
        etCheckTotalNotax!!.text = "₹" +total.toString()
        tvCartTotal!!.text = "₹ "+ total.toString()

        mGooglePayButton = bookTicketView.findViewById(R.id.googlepay_button)

        mGooglePayButton!!.setOnClickListener(this)

        seatNo = bookTicketView.findViewById(R.id.seatNo)
        seatNo!!.setOnClickListener(this)
        mPaymentsClient = PaymentsUtil.createPaymentsClient(activity)
        checkIsReadyToPay()

        return bookTicketView
    }

    private fun checkIsReadyToPay() {
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        PaymentsUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener { task ->
            try {
                val result = task.getResult(ApiException::class.java)
                setGooglePayAvailable(result)
            } catch (exception: ApiException) {
                // Process error
                Log.w("isReadyToPay failed", exception)
            }
        }
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.googlepay_button -> requestPayment(v)
            R.id.seatNo -> openSeatDialog()
            R.id.btnBookTicket -> checkoutTicket()
        }


    }

    private fun checkoutTicket() {
       var confirmedTicketFragment = ConfirmedTicketFragment() as ConfirmedTicketFragment
        var bundle:Bundle = Bundle()
        bundle.putString("amount",tvCartTotal.text.toString())
        confirmedTicketFragment.arguments = bundle
        fragmentManager!!.beginTransaction().replace(R.id.testFrame,confirmedTicketFragment,"checkout complete").commit()
    }

    private fun openSeatDialog() {

        fragmentSeatPicker = FragmentSeatPicker();
        fragmentSeatPicker!!.getSeatNo(this)
        fragmentSeatPicker!!.show(activity!!.supportFragmentManager, "test")

    }

    private fun setGooglePayAvailable(available: Boolean) {
        // If isReadyToPay returned true, show the button and hide the "checking" text. Otherwise,
        // notify the user that Pay with Google is not available.
        // Please adjust to fit in with your current user flow. You are not required to explicitly
        // let the user know if isReadyToPay returns false.
        if (available) {
            //            mGooglePayStatusText.setVisibility(View.GONE);
            mGooglePayButton!!.visibility = View.VISIBLE
        } else {
            //            mGooglePayStatusText.setText(R.string.googlepay_status_unavailable);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val paymentData = PaymentData.getFromIntent(data!!)
                        handlePaymentSuccess(paymentData!!)
                    }
                    Activity.RESULT_CANCELED -> {
                    }
                    AutoResolveHelper.RESULT_ERROR -> {
                        val status = AutoResolveHelper.getStatusFromIntent(data)
                        handleError(status!!.statusCode)
                    }
                }// Nothing to here normally - the user simply cancelled without selecting a
                // payment method.

                // Re-enables the Pay with Google button.
                mGooglePayButton!!.isClickable = true
            }
        }
    }

    private fun handlePaymentSuccess(paymentData: PaymentData) {
        // PaymentMethodToken contains the payment information, as well as any additional
        // requested information, such as billing and shipping address.
        //
        // Refer to your processor's documentation on how to proceed from here.
        val token = paymentData.paymentMethodToken

        // getPaymentMethodToken will only return null if PaymentMethodTokenizationParameters was
        // not set in the PaymentRequest.
        if (token != null) {
            // If the gateway is set to example, no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (token.token == "examplePaymentMethodToken") {
                val alertDialog = AlertDialog.Builder(activity)
                        .setTitle("Warning")
                        .setMessage("Gateway name set to \"example\" - please modify " + "Constants.java and replace it with your own gateway.")
                        .setPositiveButton("OK", null)
                        .create()
                alertDialog.show()
            }

            val billingName = paymentData.cardInfo.billingAddress!!.name


            // Use token.getToken() to get the token string.
            Log.d("PaymentData", "PaymentMethodToken received")
        }
    }

    private fun handleError(statusCode: Int) {
        // At this stage, the user has already seen a popup informing them an error occurred.
        // Normally, only logging is required.
        // statusCode will hold the value of any constant from CommonStatusCode or one of the
        // WalletConstants.ERROR_CODE_* constants.
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode))
    }

    fun requestPayment(view: View) {
        // Disables the button to prevent multiple clicks.
        mGooglePayButton!!.isClickable = false

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        val price = PaymentsUtil.microsToString(1000)

        val transaction = PaymentsUtil.createTransaction(price)
        val request = PaymentsUtil.createPaymentDataRequest(transaction)
        val futurePaymentData = mPaymentsClient!!.loadPaymentData(request)

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(futurePaymentData, activity!!, LOAD_PAYMENT_DATA_REQUEST_CODE)
    }

    fun getSeatDetails(seatno: String) {
        seatNo!!.text = "S " + seatno


    }

    companion object {

        private val LOAD_PAYMENT_DATA_REQUEST_CODE = 991
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
