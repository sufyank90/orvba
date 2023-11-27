package base.mechanic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orvba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;

import base.MyNote;

public class AddMechanicActivity extends AppCompatActivity {
    private Button submitBtn;
    private EditText shopNameEt, mechanicNameEt, numberEt, addressEt, addNoteEt;
    private ImageView backIv;
    private ImageView addProfileIv;
    private RelativeLayout progressRl;
    private DatabaseReference myRef;
    private StorageReference storageRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_mechanic);

        myRef = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();

        initViews();

        setListeners();
    }

    private void initViews() {
        progressRl = findViewById(R.id.progressRl);
        addProfileIv = findViewById(R.id.addProfileIv);
        submitBtn = findViewById(R.id.submitBtn);
        shopNameEt = findViewById(R.id.shopNameEt);
        mechanicNameEt = findViewById(R.id.mechanicNameEt);
        numberEt = findViewById(R.id.numberEt);
        addressEt = findViewById(R.id.addressEt);
        addNoteEt = findViewById(R.id.addNoteEt);
        backIv = findViewById(R.id.backIv);
    }

    private void setListeners() {
        addProfileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1000);
            }
        });

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
                    String addNote = addNoteEt.getText().toString();


                    progressRl.setVisibility(View.VISIBLE);
                    // Get the data from an ImageView as bytes
                    try {
                        addProfileIv.setDrawingCacheEnabled(true);
                        addProfileIv.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) addProfileIv.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] data = baos.toByteArray();

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        StorageReference fileRef = storageRef.child("mechanic-images/").child(userId);

                        UploadTask uploadTask = fileRef.putBytes(data);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressRl.setVisibility(View.GONE);

                                Toast.makeText(AddMechanicActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // get image url
                                        String id = myRef.child("mechanics").push().getKey();
                                        Mechanic mechanic = new Mechanic(
                                                "" + id,
                                                "" + mechanicName,
                                                "" + shopName,
                                                "" + mechanicName,
                                                "" + number,
                                                "",
                                                "",
                                                "",
                                                ""+address,
                                                "",
                                                "" + addNote + ""+uri.toString()
                                        );
                                        myRef.child("mechanics")
                                                .child(mechanic.getId())
                                                .setValue(mechanic)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progressRl.setVisibility(View.GONE);

                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(AddMechanicActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        } else {
                                                            Toast.makeText(AddMechanicActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressRl.setVisibility(View.GONE);
                                        Toast.makeText(AddMechanicActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                    } catch (Exception e) {
                        progressRl.setVisibility(View.GONE);
                    }

/*
                    String id = myRef.child("my_data").push().getKey();

                    MyNote note = new MyNote(id, shopName, mechanicName, number, address, data);

                    writeToFirebase(note);
*/
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1000) {

            try {
                if (data != null) {
                    Uri uri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    addProfileIv.setImageBitmap(bitmap);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
