package com.darksky.weather.today.weatherforecast.locationactivity.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ListItemLocationBinding;
import com.darksky.weather.today.weatherforecast.databinding.ListItemSearchHeaderBinding;
import com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata.AddLocationData;
import com.darksky.weather.today.weatherforecast.mainactivity.model.locationdata.SearchData;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class LocationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private final FirebaseAnalytics mFirebaseAnalytics;
    ArrayList<AddLocationData> mList = new ArrayList<>();
    Activity mContext;
    Type type = new TypeToken<List<AddLocationData>>() {
    }.getType();
    ArrayList<SearchData> serarchDatas;
    private AutoSuggestAdapter autoSuggestAdapter;
    private Handler handler;

    public LocationListAdapter(Activity mContext, ArrayList<AddLocationData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        AndroidNetworking.initialize(mContext);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            ListItemSearchHeaderBinding listItemSearchHeaderBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_search_header, parent, false);
            return new HeaderViewHolder(listItemSearchHeaderBinding);

        } else if (viewType == TYPE_ITEM) {
            ListItemLocationBinding listItemLocationBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_location, parent, false);
            return new ViewHolder(listItemLocationBinding);

        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        try {

            int viewType = getItemViewType(position);
            switch (viewType) {
                case TYPE_HEADER:
                    final HeaderViewHolder searchViewHolder = (HeaderViewHolder) holder;

                    searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    serarchDatas = new ArrayList<>();
                    autoSuggestAdapter = new AutoSuggestAdapter(mContext, R.layout.list_item_search_auto, R.id.text1, new ArrayList<>());
                    searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.setThreshold(2);
                    searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.setAdapter(autoSuggestAdapter);

                    searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int pos, long id) {
                                    ArrayList<AddLocationData> addLocationDataArrayList;
                                    if (!Preference.getDefaultLocationListInfo(mContext).equals("")) {
                                        addLocationDataArrayList = new Gson().fromJson(Preference.getDefaultLocationListInfo(mContext), type);
                                    } else {
                                        addLocationDataArrayList = new ArrayList<>();
                                    }
                                    AddLocationData addLocationData = new AddLocationData();
                                    addLocationData.setKey(serarchDatas.get(pos).getKey());
                                    addLocationData.setCity(serarchDatas.get(pos).getCity());
                                    addLocationData.setCountry(serarchDatas.get(pos).getCountry());
                                    addLocationData.setArea(serarchDatas.get(pos).getArea());
                                    addLocationData.setDaynight(0);
                                    addLocationDataArrayList.add(1, addLocationData);
                                    Preference.setDefaultLocationInfo(mContext, new Gson().toJson(addLocationDataArrayList));
                                    mList.add(2, addLocationData);
                                    notifyDataSetChanged();
                                }
                            });


                    searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int
                                count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before,
                                                  int count) {
                            if (s.length() > 2) {
                                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                                        AUTO_COMPLETE_DELAY);
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    handler = new Handler(msg -> {
                        if (msg.what == TRIGGER_AUTO_COMPLETE) {
                            if (!TextUtils.isEmpty(searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.getText())) {
                                fireAnalytics("SearchLocation", "text");
                                fireAnalytics("SearchLocation", searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.getText().toString());
                                searchQuery(searchViewHolder.listItemSearchHeaderBinding.autoCompleteEditText.getText().toString());
                            }
                        }
                        return false;
                    });

                    break;
                case TYPE_ITEM:
                    ViewHolder itemholder = (ViewHolder) holder;
                    if (position == 1) {
                        itemholder.listItemLocationBinding.ivDelete.setVisibility(View.GONE);
                    } else {
                        itemholder.listItemLocationBinding.ivDelete.setVisibility(View.VISIBLE);
                    }
                    if (position == 1) {
                        itemholder.listItemLocationBinding.tvlocationName.setText("Current location");
                        itemholder.listItemLocationBinding.tvarea.setText(mList.get(position).getArea());
                    } else {
                        itemholder.listItemLocationBinding.tvlocationName.setText(mList.get(position).getCity());
                        itemholder.listItemLocationBinding.tvarea.setText(mList.get(position).getArea() + ", " + mList.get(position).getCountry());
                    }

                    itemholder.listItemLocationBinding.ivDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("LLLLL_BEFORE: ", String.valueOf(mList.size()));
                            mList.remove(position);
                            Preference.setDefaultLocationInfo(mContext, new Gson().toJson(mList));
                            mList.clear();
                            mList = new ArrayList<>();
                            Log.e("LLLLL_AFTER: ", String.valueOf(Preference.getDefaultLocationListInfo(mContext)));
                            if (!Preference.getDefaultLocationListInfo(mContext).equals("")) {
                                AddLocationData addLocationData = new AddLocationData();
                                mList = new Gson().fromJson(Preference.getDefaultLocationListInfo(mContext), type);
                                mList.remove(0);
                                mList.add(0, addLocationData);
                            } else {
                                mList = new ArrayList<>();
                                AddLocationData addLocationData = new AddLocationData();
                                mList.add(0, addLocationData);
                            }

                            notifyDataSetChanged();
                        }
                    });

                    itemholder.listItemLocationBinding.loutMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*if (position == 1) {
                                Utils.saveUnitToDefaults(mContext, Constant.IS_DEAFAULT_LOCATION, 0);
                            } else {
                                Utils.saveUnitToDefaults(mContext, Constant.IS_DEAFAULT_LOCATION, 1);
                            }*/
