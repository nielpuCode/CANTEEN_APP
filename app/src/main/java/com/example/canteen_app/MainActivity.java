package com.example.canteen_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnLoginBuyer = findViewById(R.id.btnLoginBuyer);
        Button btnLoginSeller = findViewById(R.id.btnLoginSeller);

        btnLoginBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When this button is clicked, it will move into next page (for temporary, we must change this later on)
                Intent intent = new Intent(MainActivity.this, Buyer_Page.class);
                startActivity(intent);
            }
        });

        btnLoginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // When this button is clicked, it will move into next page (for temporary, we must change this later on)
                Intent intent = new Intent(MainActivity.this, Seller_Page.class);
                startActivity(intent);
            }
        });
    }
}