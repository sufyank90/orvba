package base.mechanic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.ImageView;

import com.example.orvba.R;

import base.ViewFeedbackActivity;

public class MechanicActivity extends AppCompatActivity {
    private ImageView backIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic);

        Button postDetailsButton = findViewById(R.id.postDetailsButton);
        Button viewFeedbackButton = findViewById(R.id.viewFeedbackButton);

        backIv = (ImageView) findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        postDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement mechanic details posting logic
                startActivity(new Intent(MechanicActivity.this, MechanicList.class));
            }

        });

        viewFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement viewing feedback logic
                startActivity(new Intent(MechanicActivity.this, ViewFeedbackActivity.class));
            }
        });
    }
}
