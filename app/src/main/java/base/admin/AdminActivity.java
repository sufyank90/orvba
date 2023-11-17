package base.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import base.ApproveMechanicActivity;
import com.example.orvba.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button approveButton = findViewById(R.id.approveButton);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement mechanic approval logic
                startActivity(new Intent(AdminActivity.this, ApproveMechanicActivity.class));
            }
        });
    }
}
