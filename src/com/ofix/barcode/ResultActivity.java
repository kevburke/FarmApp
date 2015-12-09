package com.ofix.barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.loginapp.Helloworld;
import com.google.zxing.client.android.CaptureActivity;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CaptureActivity.BARCODE_ID);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView);
//
//        Intent intentReturn = new Intent(ResultActivity.this,Helloworld.class);
//        Bundle b = new Bundle();
//
//        b.putString("SCAN_RESULT",message);
//        intentReturn.putExtras(b);
//        setResult(Activity.RESULT_OK,intentReturn);
    }
}
