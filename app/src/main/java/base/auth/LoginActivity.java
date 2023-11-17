package base.auth;

import android.content.Intent;
import android.os.Bundle;
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

import base.MainActivity;
import com.example.orvba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginButton;
    private TextView signupRedirectText;

    private TextView loginforgotPassTv;
    private ProgressBar pb;

    private FirebaseAuth mAuth;

    private ImageView backIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        initViews();

        listener();


        checkIfAlreadyLoggedIn();
    }

    private void checkIfAlreadyLoggedIn() {
        if (mAuth.getCurrentUser() != null) {
            // goto main-activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            // do nothing
        }
    }

    private void initViews() {

        pb = findViewById(R.id.pb);
        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginforgotPassTv = findViewById(R.id.forgotPassTv);
        backIv = (ImageView) findViewById(R.id.backIv);

    }

    private void listener() {


        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


//                if (email.length() == 0) {
//                    Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (pass.length() == 0) {
//                    Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
//                    return;
//                }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString();
                String password = loginPassword.getText().toString();
                if (!validateUsername() | !validatePassword()) {
                    // Handle validation failure
                } else {
                    checkUser(loginUsername, loginPassword);
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

    public boolean validateUsername() {
        String val = loginUsername.getText().toString();
        if (val.isEmpty()) {
            loginUsername.setError("Username Can Not be Empty");
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()) {
            loginPassword.setError("Password Can Not be Empty");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }


//        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                pb.setVisibility(View.GONE);
//                if (task.isSuccessful()) {
//                    // User Authentication is successful.
//                    // now goto home
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

    public void checkUser(EditText loginUsername, EditText loginPassword) {
        String userUsername = this.loginUsername.getText().toString().toLowerCase();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    LoginActivity.this.loginUsername.setError(null);
                    String passwordFromDb = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDb != null && passwordFromDb.equalsIgnoreCase(LoginActivity.this.loginPassword.getText().toString())) {
                        LoginActivity.this.loginPassword.setError(null);
                        // Successful login, show a toast message
                        pb.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();


                        // Redirect to the main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        LoginActivity.this.loginPassword.setError("Invalid UserName/Password");
                        LoginActivity.this.loginPassword.requestFocus();
                    }

                } else {
                    LoginActivity.this.loginUsername.setError("User Does Not Exist");
                    LoginActivity.this.loginUsername.requestFocus();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }


}
