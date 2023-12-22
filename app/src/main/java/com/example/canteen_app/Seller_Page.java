package com.example.canteen_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Seller_Page extends AppCompatActivity {

    Button btnMenuBuyer, btnOrderBuyer, btnAddMenuSeller, btnLogOut;
    Fragment menuFragment, orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_page);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnMenuBuyer = findViewById(R.id.btnMenuSeller);
        btnOrderBuyer = findViewById(R.id.btnOrderSeller);
        btnAddMenuSeller = findViewById(R.id.btnAddMenuSeller);

        btnMenuBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.seller_fragment, fragment_menu_seller.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        btnOrderBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.seller_fragment, fragment_order_seller.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        btnAddMenuSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Seller_Page.this, Add_Menu_Seller.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Seller_Page.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}