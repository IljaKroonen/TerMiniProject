package com.m2dl.nater.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.m2dl.nater.R;
import com.m2dl.nater.data.GlobalVars;
import com.m2dl.nater.data.IdentificationKey;
import com.m2dl.nater.data.Picture;
import com.m2dl.nater.database.UserDAO;
import com.m2dl.nater.sharing.SendPictureEmail;
import com.m2dl.nater.sharing.SenderProvider;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class IdentificationKeyActivity extends Activity {
    private final Activity thisActivity = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identification);
        GlobalVars.context = this;
//        ImageView image = (ImageView) findViewById(R.id.image);
//        image.setImageBitmap(GlobalVars.bitmap);

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
                sendPicture(key);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key.registerChoice((String)spinner.getSelectedItem());
                if (key.getCurrentChoices().length == 0) {
                    sendPicture(key);
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(thisActivity, android.R.layout.simple_spinner_item, Arrays.asList(key.getCurrentChoices()));
                spinner.setAdapter(spinnerArrayAdapter);
            }
        });
    }

    private void sendPicture(IdentificationKey key) {
        Picture picture = new Picture();
        picture.mIdentificationKey = key;
        picture.mDate = new Date();
        picture.mLocation = GlobalVars.location;
        picture.mImageUri = GlobalVars.file;
        UserDAO user = new UserDAO(this);
        user.open();
        picture.mUser = user.getPseudo();
        SenderProvider.SHARING_EMAIL = user.getMail();
        user.close();
        SenderProvider.availableSenders[0].sendPicture(picture);
        Toast.makeText(this,"Email envoyé",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PictureActivity.class);
        startActivity(intent);
        finish();
    }
}
