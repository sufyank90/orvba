package base.mechanic;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orvba.R;

import java.util.List;

public class MechanicAdapter2 extends RecyclerView.Adapter<MechanicAdapter2.MyViewHolder> {

    private Context context;
    private List<Mechanic> arrayList;

    public MechanicAdapter2(Context context, List<Mechanic> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mechanic_item_2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mechanic mechanic = arrayList.get(position);
        holder.nameTv.setText(mechanic.getName());
        holder.numberTv.setText(mechanic.getPhone());
        Glide.with(context).load(mechanic.getImgUrl()).circleCrop().into(holder.iv);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MechanicDetailActivity.class);
                intent.putExtra("", "");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv;
        TextView numberTv;
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            numberTv = itemView.findViewById(R.id.numberTv);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
