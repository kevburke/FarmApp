package com.ofix.barcode;

import com.google.zxing.client.android.CaptureActivity;
import com.ofix.barcode.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                openCapture();
            }
        }, 2000);
    }

    public void openCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
        finish();
    }

}
