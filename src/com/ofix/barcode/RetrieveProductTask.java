package com.ofix.barcode;

import android.os.AsyncTask;
import com.google.zxing.client.android.CaptureActivity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//public class RetrieveProductTask{
public class RetrieveProductTask extends AsyncTask<String, Void, ProductData> {

    private CaptureActivity delegate;

    public RetrieveProductTask(CaptureActivity activity) {
        delegate = activity;
    }

    @Override
    protected ProductData doInBackground(String... arg0) {
        //Create a new HttpClient and Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://journeyplanner.herokuapp.com/dummy?id=" + arg0[0]);
        ProductData result = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json = reader.readLine();
            result = ProductData.parseJson(json);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return result;
        //return arg0[0];
    }

    @Override
    protected void onPostExecute(ProductData result) {
        delegate.productFound(result);
        super.onPostExecute(result);

    }


}
