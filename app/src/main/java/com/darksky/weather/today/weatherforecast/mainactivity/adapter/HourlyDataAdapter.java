package com.darksky.weather.today.weatherforecast.mainactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ListHourlyDataBinding;
import com.darksky.weather.today.weatherforecast.mainactivity.constant.Const;
import com.darksky.weather.today.weatherforecast.mainactivity.model.hours24.Hours24ResponseItem;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class HourlyDataAdapter extends RecyclerView.Adapter<HourlyDataAdapter.MyClassView> {

    ArrayList<Hours24ResponseItem> hours24ResponseItems;
    Activity activity;

    public HourlyDataAdapter(ArrayList<Hours24ResponseItem> hours24ResponseItems, Activity activity) {
        this.hours24ResponseItems = hours24ResponseItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyClassView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ListHourlyDataBinding listHourlyDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_hourly_data, parent, false);

        return new MyClassView(listHourlyDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyDataAdapter.MyClassView holder, int position) {
        Hours24ResponseItem hours24ResponseItem = hours24ResponseItems.get(position);

        DateTime dt = new DateTime(hours24ResponseItem.getDateTime());
        holder.listHourlyDataBinding.tvTime.setText(dt.getHourOfDay() + ":" + "00");
        holder.listHourlyDataBinding.tvFellTemp.setText("" + Math.round(Double.parseDouble(String.valueOf(hours24ResponseItem.getTemperature().getValue()))) +
                (char) 0x00B0 + hours24ResponseItem.getTemperature().getUnit());
        holder.listHourlyDataBinding.imgIcon.setImageResource(Const.weatherIcon[hours24ResponseItem.getWeatherIcon() - 1]);
    }

    @Override
    public int getItemCount() {
        return hours24ResponseItems.size();
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        private final ListHourlyDataBinding listHourlyDataBinding;

        public MyClassView(ListHourlyDataBinding listHourlyDataBinding) {
            super(listHourlyDataBinding.getRoot());
            this.listHourlyDataBinding = listHourlyDataBinding;
        }
    }
}
