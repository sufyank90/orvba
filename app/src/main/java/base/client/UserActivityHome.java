package base.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.orvba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import base.StartActivity;
import base.admin.AdminDashboardActivity;
import base.auth.LoginActivity;
import base.mechanic.Mechanic;
import base.mechanic.MechanicAdapter2;

public class UserActivityHome extends AppCompatActivity {
    private DatabaseReference myRef;
    private Toolbar toolbar_;
    private ArrayList<Mechanic> arrayList = new ArrayList<>();
    private RelativeLayout progressRl;
    private RecyclerView rv;
    private ImageView logoutIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        listener();

    }

    private void initViews() {
        toolbar_ = findViewById(R.id.toolbar_);
        toolbar_.setTitle("HOME");
        toolbar_.setSubtitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        logoutIv = findViewById(R.id.logoutIv);
        progressRl = findViewById(R.id.progressRl);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void listener() {
        logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivityHome.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getListFromFirebase() {
        progressRl.setVisibility(View.VISIBLE);
        myRef.child("mechanics").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                progressRl.setVisibility(View.GONE);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Mechanic aa = ds.getValue(Mechanic.class);
                        if (aa != null) {
                            arrayList.add(aa);
                        }
                        loadList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressRl.setVisibility(View.GONE);

            }
        });
    }

    public void loadList() {
        MechanicAdapter2 adapter = new MechanicAdapter2(UserActivityHome.this, arrayList);
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListFromFirebase();
    }
}
