package base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orvba.R;

import base.mechanic.Mechanic;
import base.mechanic.MechanicAdapter;

import java.util.ArrayList;
import java.util.List;

public class MechanicListActivity extends AppCompatActivity {

    private MechanicAdapter mechanicAdapter;
    private List<Mechanic> mechanicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewMechanics);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mechanicList = new ArrayList<>();
        mechanicAdapter = new MechanicAdapter(this, mechanicList);
        recyclerView.setAdapter(mechanicAdapter);

        // Load mechanics from the database
        loadHardcodedMechanics();
//        loadMechanics();

    }


    private void loadHardcodedMechanics() {
//        mechanicList.add(new Mechanic("Muzammil Alam", "North Nazimabad 1", "Bike Machanic"));
//        mechanicList.add(new Mechanic("Syed Zakir Ali Hashmi", " Liaquatabad Town, Karachi, Karachi City, Sindh", "Car"));
//        mechanicList.add(new Mechanic("Ahmed Ali", "Location 3", "Car , Bike"));

//        mechanicAdapter.notifyDataSetChanged();
    }

//    private void loadMechanics() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("mechanics");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mechanicList.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Mechanic mechanic = dataSnapshot.getValue(Mechanic.class);
//                    mechanicList.add(mechanic);
//                }
//                mechanicAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//
//        });
//    }
}