package base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orvba.R;

import base.mechanic.Mechanic;

import java.util.List;

public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.MechanicViewHolder> {

    private final Context context;
    private final List<Mechanic> mechanicList;

    public MechanicAdapter(Context context, List<Mechanic> mechanicList) {
        this.context = context;
        this.mechanicList = mechanicList;
    }

    @NonNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mechanic_item, parent, false);
        return new MechanicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MechanicViewHolder holder, int position) {
        Mechanic mechanic = mechanicList.get(position);
        holder.textMechanicName.setText(mechanic.getName());
        holder.textMechanicLocation.setText(mechanic.getLocation());
        holder.textMechanicService.setText(mechanic.getServices());

        // Add more details if needed
    }

    @Override
    public int getItemCount() {
        return mechanicList.size();
    }

    public static class MechanicViewHolder extends RecyclerView.ViewHolder {

        TextView textMechanicName, textMechanicLocation,textMechanicService;

        public MechanicViewHolder(@NonNull View itemView) {
            super(itemView);

            textMechanicName = itemView.findViewById(R.id.textMechanicName);
            textMechanicLocation = itemView.findViewById(R.id.textMechanicLocation);
            textMechanicService = itemView.findViewById(R.id.textMechanicService);

            // Add more TextViews if needed
        }
    }
}
