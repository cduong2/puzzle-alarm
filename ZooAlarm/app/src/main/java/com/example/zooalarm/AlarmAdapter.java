package com.example.zooalarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooalarm.database.Alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private List<Alarm> alarms = new ArrayList<>();
    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item, parent, false);
        return new AlarmHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        Alarm currentAlarm = alarms.get(position);
        holder.textViewTitle.setText(String.valueOf(currentAlarm.getTime()));
        holder.textViewDescription.setText(String.valueOf(currentAlarm.getActId()));
        holder.textViewPriority.setText(String.valueOf(currentAlarm.getRepeat()));
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public AlarmHolder (View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
        }
    }
}
