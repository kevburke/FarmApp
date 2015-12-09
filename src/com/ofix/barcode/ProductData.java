package com.ofix.barcode;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ProductData {

    public String name;
    public long id;
    public String url;
    public float price;

    public static ProductData parseJson(String json) {
        ProductData result = new ProductData();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject jsonObject = new JSONObject(tokener);
            result.id = jsonObject.getLong("id");
            result.name = jsonObject.getString("name");
        } catch (JSONException e) {
            return null;
        }

        return result;
    }

}
