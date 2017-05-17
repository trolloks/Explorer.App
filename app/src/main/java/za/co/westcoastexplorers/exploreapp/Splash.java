package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FireBaseController.getInstance().destroy();
        FireBaseController.getInstance().setFireBaseListener(new FireBaseController.FireBaseListener() {
            @Override
            public void onSuccess() {
                // loaded data
                Intent intent = new Intent(Splash.this, Tutorial.class);
                startActivity(intent);
                // don't come back to splash
                finish();
            }

            @Override
            public void onError() {
                // error
            }
        });
    }
}
