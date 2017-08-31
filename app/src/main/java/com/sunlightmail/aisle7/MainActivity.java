package com.sunlightmail.aisle7;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private String userId;
    private String userPassword;
    private AssociateConnectionSession mySession;
    private SectionsStatePagerAdapter mSectionsStatePageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.fragmentContainer);
        setupViewPager(mViewPager);


        /*
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final EditText userIdEditText = (EditText) findViewById(R.id.userIdEditText);
        final EditText userPasswordEditText = (EditText) findViewById(R.id.userPasswordEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userId = userIdEditText.getText().toString();
                userPassword = userPasswordEditText.getText().toString();
                final LoginTest myLogin = new LoginTest();

                myLogin.execute();
            }
        });*/

        /*******************************
        Begin Login Test
        *******************************/
        //LoginTest myLogin = new LoginTest();
        //myLogin.execute();
        /*******************************
         End Login Test
         *******************************/
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new LoadingFragment());
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }

    // Login Test
    public class LoginTest extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mySession = new AssociateConnectionSession();
                mySession.login(userId, userPassword);
            } catch (LoginErrorException e) {
                e.printStackTrace();
                return false;
            }
            WorkCalendar testC = mySession.getMonthView();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                //String test = mySession.getMonthView().getCalendar().get(28).getTime();
                Toast.makeText(getApplicationContext(), "Login was successful!", Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error: Check login information.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
