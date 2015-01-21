package com.m2dl.nater.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.m2dl.nater.R;
import com.m2dl.nater.database.UserDAO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Romain on 21/01/2015.
 */
public class RegisterActivity extends Activity {

    private EditText pseudo,mail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mail = (EditText) findViewById(R.id.mail);
        pseudo = (EditText) findViewById(R.id.pseudo);
    }

    public void register(View v) {
        if (mail.getText() == null || mail.getText().toString().equals("")) {
            mail.setError("Le champs est obligatoire");
            return;
        }
        if (pseudo.getText() == null || pseudo.getText().toString().equals("")) {
            pseudo.setError("Le champs est obligatoire");
            return;
        }
        if (!isEmailValid(mail.getText().toString())) {
            mail.setError("Mail invalide");
            return;
        }
        UserDAO user = new UserDAO(this);
        user.open();
        user.ajouter(pseudo.getText().toString(),mail.getText().toString());
        user.close();
        Intent intent = new Intent(this,PictureActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
