package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import com.ofix.barcode.R;

/**
 * Created by Kev on 04/04/2016.
 */
public class BullSelect extends Activity {
    SQLiteDatabase db;
    TextView textView17;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsel);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] details = new String[5];
        details[0] =bundle.getString("1");
        details[1] =bundle.getString("2");
        details[2] =bundle.getString("3");
        details[3] =bundle.getString("4");
        details[4] =bundle.getString("5");
        TextView textView17 = (TextView) findViewById(R.id.textView17);
        textView17.setText("Filtered by: "+details[0] + "\n"+details[1]+"\n"+details[2]+"\n"+details[3]+"\n"+
        details[4]);

        String path = "ICBF";
        db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
        SQLiteDatabase db = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

        db.beginTransaction();

        try{
            Cursor cur =  db.rawQuery("SELECT * FROM "+details[0]+" WHERE breed="+details[2], null);
            cur.moveToFirst();
    }
}