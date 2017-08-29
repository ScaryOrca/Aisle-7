package com.sunlightmail.aisle7;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String userId;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        });

        /*******************************
        Begin Login Test
        *******************************/
        //LoginTest myLogin = new LoginTest();
        //myLogin.execute();
        /*******************************
         End Login Test
         *******************************/
    }

    // Login Test
    public class LoginTest extends AsyncTask<Void, Void, Boolean> {
        private AssociateConnectionSession mySession;

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mySession = new AssociateConnectionSession();
                Log.d("!!!!!", "First attempt, starting.");
                mySession.login(userId, userPassword);
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
