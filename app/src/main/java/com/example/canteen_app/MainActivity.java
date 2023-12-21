package com.example.canteen_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {
    Button btnLoginBuyer, btnLoginSeller;
    EditText usEmail, usPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        btnLoginBuyer = findViewById(R.id.btnLoginBuyer);
        btnLoginSeller = findViewById(R.id.btnLoginSeller);
        usEmail = findViewById(R.id.editTextEmail);
        usPassword = findViewById(R.id.editTextPassword);

        //handles login button

        btnLoginBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usEmail.getText().toString();
                String password = usPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Email must not be empty", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Password must not be empty", Toast.LENGTH_LONG).show();
                } else {
                    // Check if the user already exists
                    auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                SignInMethodQueryResult result = task.getResult();
                                if (result != null && result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                    // User already exists, attempt to sign in
                                    Log.d("SignInDebug", "User exists, attempting to sign in");
                                    signInUser(email, password);
                                } else {
                                    // User does not exist, create a new account and sign in
                                    Log.d("SignInDebug", "User does not exist, creating a new account");
                                    registerOrSignInUser(email, password);
                                }
                            } else {
                                // Handle the exception
                                Toast.makeText(MainActivity.this, "Failed to check user existence: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });

        //handles login button
        btnLoginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usEmail.getText().toString();
                String password = usPassword.getText().toString();

                if (email.equals("mamankcilok.cashier@gmail.com") || password.equals("mamankcilok")){
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(MainActivity.this, "Email must not be empty", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(password)) {
                        Toast.makeText(MainActivity.this, "Password must not be empty", Toast.LENGTH_LONG).show();
                    } else{
                        registerOrSignInUser(email, password);

                        // Move the intent inside the successful registration block
                        // When this button is clicked, it will move into the next page
                        Intent intent = new Intent(MainActivity.this, Seller_Page.class);
                        startActivity(intent);
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "U have not been registered as employee by the company", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // checks if user's accoutn is not yet registered, the user's account will be registered and the user is directed to the main menu
    private void registerOrSignInUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Successfully Registered and Logged In", Toast.LENGTH_SHORT).show();
                            navigateToBuyerPage();
                        } else {
                            // If the registration fails, try signing in (assuming the email is already registered)
                            signInUser(email, password);
                        }
                    }
                });
    }

    // checks if user's accoutn is already registered, the userr will be directed to the main menu
    private void signInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            navigateToBuyerPage();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToBuyerPage() {
        // When this button is clicked, it will move into the next page
        Intent intent = new Intent(MainActivity.this, Buyer_Page.class);
        startActivity(intent);
    }
}
