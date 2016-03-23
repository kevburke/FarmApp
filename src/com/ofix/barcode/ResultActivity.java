package com.ofix.barcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
        textView.setTextSize(20);
        textView.setText(message);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Cow",message);
        editor.apply();
        // Set the text view as the activity layout
        setContentView(textView);

        Intent in = new Intent(ResultActivity.this, Helloworld.class);
        startActivity(in);
    }
}
