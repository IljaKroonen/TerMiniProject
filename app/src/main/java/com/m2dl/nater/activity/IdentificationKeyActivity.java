package com.m2dl.nater.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.m2dl.nater.R;
import com.m2dl.nater.data.IdentificationKey;

import java.util.ArrayList;
import java.util.Arrays;

public class IdentificationKeyActivity extends Activity {
    private final Activity thisActivity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);

        final IdentificationKey key;
        try {
            key = new IdentificationKey(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, key.getCurrentChoices());
        spinner.setAdapter(spinnerArrayAdapter);

        final Button okButtpn = (Button) findViewById(R.id.okButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);

        okButtpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Activity done
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key.registerChoice((String)spinner.getSelectedItem());
                if (key.getCurrentChoices().length == 0) {
                    // Activity done
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, Arrays.asList(key.getCurrentChoices()));
                spinner.setAdapter(spinnerArrayAdapter);
            }
        });
    }
}
