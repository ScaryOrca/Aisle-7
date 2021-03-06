package com.sunlightmail.aisle7;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Map;

public class LoginFragment extends Fragment {
    private AssociateConnectionSession mySession;
    private String userId;
    private String userPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final Button loginButton = (Button) view.findViewById(R.id.loginButton);
        final EditText userIdEditText = (EditText) view.findViewById(R.id.userIdEditText);
        final EditText userPasswordEditText = (EditText) view.findViewById(R.id.userPasswordEditText);

        // Testing Calendar View
        //Intent intent = new Intent(((MainActivity)getActivity()), SessionActivity.class);
        //startActivity(intent);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setViewPager(1);
                userId = userIdEditText.getText().toString();
                userPassword = userPasswordEditText.getText().toString();
                final LoginTest myLogin = new LoginTest();

                // Trick to remove focus from inputs, don't want keyboard open on next fragment.
                userIdEditText.setEnabled(false);
                userPasswordEditText.setEnabled(false);

                myLogin.execute();

                // Enable focus, in case credentials need to be changed.
                userIdEditText.setEnabled(true);
                userPasswordEditText.setEnabled(true);
            }
        });

        return view;
    }

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
            //WorkCalendar testC = mySession.getMonthView();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                //String test = mySession.getMonthView().getCalendar().get(28).getTime();
                Toast.makeText(getActivity(), "Login was successful!", Toast.LENGTH_LONG).show();
                //((MainActivity)getActivity()).setViewPager(0);
                Map<String, String> myCookies = mySession.getCookies();
                Intent intent = new Intent(((MainActivity)getActivity()), SessionActivity.class);
                intent.putExtra("cookies", (Serializable) myCookies);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
            }
            else {
                ((MainActivity)getActivity()).setViewPager(0);

                Toast.makeText(getActivity(), "Error: Check login information.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
