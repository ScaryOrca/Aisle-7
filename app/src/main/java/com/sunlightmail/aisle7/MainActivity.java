package com.sunlightmail.aisle7;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginTest myLogin = new LoginTest();
        myLogin.execute();
    }

    // Login Test
    public class LoginTest extends AsyncTask<Void, Void, Boolean> {
        private AssociateConnectionSession mySession;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mySession = new AssociateConnectionSession();
                mySession.login("1810185", "Mississippi1!");
                Log.d("!!!!!", "Login worked");
            } catch (LoginErrorException e) {
                e.printStackTrace();
                Log.d("!!!!!", "Login failed");
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                Log.d("!!!!!", "We're in business, boys.");
            }
        }
    }
}
