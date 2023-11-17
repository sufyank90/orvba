package base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orvba.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MyNote> arrayList;

    public NoteAdapter(Context context, ArrayList<MyNote> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_sm, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyNote data = arrayList.get(position);
        holder.shopname.setText(data.getShopName());
        holder.mechanic.setText(data.getMechanicName());
        holder.number.setText(data.getNumber());
        holder.address.setText(data.getAddress());
        holder.note.setText(data.getNote());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("my_data").child(data.getId()).setValue(null);
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                ((PostDetailsActivity) context).getListFromFirebase();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView shopname;
        TextView mechanic;
        TextView number;
        TextView address;
        TextView note;
        Button deleteBtn;
        Button updateBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            shopname = itemView.findViewById(R.id.shopNameTv);
            mechanic = itemView.findViewById(R.id.mechanicNameTv);
            number = itemView.findViewById(R.id.numberTv);
            address = itemView.findViewById(R.id.addressTv);
            note = itemView.findViewById(R.id.noteTv);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            updateBtn = itemView.findViewById(R.id.updateBtn);
        }
    }
}
