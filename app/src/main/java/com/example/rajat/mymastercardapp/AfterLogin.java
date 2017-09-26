package com.example.rajat.mymastercardapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
//Here after logging in will contain options related
//to the accounts the relevant details will be dynamically fetched from
//the ibm bluemix cloudant database
public class AfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        ArrayList<String> features=new ArrayList<String>();
        features.add("Net Balance");
        features.add("Last 10 Transactions");
        features.add("Monthly Usage Stats");

        ArrayAdapter<String> features_adap=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,features);
        final GridView lis=(GridView)findViewById(R.id.gridView);
        lis.setAdapter(features_adap);
        lis.setClickable(true);
        lis.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object o = lis.getItemAtPosition(position);
                if (position == 0) {
                    Intent intent = new Intent(AfterLogin.this, Net_Balance.class);
                    //based on item add info to intent
                    startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(AfterLogin.this, Last10transactions.class);
                    //based on item add info to intent
                    startActivity(intent);

                } else if (position == 2) {
                    Intent intent = new Intent(AfterLogin.this, MonthlyUsage.class);
                    //based on item add info to intent
                    startActivity(intent);

                }else if (position == 3) {
                    Intent intent = new Intent(AfterLogin.this, Last10transactions.class);
                    //based on item add info to intent
                    startActivity(intent);

                }
            }
        });
    }
}
