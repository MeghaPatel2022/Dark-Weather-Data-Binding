package com.darksky.weather.today.weatherforecast.settingactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.aboutus.AboutUsActivity;
import com.darksky.weather.today.weatherforecast.databinding.ActivitySettingBinding;
import com.darksky.weather.today.weatherforecast.mainactivity.sharedpreference.Preference;
import com.darksky.weather.today.weatherforecast.privacypolicy.PrivacyPolicyActivity;

import es.dmoral.toasty.Toasty;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding activitySettingBinding;
    MyClickHandlers myClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        activitySettingBinding = DataBindingUtil.setContentView(SettingActivity.this, R.layout.activity_setting);
        activitySettingBinding.setMatrixType(Preference.getUnit(SettingActivity.this));

        activitySettingBinding.setIsNotificationOn(Preference.getNotification(SettingActivity.this).equals(""));

        myClickHandlers = new MyClickHandlers(SettingActivity.this);
        activitySettingBinding.setClickButton(myClickHandlers);
    }

    public void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + Uri.encode(getResources().getString(R.string.email))));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email via..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "There are no email clients installed.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void Rate() {

        String url = "https://play.google.com/store/apps/details?id="
                + getPackageName() + "";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        String url = "https://play.google.com/store/apps/details?id="
                + getApplicationContext().getPackageName() + "";

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

    public class MyClickHandlers {
        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void backBtnClick(View view) {
            onBackPressed();
        }

        public void onUnitBtnClicked(View view) {
            if (Preference.getUnit(SettingActivity.this).equalsIgnoreCase("Metric")) {
                Preference.setUnit(SettingActivity.this, "Imperial");
            } else {
                Preference.setUnit(SettingActivity.this, "Metric");
            }
            activitySettingBinding.setMatrixType(Preference.getUnit(SettingActivity.this));
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

        public void onNotificationBtnClick(View view) {
            if (Preference.getNotification(SettingActivity.this).equals("1")) {
                Preference.setNotification(SettingActivity.this, "");
                activitySettingBinding.setIsNotificationOn(true);
            } else {
                Preference.setNotification(SettingActivity.this, "1");
                activitySettingBinding.setIsNotificationOn(false);
            }
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

        public void shareAppBtnClick(View v) {
            shareTextUrl();
        }

        public void rateAppBtnClick(View view) {
            Rate();
        }

        public void feedbackBtnClick(View view) {
            sendEmail();
        }

        public void aboutUsBtnClick(View view) {
            Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }

        public void privacyPolicy(View view) {
            Intent intent = new Intent(SettingActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        }

        public void moreBtnClick(View view) {
            Toasty.normal(SettingActivity.this, "Coming soon...", Toasty.LENGTH_LONG).show();
        }

    }
}