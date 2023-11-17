package base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orvba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNotedActivity extends AppCompatActivity {
    private Button submitBtn;
    private EditText shopNameEt, mechanicNameEt, numberEt, addressEt, addNoteEt;
    private ImageView backIv;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_note_sm);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        setListeners();
    }

    private void initViews() {
        submitBtn = findViewById(R.id.submitBtn);
        shopNameEt = findViewById(R.id.shopNameEt);
        mechanicNameEt = findViewById(R.id.mechanicNameEt);
        numberEt = findViewById(R.id.numberEt);
        addressEt = findViewById(R.id.addressEt);
        addNoteEt = findViewById(R.id.addNoteEt);
        backIv = findViewById(R.id.backIv);
    }

    private void setListeners() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFields()) {
                    String shopName = shopNameEt.getText().toString();
                    String mechanicName = mechanicNameEt.getText().toString();
                    String number = numberEt.getText().toString();
                    String address = addressEt.getText().toString();
                    String data = addNoteEt.getText().toString();

                    String id = myRef.child("my_data").push().getKey();

                    MyNote note = new MyNote(id, shopName, mechanicName, number, address, data);

                    writeToFirebase(note);
                }
            }
        });
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(shopNameEt.getText().toString())) {
            shopNameEt.setError("Shop Name is required");
            return false;
        }

        if (TextUtils.isEmpty(mechanicNameEt.getText().toString())) {
            mechanicNameEt.setError("Mechanic Name is required");
            return false;
        }

        if (TextUtils.isEmpty(numberEt.getText().toString())) {
            numberEt.setError("Number is required");
            return false;
        } else {
            String phoneNumber = numberEt.getText().toString();
            if (!TextUtils.isDigitsOnly(phoneNumber) || phoneNumber.length() != 11) {
                numberEt.setError("Please enter a valid 11-digit Phone number");
                return false;
            }
        }

        if (TextUtils.isEmpty(addressEt.getText().toString())) {
            addressEt.setError("Address is required");
            return false;
        } else if (addressEt.getText().toString().length() > 30) {
            addressEt.setError("Address cannot exceed 30 characters");
            return false;
        }

        // You can add more validation as needed

        return true;
    }

    private void writeToFirebase(MyNote note) {
        myRef.child("my_data").child(note.getId()).setValue(note)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddNotedActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddNotedActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
