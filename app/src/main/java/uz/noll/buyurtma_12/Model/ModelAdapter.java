package uz.noll.buyurtma_12.Model;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uz.noll.buyurtma_12.R;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.ModelViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Buyurtma");

    public ModelAdapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buyurtma,parent,false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        holder.textView1.setText(modelArrayList.get(position).getName());
        holder.textView2.setText(modelArrayList.get(position).getHour1());
        holder.textView3.setText(modelArrayList.get(position).getHour2());
        holder.textView4.setText(modelArrayList.get(position).getData_tel());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.imageView.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.imageView.setOnClickListener(view -> {
            databaseReference.child(modelArrayList.get(position).getId()).removeValue();
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    class ModelViewHolder extends RecyclerView.ViewHolder {
        TextView textView1,textView2,textView3,textView4;
        ImageView imageView;
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.item_name);
            textView2 = itemView.findViewById(R.id.item_data1);
            textView3 = itemView.findViewById(R.id.item_data2);
            textView4 = itemView.findViewById(R.id.data_tel);
        }
    }
}
