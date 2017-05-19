package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

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

                if (!isValidEmail(email.getText())){
                    email.setError("Needs to be a valid email");
                    return;
                }

                AppCompatCheckBox check = (AppCompatCheckBox)findViewById(R.id.check);

                BackgroundMail.newBuilder(Email.this)
                        .withUsername("wcexplorersclub@gmail.com")
                        .withPassword(getString(R.string.email_password))
                        .withMailto("wcexplorersclub@gmail.com")
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject("WCEC Signup")
                        .withBody("Hi!\n\nI just signed up to the WCEC App.\n\nI would " + (check.isChecked() ? "like" : "NOT like") + " to sign up for the newsletter.\n\nRegards\n\n" + email.getText())
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            @Override
                            public void onSuccess() {
                                // set to cache
                                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putBoolean("skipEmail", true);
                                editor.apply();

                                Intent intent = new Intent(Email.this, Map.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            @Override
                            public void onFail() {
                                //do some magic
                            }
                        })
                        .send();



            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
