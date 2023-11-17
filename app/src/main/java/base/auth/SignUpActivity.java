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

import base.HelperClass;
import base.MainActivity;
import base.client.User;

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

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEt;
    private EditText emailEt;
    private EditText passEt;
    private Button signupButton;
    private TextView loginRedirectText;
    private ProgressBar pb;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    private ImageView backIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Database Reference initialize
        myRef = FirebaseDatabase.getInstance().getReference().child("user");

        initViews();

        listener();
    }

    private void initViews() {
        pb = findViewById(R.id.pb);
        nameEt = findViewById(R.id.nameEt);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        backIv = (ImageView) findViewById(R.id.backIv);
    }

    private void listener() {

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passEt.getText().toString();

                if (name.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 5) {
                    Toast.makeText(SignUpActivity.this, "Password Length should be greater than 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User("", name, email, password);
                signup(user);
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signup(User user) {

        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPass()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // If successful write to firebase.

                    writeToDatabase(user);

                } else {
                    Toast.makeText(SignUpActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void writeToDatabase(User user) {
        String id = myRef.push().getKey();

        user.setId(id);

        myRef.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    // go to login page
                    mAuth.signOut();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(SignUpActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


