package base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.orvba.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button searchButton = findViewById(R.id.searchButton);
        Button postFeedbackButton = findViewById(R.id.postFeedbackButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement mechanic search logic
                startActivity(new Intent(UserActivity.this, MechanicListActivity.class));
            }
        });

        postFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement feedback submission logic
                startActivity(new Intent(UserActivity.this, FeedbackActivity.class));
            }
        });
    }
}
