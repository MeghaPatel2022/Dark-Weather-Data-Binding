package com.darksky.weather.today.weatherforecast.aboutus;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.darksky.weather.today.weatherforecast.BuildConfig;
import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ActivityAboutUsBinding;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AboutUsActivity extends AppCompatActivity {

    ActivityAboutUsBinding activityAboutUsBinding;
    MyClickHandlers myClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        activityAboutUsBinding = DataBindingUtil.setContentView(AboutUsActivity.this, R.layout.activity_about_us);
        myClickHandlers = new MyClickHandlers(AboutUsActivity.this);
        activityAboutUsBinding.setOnBackClick(myClickHandlers);

        Glide
                .with(AboutUsActivity.this)
                .load(R.drawable.icon)
                .transition(withCrossFade())
                .transition(new DrawableTransitionOptions().crossFade(700))
                .into(activityAboutUsBinding.imgLogo);

        activityAboutUsBinding.tvVersion.setText("Version: " + BuildConfig.VERSION_NAME)
        ;

    }

    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onBackBtnClicked(View view) {
            onBackPressed();
        }

    }
}