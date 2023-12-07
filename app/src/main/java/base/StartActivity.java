package base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orvba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import base.auth.LoginActivity;
import base.auth.SignUpActivity;
import base.client.User;
import base.client.UserActivityHome;
import base.mechanic.MechanicList;

public class StartActivity extends AppCompatActivity {
    private DatabaseReference myRef;
    private RelativeLayout progressRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity); // Replace with the actual layout file name

        myRef = FirebaseDatabase.getInstance().getReference();

        // Find your buttons and text views by their IDs
        progressRl = findViewById(R.id.progressRl);
        Button loginUserButton = findViewById(R.id.login_user);
        Button loginMechanicButton = findViewById(R.id.login_mechanic);
        TextView loginUserSignupTextView = findViewById(R.id.login_user_signup);
        TextView loginMechanicSignupTextView = findViewById(R.id.login_mechanic_signup);

        // get list from db
        // check for user or mechanic
        // open activity according

        //getListFromFirebase();


        // Set click listeners for the buttons
        loginUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                intent.putExtra("who", "user");
                startActivity(intent);
                finish();
            }
        });

        loginMechanicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                intent.putExtra("who", "mechanic");
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the "Not yet Registered? User Signup" text view
        loginUserSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                intent.putExtra("who", "user");
                startActivity(intent);
                finish();
            }
        });

        // Set click listener for the "Not yet Registered? Mechanic Signup" text view
        loginMechanicSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                intent.putExtra("who", "mechanic");
                startActivity(intent);
                finish();
            }
        });
    }

    private String email = "";

    private void getListFromFirebase() {

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        progressRl.setVisibility(View.VISIBLE);
        myRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressRl.setVisibility(View.GONE);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        User user = ds.getValue(User.class);
                        if (user != null && user.getEmail().equals(email) && user.getType().equals("user")) {
                            startActivity(new Intent(StartActivity.this, UserActivityHome.class));
                        } else if (user != null && user.getEmail().equals(email) && user.getType().equals("mechanic")) {
                            startActivity(new Intent(StartActivity.this, MechanicList.class));
                        }
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressRl.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromFirebase();
    }
}
