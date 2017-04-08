package com.example.tubaphc.convertcurrency1;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Double rateOfUSD = 0.0;
    private Double rateOfJPY = 0.0;
    private Double rateOfGBP = 0.0;
    private Double rateOfEUR = 0.0;

    public void convert(View view) {

        EditText amountField = (EditText) findViewById(R.id.amountField);

        Double amountOfMoney = Double.parseDouble(amountField.getText().toString());

        Double vndAmount = 0.0;

        RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup) ;
        int idChecked = group.getCheckedRadioButtonId();
        switch (idChecked) {
            case R.id.radioUSD:
                vndAmount = amountOfMoney * rateOfUSD;
                break;
            case R.id.radioEUR:
                vndAmount = amountOfMoney * rateOfEUR;
                break;
            case R.id.radioGBP:
                vndAmount = amountOfMoney * rateOfGBP;
                break;
            case R.id.radioYEN:
                vndAmount = amountOfMoney * rateOfJPY;
                break;
        }

        TextView textVNĐ = (TextView) findViewById(R.id.textVNĐ);
        textVNĐ.setText(vndAmount.toString());

        //Toast.makeText(getApplicationContext(), vndAmount.toString() + "VNĐ" , Toast.LENGTH_LONG).show();
        Log.wtf("DDD", "Convert");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetData();
    }

    private void GetData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.floatrates.com/daily/vnd.json";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //USD
                            JSONObject usd = response.getJSONObject("usd");
                            rateOfUSD = 1 / Double.valueOf(usd.getString("rate"));
                            TextView textUSDVND = (TextView) findViewById(R.id.textViewUSDVND);
                            textUSDVND.setText(rateOfUSD.toString());

                            //JPY
                            JSONObject jpy = response.getJSONObject("jpy");
                            rateOfJPY = 1 / Double.valueOf(jpy.getString("rate"));
                            TextView textJPYVND = (TextView) findViewById(R.id.textViewYENVND);
                            textJPYVND.setText(rateOfJPY.toString());

                            //EUR
                            JSONObject eur = response.getJSONObject("eur");
                            rateOfEUR = 1 / Double.valueOf(eur.getString("rate"));
                            TextView textEURVND = (TextView) findViewById(R.id.textViewEURVND);
                            textEURVND.setText(rateOfEUR.toString());

                            //GBP
                            JSONObject gbp = response.getJSONObject("gbp");
                            rateOfGBP = 1 / Double.valueOf(gbp.getString("rate"));
                            TextView textGBPVND = (TextView) findViewById(R.id.textViewGBPVND);
                            textGBPVND.setText(rateOfGBP.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonRequest);
    }
}
