package com.android.loginapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import com.ofix.barcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev on 29/03/2016.
 */
public class BullSearch extends Activity implements AdapterView.OnItemSelectedListener {

    private RadioButton radioButton;
    private RadioButton radioButton2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsearch);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 =(Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner1 Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Ratings");
        categories.add("1 Star");
        categories.add("2 Stars");
        categories.add("3 Stars");
        categories.add("4 Stars");
        categories.add("5 Stars");

        // Spinner2 Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("Breed");
        categories2.add("AUNGUS");
        categories2.add("AUBRAC");
        categories2.add("BARZONA");
        categories2.add("BELIGUM BLUE");
        categories2.add("CHAROLAIS");
        categories2.add("HEREFORD");
        categories2.add("LIMOUSIN");
        categories2.add("PARTHENAISE");
        categories2.add("SHORTHORN");
        categories2.add("SIMMENTAL");
        categories2.add("WAGYU");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Rating = parent.getItemAtPosition(position).toString();
        String Breed = parent.getItemAtPosition(position).toString();
        System.out.println("******************"+Rating+"******************");
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

        if(Rating !="Ratings") {
            if (!(radioButton.isChecked()))
                radioButton.toggle();
        }
        if(Breed != "Breed"){
            if (!(radioButton2.isChecked()))
                radioButton2.toggle();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}