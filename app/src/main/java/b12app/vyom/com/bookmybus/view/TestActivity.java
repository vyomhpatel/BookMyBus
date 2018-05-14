package b12app.vyom.com.bookmybus.view;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import b12app.vyom.com.bookmybus.R;
import b12app.vyom.com.bookmybus.extras.SendMail;
import b12app.vyom.com.bookmybus.view.bookticket.BookTicketFragment;
import b12app.vyom.com.bookmybus.view.confirmedticket.ConfirmedTicketFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.testFrame)
    FrameLayout testFrame;
    Toolbar tbTest;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tbTest=findViewById(R.id.tbTest);
        mPreferences = getSharedPreferences(getString(R.string.shared_pref_title),MODE_PRIVATE);

        initToolbar();
        BookTicketFragment bookTicketFragment = new BookTicketFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.testFrame,bookTicketFragment,"test").commit();
    }

    private void initToolbar() {
        tbTest.setNavigationIcon(R.drawable.back_ic);
        setSupportActionBar(tbTest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mPreferences.getString("from_city","")+
                " - "
                +mPreferences.getString("to_city",""));
    }
}
