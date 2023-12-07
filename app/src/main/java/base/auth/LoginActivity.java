package base.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import base.client.User;
import base.mechanic.MechanicList;
import base.StartActivity;
import base.admin.AdminDashboardActivity;
import base.client.UserActivityHome;
import base.mechanic.MechanicActivity;

import com.example.orvba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEt;
    private EditText passEt;
    private Button loginButton;
    private TextView signupRedirectText;

    private TextView loginforgotPassTv;
    private ProgressBar pb;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private ImageView backIv;

    private String who = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myRef = FirebaseDatabase.getInstance().getReference();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initViews();

        listener();


        checkIfAlreadyLoggedIn();
    }

    private void checkIfAlreadyLoggedIn() {
        try {
            if (mAuth.getCurrentUser() != null && who.equals("user")) {
                // goto main-activity
                Intent intent = new Intent(LoginActivity.this, UserActivityHome.class);
                startActivity(intent);
                finish();

            } else if (mAuth.getCurrentUser() != null && who.equals("mechanic")) {
                // do nothing
                Intent intent = new Intent(LoginActivity.this, MechanicList.class);
                startActivity(intent);
                finish();

            } else {
                // do nothing
            }
        } catch (Exception e) {
        }
    }

    private void initViews() {

        pb = findViewById(R.id.pb);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginforgotPassTv = findViewById(R.id.forgotPassTv);
        backIv = (ImageView) findViewById(R.id.backIv);

        if (getIntent() != null) {
            who = getIntent().getStringExtra("who");
        }

    }

    private void listener() {

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, StartActivity.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString();
                String password = passEt.getText().toString();

                if (email.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 5) {
                    Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.equals("admin") && password.equals("admin")) {
                    // open admin panel
                    startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                    finish();

                } else {
                    getListFromFirebase();
                }
            }
        });


        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginforgotPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                } else {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, String.valueOf(task.getException()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getListFromFirebase() {
        pb.setVisibility(View.VISIBLE);

        myRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pb.setVisibility(View.GONE);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        User user = ds.getValue(User.class);
                        if (user != null) {
                            if ((user.getEmail().equals(emailEt.getText().toString()) && user.getType().equals("user") && who.equals("mechanic")) || user.getType().equals("mechanic") && who.equals("user")) {
                                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                return;

                            } else {

                                if ((user.getEmail().equals(emailEt.getText().toString()) && user.getType().equals("mechanic") && who.equals("mechanic") ||
                                        (user.getEmail().equals(emailEt.getText().toString())) &&
                                                user.getType().equals("user") && who.equals("user"))) {
                                    login(emailEt.getText().toString(), passEt.getText().toString());
                                    return;
                                }
                            }
                        }
                    } catch (Exception e) {
                        pb.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);
            }
        });
    }

}
