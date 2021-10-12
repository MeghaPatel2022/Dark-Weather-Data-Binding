package com.darksky.weather.today.weatherforecast.mainactivity.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ListFbNativeAdBinding;
import com.darksky.weather.today.weatherforecast.databinding.ListFifteenDaysListBinding;
import com.darksky.weather.today.weatherforecast.mainactivity.constant.Const;
import com.darksky.weather.today.weatherforecast.mainactivity.model.days15.DailyForecastsItem;
import com.darksky.weather.today.weatherforecast.mainactivity.model.days15.Day;
import com.darksky.weather.today.weatherforecast.mainactivity.model.days15.Night;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Weather15Days extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int AD_TYPE = 1;
    private final FirebaseAnalytics mFirebaseAnalytics;
    private final Activity context;
    private final int FIRST_ADS_ITEM_POSITION = 5;
    private final int ADS_FREQUENCY = 6;
    private final NativeAdsManager nativeAdsManager;
    public List<Object> objects;
    int no_of_ad_request;
    String[] weekOfDay = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    private List<NativeAd> nativeAdList = new ArrayList<>();


    public Weather15Days(List<Object> objects, int size, Activity context) {
        this.context = context;
        this.objects = objects;
        no_of_ad_request = size / (ADS_FREQUENCY - 1);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        fireAnalyticsAds("fb_native", "Ad Request send");
        // test id
        nativeAdsManager = new NativeAdsManager(context, "IMG_16_9_APP_INSTALL#422272992369299_430877624842169", no_of_ad_request);
        // live ads
//        nativeAdsManager = new NativeAdsManager(context, "422272992369299_430877624842169", no_of_ad_request);
//        initNativeAds();
    }

    public void initNativeAds() {

        nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                fireAnalyticsAds("fb_native", "loaded");
                Log.e("LLL_UniqueID: ", String.valueOf(nativeAdsManager.getUniqueNativeAdCount()));
                int count = nativeAdsManager.getUniqueNativeAdCount();
                for (int i = 0; i < count; i++) {
                    NativeAd ad = nativeAdsManager.nextNativeAd();
                    addNativeAds(i, ad);
                }
            }

            @Override
            public void onAdError(AdError adError) {
                Log.e("LLL_Error: ", adError.getErrorMessage());
                if (adError != null)
                    fireAnalyticsAds("fb_native_Error", adError.getErrorMessage());
//                nativeAdsManager.loadAds();
            }
        });
        nativeAdsManager.loadAds();
    }

    public void addNativeAds(int i, NativeAd ad) {

        if (ad == null) {
            return;
        }
        if (this.nativeAdList.size() > i && this.nativeAdList.get(i) != null) {
            this.nativeAdList.get(i).unregisterView();
            this.objects.remove(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY));
            this.nativeAdList = null;
            this.notifyDataSetChanged();
        }

        this.nativeAdList.add(i, ad);

        if (objects.size() > FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY)) {
            objects.add(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY), ad);
            notifyItemInserted(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY));
        }

    }

    private void fireAnalyticsAds(String arg1, String arg2) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, arg1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, arg2);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
    }

    @Override
    public int getItemViewType(int position) {

        if (objects.get(position) instanceof NativeAd) {
            return AD_TYPE;
        } else {
            return ITEM;
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                ListFifteenDaysListBinding listFifteenDaysListBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_fifteen_days_list, parent, false);
                viewHolder = new MyClassView(listFifteenDaysListBinding);
                break;
            case AD_TYPE:
                ListFbNativeAdBinding listFbNativeAdBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_fb_native_ad, parent, false);
                View view = inflater.inflate(R.layout.list_fb_native_ad, parent, false);
                viewHolder = new AdHolder(listFbNativeAdBinding);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder viewholder1, int position) {
        int day = 0; // if day = 0; Day else Night
        if (viewholder1 instanceof MyClassView) {
            MyClassView viewholder = (MyClassView) viewholder1;
            DailyForecastsItem nextDayData = (DailyForecastsItem) objects.get(position);

            DateTime dtsunset = new DateTime(nextDayData.getSun().getSet());
            DateTime dtsunrise = new DateTime(nextDayData.getSun().getRise());

            if ((System.currentTimeMillis() > dtsunrise.getMillis()) &&
                    (System.currentTimeMillis() < dtsunset.getMillis())) {
                day = 0; // Day
            } else {
                day = 1; // Night
            }

            if (day == 0) {
                Day day1 = nextDayData.getDay();
                if (nextDayData.getTemperature().getMaximum() != null) {

                    DateTime dt = new DateTime(nextDayData.getDate());
                    String date = "";
                    date = weekOfDay[dt.getDayOfWeek() - 1] + " " + dt.getDayOfMonth() + "," + " " + dt.getYear();

                    viewholder.listFifteenDaysListBinding.tvDate.setText(date);
                    viewholder.listFifteenDaysListBinding.tvrain1.setText("" + day1.getRainProbability() + "%");
                    viewholder.listFifteenDaysListBinding.tvtemp1.setText("" + Math.round(Double.parseDouble(String.valueOf(nextDayData.getTemperature().getMaximum().getValue()))) + " " + "" + (char) 0x00B0 + nextDayData.getTemperature().getMaximum().getUnit());
                    viewholder.listFifteenDaysListBinding.ivIcon.setBackgroundResource(Const.weatherIcon[nextDayData.getDay().getIcon() - 1]);
                    viewholder.listFifteenDaysListBinding.ivrain.setImageResource(Const.weatherIcon1[nextDayData.getDay().getIcon() - 1]);
                    viewholder.listFifteenDaysListBinding.tvSunShine1.setText("" + day1.getIconPhrase());
                    viewholder.listFifteenDaysListBinding.tvCloud1.setText(day1.getCloudCover() + "%");
                    viewholder.listFifteenDaysListBinding.tvWind1.setText(day1.getWind().getSpeed().getValue() + "" + day1.getWind().getSpeed().getUnit());
                }
            } else {
                Night night = nextDayData.getNight();
                if (nextDayData.getTemperature().getMaximum() != null) {

                    DateTime dt = new DateTime(nextDayData.getDate());
                    String date = "";
                    date = weekOfDay[dt.getDayOfWeek() - 1] + " " + dt.getDayOfMonth() + "," + " " + dt.getYear();

                    viewholder.listFifteenDaysListBinding.tvDate.setText(date);
                    viewholder.listFifteenDaysListBinding.tvrain1.setText("" + night.getRainProbability() + "%");
                    viewholder.listFifteenDaysListBinding.tvtemp1.setText("" + Math.round(Double.parseDouble(String.valueOf(nextDayData.getTemperature().getMaximum().getValue()))) + " " + "" + (char) 0x00B0 + nextDayData.getTemperature().getMaximum().getUnit());
                    viewholder.listFifteenDaysListBinding.ivIcon.setBackgroundResource(Const.weatherIcon[nextDayData.getDay().getIcon() - 1]);
                    viewholder.listFifteenDaysListBinding.ivrain.setImageResource(Const.weatherIcon1[nextDayData.getDay().getIcon() - 1]);
                    viewholder.listFifteenDaysListBinding.tvSunShine1.setText("" + night.getIconPhrase());
                    viewholder.listFifteenDaysListBinding.tvCloud1.setText(night.getCloudCover() + "%");
                    viewholder.listFifteenDaysListBinding.tvWind1.setText(night.getWind().getSpeed().getValue() + "" + night.getWind().getSpeed().getUnit());
                }
            }

        } else {
            NativeAd nativeAd = (NativeAd) objects.get(position);
            AdHolder adsViewHolder = (AdHolder) viewholder1;
            adsViewHolder.listFbNativeAdBinding.nativeAdTitle.setText(nativeAd.getAdvertiserName());
            adsViewHolder.listFbNativeAdBinding.nativeAdBody.setText(nativeAd.getAdBodyText());
            adsViewHolder.listFbNativeAdBinding.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            adsViewHolder.listFbNativeAdBinding.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            adsViewHolder.listFbNativeAdBinding.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            adsViewHolder.listFbNativeAdBinding.nativeAdSponsoredLabel.setText(nativeAd.getSponsoredTranslation());
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(adsViewHolder.listFbNativeAdBinding.nativeAdTitle);
            clickableViews.add(adsViewHolder.listFbNativeAdBinding.nativeAdCallToAction);
            nativeAd.registerViewForInteraction(adsViewHolder.itemView, adsViewHolder.listFbNativeAdBinding.nativeAdMedia, adsViewHolder.listFbNativeAdBinding.nativeAdIcon, clickableViews);
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private static class AdHolder extends RecyclerView.ViewHolder {

        private final ListFbNativeAdBinding listFbNativeAdBinding;

        public AdHolder(ListFbNativeAdBinding listFbNativeAdBinding) {
            super(listFbNativeAdBinding.getRoot());
            this.listFbNativeAdBinding = listFbNativeAdBinding;

        }
    }

    public class MyClassView extends RecyclerView.ViewHolder {

        private final ListFifteenDaysListBinding listFifteenDaysListBinding;

        public MyClassView(ListFifteenDaysListBinding listFifteenDaysListBinding) {
            super(listFifteenDaysListBinding.getRoot());

            this.listFifteenDaysListBinding = listFifteenDaysListBinding;
        }
    }
}
