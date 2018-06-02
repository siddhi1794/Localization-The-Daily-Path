package com.example.siddhipatil.location3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by siddhipatil on 11/19/17.
 */

public class MarkerNameActivity extends AppCompatActivity {
    Button addMarker;
    EditText markerText;
    String markerName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_marker);

        addMarker= (Button)findViewById(R.id.addMarker);
        markerText=(EditText)findViewById(R.id.idMloc);

        addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerName= markerText.getText().toString();
                Intent intent= new Intent();
                intent.putExtra("name", markerName);
                setResult(RESULT_OK,intent);
                finish();

            }
        });
    }

}
