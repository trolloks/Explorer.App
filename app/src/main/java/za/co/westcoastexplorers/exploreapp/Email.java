package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import za.co.westcoastexplorers.R;

/**
 * Created by rikus on 2017/05/17.
 */

public class Email extends AppCompatActivity {
    public static final String PREFS_NAME = "EmailSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean skipEmail = settings.getBoolean("skipEmail", false);

        if (skipEmail){
            Intent intent = new Intent(Email.this, Map.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatEditText email = (AppCompatEditText)findViewById(R.id.email);
                if (email.getText().length() == 0){
                    email.setError("You need to specify an email");
                    return;
                }

                AppCompatCheckBox check = (AppCompatCheckBox)findViewById(R.id.check);

                // set to cache
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("skipEmail", true);
                editor.apply();

                Intent intent = new Intent(Email.this, Map.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
