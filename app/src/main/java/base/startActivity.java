package base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orvba.R;

import base.auth.LoginActivity;
import base.auth.SignUpActivity;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity); // Replace with the actual layout file name

        // Find your buttons and text views by their IDs
        Button loginUserButton = findViewById(R.id.login_user);
        Button loginMechanicButton = findViewById(R.id.login_mechanic);
        TextView loginUserSignupTextView = findViewById(R.id.login_user_signup);
        TextView loginMechanicSignupTextView = findViewById(R.id.login_mechanic_signup);


        // Set click listeners for the buttons
        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(startActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginMechanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(startActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the "Not yet Registered? User Signup" text view
        loginUserSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(startActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the "Not yet Registered? Mechanic Signup" text view
        loginMechanicSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(startActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
