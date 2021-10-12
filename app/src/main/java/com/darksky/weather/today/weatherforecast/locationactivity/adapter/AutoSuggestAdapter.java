package com.darksky.weather.today.weatherforecast.locationactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata.SearchData;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AutoSuggestAdapter extends ArrayAdapter<SearchData> implements Filterable {
    private final ArrayList<SearchData> mlistData;
    private final ArrayList<SearchData> tempItems;
    private final ArrayList<SearchData> suggestions;
    Context context;
    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((SearchData) resultValue).getCity();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SearchData people : tempItems) {
                    String name = people.getCity();
                    if (name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            try {
                ArrayList<SearchData> filterList = (ArrayList<SearchData>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (SearchData people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }

            } catch (ConcurrentModificationException e) {
            }

        }
    };


    public AutoSuggestAdapter(Context context, int resource, int textViewResourceId, ArrayList<SearchData> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        mlistData = items;
        tempItems = new ArrayList<SearchData>(items); // this makes the difference.
        suggestions = new ArrayList<SearchData>();
    }

    public void setData(ArrayList<SearchData> list) {
        mlistData.clear();
        notifyDataSetChanged();
        mlistData.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Nullable
    @Override
    public SearchData getItem(int position) {
        return mlistData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_search_auto, parent, false);
        }
        SearchData people = mlistData.get(position);
        if (people != null) {
            TextView lblName = view.findViewById(R.id.text1);
            if (lblName != null)
                lblName.setText(people.getCity() + "," + people.getCountry());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }
}
