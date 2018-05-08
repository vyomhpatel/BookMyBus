package b12app.vyom.com.bookmybus.view.bookticket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.TransactionInfo;

import b12app.vyom.com.bookmybus.R;
import b12app.vyom.com.bookmybus.utils.PaymentsUtil;

public class BookTicketFragment extends Fragment implements View.OnClickListener {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private PaymentsClient mPaymentsClient;

    private View mGooglePayButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View bookTicketView = inflater.inflate(R.layout.ticket_checkout,container,false);

        mGooglePayButton = bookTicketView.findViewById(R.id.googlepay_button);

        mGooglePayButton.setOnClickListener(this);
        mPaymentsClient = PaymentsUtil.createPaymentsClient(getActivity());
        checkIsReadyToPay();

        return bookTicketView;
    }

    private void checkIsReadyToPay() {
        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        PaymentsUtil.isReadyToPay(mPaymentsClient).addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result = task.getResult(ApiException.class);
                            setGooglePayAvailable(result);
                        } catch (ApiException exception) {
                            // Process error
                            Log.w("isReadyToPay failed", exception);
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.googlepay_button:
            requestPayment(v);
            break;
        }
    }

    private void setGooglePayAvailable(boolean available) {
        // If isReadyToPay returned true, show the button and hide the "checking" text. Otherwise,
        // notify the user that Pay with Google is not available.
        // Please adjust to fit in with your current user flow. You are not required to explicitly
        // let the user know if isReadyToPay returns false.
        if (available) {
//            mGooglePayStatusText.setVisibility(View.GONE);
            mGooglePayButton.setVisibility(View.VISIBLE);
        } else {
//            mGooglePayStatusText.setText(R.string.googlepay_status_unavailable);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;
                    case Activity.RESULT_CANCELED:
                        // Nothing to here normally - the user simply cancelled without selecting a
                        // payment method.
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Pay with Google button.
                mGooglePayButton.setClickable(true);
                break;
        }
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        // PaymentMethodToken contains the payment information, as well as any additional
        // requested information, such as billing and shipping address.
        //
        // Refer to your processor's documentation on how to proceed from here.
        PaymentMethodToken token = paymentData.getPaymentMethodToken();

        // getPaymentMethodToken will only return null if PaymentMethodTokenizationParameters was
        // not set in the PaymentRequest.
        if (token != null) {
            // If the gateway is set to example, no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (token.getToken().equals("examplePaymentMethodToken")) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Warning")
                        .setMessage("Gateway name set to \"example\" - please modify " +
                                "Constants.java and replace it with your own gateway.")
                        .setPositiveButton("OK", null)
                        .create();
                alertDialog.show();
            }

            String billingName = paymentData.getCardInfo().getBillingAddress().getName();


            // Use token.getToken() to get the token string.
            Log.d("PaymentData", "PaymentMethodToken received");
        }
    }

    private void handleError(int statusCode) {
        // At this stage, the user has already seen a popup informing them an error occurred.
        // Normally, only logging is required.
        // statusCode will hold the value of any constant from CommonStatusCode or one of the
        // WalletConstants.ERROR_CODE_* constants.
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    public void requestPayment(View view) {
        // Disables the button to prevent multiple clicks.
        mGooglePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        String price = PaymentsUtil.microsToString(1000);

        TransactionInfo transaction = PaymentsUtil.createTransaction(price);
        PaymentDataRequest request = PaymentsUtil.createPaymentDataRequest(transaction);
        Task<PaymentData> futurePaymentData = mPaymentsClient.loadPaymentData(request);

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        AutoResolveHelper.resolveTask(futurePaymentData, getActivity(), LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

}
