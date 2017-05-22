package za.co.westcoastexplorers.exploreapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.MenuItem;
import android.view.View;

import za.co.westcoastexplorers.R;
import za.co.westcoastexplorers.exploreapp.controller.FireBaseController;

/**
 * Created by rikus on 2017/05/09.
 */

public class ContactUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setTitle(getString(R.string.home_vouchers));

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatEditText login = (AppCompatEditText)findViewById(R.id.login);
                if (!FireBaseController.getInstance().isVoucherValid(login.getText().toString())){
                    login.setError("Invalid voucher");
                    return;
                }

                Intent intent = new Intent(ContactUs.this, Vouchers.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