//                            YandexMetrica.getReporter(holder.itemView.getContext(), holder.itemView.getContext().getString(R.string.app_key)).reportEvent("Search City", mList.get(position).getCountry());
                            Preference.setKey(autoSuggestAdapter.context, mList.get(position).getKey());
                            Intent intent = new Intent();
                            intent.putExtra("key", mList.get(position).getKey());
                            intent.putExtra("name", mList.get(position).getCity() + "," + mList.get(position).getCountry());
                            mContext.setResult(Activity.RESULT_OK, intent);
                            mContext.finish();
                        }
                    });

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // Add two more counts to accomodate header and footer
        return this.mList.size();
    }

    private void searchQuery(String query) {
        serarchDatas.clear();
        autoSuggestAdapter.setData(serarchDatas);
        autoSuggestAdapter.notifyDataSetChanged();
        AndroidNetworking.get("https://api.accuweather.com/locations/v1/cities/autocomplete")
                .addQueryParameter("q", query)
                .addQueryParameter("apikey", "srRLeAmTroxPinDG8Aus3Ikl6tLGJd94")
                .addQueryParameter("language", "en-us")
                .addQueryParameter("get_param", "value")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<SearchData> serarchDatas1 = new ArrayList<>();
                        List<String> stringList = new ArrayList<>();
                        try {
                            if (response.length() > 0 && response != null) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    SearchData searchData = new SearchData();
                                    searchData.setKey(jsonObject.getString("Key"));
                                    searchData.setCity(jsonObject.getString("LocalizedName"));
                                    JSONObject object = jsonObject.getJSONObject("Country");
                                    searchData.setCountry(object.getString("LocalizedName"));
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("AdministrativeArea");
                                    searchData.setArea(jsonObject1.getString("LocalizedName"));
                                    serarchDatas.add(searchData);
                                    stringList.add(jsonObject.getString("LocalizedName") + ", " + object.getString("LocalizedName"));
                                }
                                Log.e("LLLL_data11: ", String.valueOf(serarchDatas1.size()));
                                mContext.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        serarchDatas1.addAll(serarchDatas);
                                        autoSuggestAdapter.setData(serarchDatas1);
                                        autoSuggestAdapter.notifyDataSetChanged();
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void fireAnalytics(String arg1, String arg2) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "LocationAdapter");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, arg1);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, arg2);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private void fireDetailAnalytics(String arg0, String arg1) {
        Bundle params = new Bundle();
        params.putString("image_path", arg0);
        params.putString("select_from", arg1);
        mFirebaseAnalytics.logEvent("select_image", params);
    }

    // The ViewHolders for Header, Item and Footer
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final ListItemSearchHeaderBinding listItemSearchHeaderBinding;

        public HeaderViewHolder(ListItemSearchHeaderBinding listItemSearchHeaderBinding) {
            super(listItemSearchHeaderBinding.getRoot());
            this.listItemSearchHeaderBinding = listItemSearchHeaderBinding;
//            loadNativeAD(fl_adplaceholder);

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemLocationBinding listItemLocationBinding;

        public ViewHolder(ListItemLocationBinding listItemLocationBinding) {
            super(listItemLocationBinding.getRoot());

            this.listItemLocationBinding = listItemLocationBinding;
        }

    }

}
