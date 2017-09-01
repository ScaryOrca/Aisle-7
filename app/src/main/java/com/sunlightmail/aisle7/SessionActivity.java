package com.sunlightmail.aisle7;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListDate>  listDates;
    private AssociateConnectionSession session;
    private WorkCalendar wc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        LinearLayoutManager lm = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.datesRecyclerView);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                lm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(lm);

        listDates = new ArrayList<>();

        // Dummy Data
        /*for(int i = 0; i < 30; i++) {
            ListDate listDate = new ListDate("MON", "12:00AM - 12:00PM", "September " + (i + 1));
            listDates.add(listDate);
        }*/

        // Real Data
        Intent mIntent = getIntent();
        Map<String, String> cookies = (Map<String, String>) mIntent.getSerializableExtra("cookies");
        session = new AssociateConnectionSession();
        session.resumeSession(cookies);

        GetCalendar myCalGen = new GetCalendar();
        myCalGen.execute();
        //WorkCalendar wc = session.getMonthView();
    }

    public class GetCalendar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            wc = session.getMonthView();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            listDates = new ArrayList<>();
            for(WorkDay wd: wc.getCalendar()) {
                String currentDay = wd.getWeekDay();
                String currentTime = wd.getTime();
                String currentDate = wd.getDate();
                Log.d("!!!!!", currentDate + " " + currentTime + " " + currentDay);
                ListDate listDate = new ListDate(currentDay, currentTime, "September " + (currentDate));
                listDates.add(listDate);
            }
            adapter = new DateAdapter(listDates, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }
}
