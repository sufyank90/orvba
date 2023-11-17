package base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.orvba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    private EditText editTextFeedback;
    private Button submitButton;

    private FirebaseAuth mAuth;
    private DatabaseReference feedbackReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // User is not signed in, handle accordingly (e.g., redirect to login)
            finish();
        }

        editTextFeedback = findViewById(R.id.editTextFeedback);
        submitButton = findViewById(R.id.submitButton);

        feedbackReference = FirebaseDatabase.getInstance().getReference("feedbacks");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        String feedbackText = editTextFeedback.getText().toString().trim();

        if (!feedbackText.isEmpty()) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();

                // Create a unique feedback entry using push()
                String feedbackId = feedbackReference.child(userId).push().getKey();

                Feedback feedback = new Feedback(userId, feedbackText);

                // Save the feedback to the database
                assert feedbackId != null;
                feedbackReference.child(userId).child(feedbackId).setValue(feedback);

                // Show a success message
                showToast("Feedback submitted successfully");

                // Navigate to another activity (optional)
                // For example, startActivity(new Intent(FeedbackActivity.this, AnotherActivity.class));

                finish(); // Close the FeedbackActivity
            }
        } else {
            // Show an error message for empty feedback
            showToast("Feedback cannot be empty");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
