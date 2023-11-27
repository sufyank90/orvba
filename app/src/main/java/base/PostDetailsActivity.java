package base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import base.auth.LoginActivity;
import base.mechanic.AddMechanicActivity;

import com.example.orvba.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostDetailsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private FloatingActionButton fabAdd;
    private ImageView backIv;
    private ArrayList<MyNote> arrayList = new ArrayList<>();
    private DatabaseReference myRef;
    private Button logoutBtn;
    private RelativeLayout progressRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        listener();
        // Implement mechanic details posting logic
        // For demonstration, you can provide text fields for mechanic details

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromFirebase();
    }

    private void initViews() {
        progressRl = findViewById(R.id.progressRl);
        logoutBtn = findViewById(R.id.logoutBtn);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        backIv = findViewById(R.id.backIv);
        fabAdd = findViewById(R.id.fabAdd);
    }

    private void listener() {


        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(PostDetailsActivity.this, AddMechanicActivity.class);
                startActivity(intent);

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PostDetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadList() {
        NoteAdapter adapter = new NoteAdapter(PostDetailsActivity.this, arrayList);
        rv.setAdapter(adapter);
    }

    public void getListFromFirebase() {
        progressRl.setVisibility(View.VISIBLE);
        myRef.child("my_data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                progressRl.setVisibility(View.GONE);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        MyNote aa = ds.getValue(MyNote.class);
                        arrayList.add(aa);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                loadList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressRl.setVisibility(View.GONE);

            }
        });
    }
}
