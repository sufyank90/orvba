package base.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.widget.RelativeLayout;

import com.example.orvba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import base.FeedbackActivity;
import base.MechanicListActivity;
import base.MyNote;
import base.NoteAdapter;
import base.PostDetailsActivity;
import base.mechanic.Mechanic;
import base.mechanic.MechanicAdapter2;

public class UserActivityHome extends AppCompatActivity {
    private DatabaseReference myRef;
    private ArrayList<Mechanic> arrayList = new ArrayList<>();
    private RelativeLayout progressRl;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        myRef = FirebaseDatabase.getInstance().getReference();

        initViews();

        listener();

    }

    private void initViews() {
        progressRl = findViewById(R.id.progressRl);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void listener() {
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
