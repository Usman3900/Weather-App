package com.example.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stormy.R;
import com.example.stormy.dataModels.Minute;

import java.util.List;

public class minutelyAdapter extends RecyclerView.Adapter<minutelyAdapter.viewHolder> {

    private List<Minute> minutes;
    Context context;

    public minutelyAdapter(List<Minute> minutes, Context context) {
        this.minutes = minutes;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.minutelylayout, parent, false);

        return new viewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Minute minute = minutes.get(position);
        holder.temperature.setText(String.valueOf(minute.getTemperature()));
        holder.time.setText(String.valueOf(minute.getTime()));
        holder.rainChances.setText(String.valueOf(minute.getRainChances())+ "%");
        holder.icon.setImageResource(minute.getIcon());
    }

    @Override
    public int getItemCount() {
        return minutes.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {

        TextView time, temperature,rainChances;
        ImageView icon;

        public viewHolder(@NonNull View itemView) {

            super(itemView);
            time = itemView.findViewById(R.id.timeLabel);
            temperature = itemView.findViewById(R.id.temperatureView);
            rainChances = itemView.findViewById(R.id.summaryView);
            icon = itemView.findViewById(R.id.iconImageView);

        }


    }
}
