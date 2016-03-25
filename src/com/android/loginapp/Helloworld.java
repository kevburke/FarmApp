package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.ofix.barcode.MainActivity;
import com.ofix.barcode.R;

import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Main layout page .
 *
 *
 */
public class Helloworld extends Activity {
    private static final Logger logger = Logger.getLogger("logger");

    public TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);


        textView = (TextView) findViewById(R.id.textView);

    }


    public void scanNow(View view) {
        logger.log(Level.INFO, "button works!");
        //Intent intent = new Intent("com.ofix.barcode.MainActivity");
        Intent intent = new Intent(Helloworld.this,MainActivity.class);
        startActivity(intent);
        //intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE","");
       // startActivityForResult(intent, 0);
        Log.d("test", "button works!");
    }
    public void search(View view){
        logger.log(Level.INFO, "Search button works");

    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if (resultCode == Activity.RESULT_OK) if (requestCode == 101) {
//
//        }
//    }
}


