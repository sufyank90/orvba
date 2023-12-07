package base.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import base.StartActivity;
import base.mechanic.MechanicActivity;
import base.client.UserActivityHome;
import base.auth.LoginActivity;

import com.example.orvba.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboardActivity extends AppCompatActivity {
    private TextView userTv;

    private TextView mechanicTv;

    private Button logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        userTv = findViewById(R.id.userTv);
        mechanicTv = findViewById(R.id.mechanicTv);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminDashboardActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        userTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, UserActivityHome.class));
            }
        });

//        adminButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminDashboardActivity.this, AdminActivity.class));
//            }
//        });

        mechanicTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, MechanicActivity.class));
            }
        });
    }
}
