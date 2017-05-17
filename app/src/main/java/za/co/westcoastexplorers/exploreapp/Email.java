package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import za.co.westcoastexplorers.R;

/**
 * Created by rikus on 2017/05/17.
 */

public class Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Email.this, Map.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
