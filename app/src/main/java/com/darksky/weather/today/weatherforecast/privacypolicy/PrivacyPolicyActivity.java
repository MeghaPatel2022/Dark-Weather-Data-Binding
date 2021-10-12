package com.darksky.weather.today.weatherforecast.privacypolicy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.darksky.weather.today.weatherforecast.R;
import com.darksky.weather.today.weatherforecast.databinding.ActivityPrivacyPolicyBinding;


public class PrivacyPolicyActivity extends AppCompatActivity {

    ActivityPrivacyPolicyBinding privacyPolicyBinding;
    MyClickHandlers myClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        privacyPolicyBinding = DataBindingUtil.setContentView(PrivacyPolicyActivity.this, R.layout.activity_privacy_policy);
        myClickHandlers = new MyClickHandlers(PrivacyPolicyActivity.this);
        privacyPolicyBinding.setBackBtnClick(myClickHandlers);

        privacyPolicyBinding.ivWebview.setWebViewClient(new MyWebViewClient());
        openURL();

    }

    private void openURL() {
        privacyPolicyBinding.ivWebview.loadUrl(getResources().getString(R.string.privacy_policy));
        privacyPolicyBinding.ivWebview.requestFocus();
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

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}