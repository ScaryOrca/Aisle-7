package com.sunlightmail.aisle7;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by matthew on 8/31/17.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private List<ListDate> listDates;
    private Context context;

    public DateAdapter(List<ListDate> listDates, Context context) {
        this.listDates = listDates;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dates_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListDate listDate = listDates.get(position);

        holder.weekDay.setText(listDate.getWeekDay());
        holder.day.setText(listDate.getDate());
        holder.time.setText(listDate.getTime());
    }

    @Override
    public int getItemCount() {
        return listDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView weekDay;
        public TextView time;
        public TextView day;

        public ViewHolder(View itemView) {
            super(itemView);

            weekDay = (TextView) itemView.findViewById(R.id.weekDayTextView);
            time = (TextView) itemView.findViewById(R.id.timeTextView);
            day = (TextView) itemView.findViewById(R.id.dateTextView);
        }
    }
}
