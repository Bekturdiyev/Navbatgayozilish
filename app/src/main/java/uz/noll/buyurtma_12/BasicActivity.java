package uz.noll.buyurtma_12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import uz.noll.buyurtma_12.Model.Model;
import uz.noll.buyurtma_12.Model.ModelAdapter;
import uz.noll.buyurtma_12.databinding.ActivityBasicBinding;

public class BasicActivity extends AppCompatActivity {

    TextView textView1,textView2,textView3,textView4;

    private ActivityBasicBinding binding;

    DatabaseReference databaseReference ;

    Model model;
    ModelAdapter modelAdapter;
    ArrayList<Model> modelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Buyurtma");
        Intent intent =getIntent();
        String name = intent.getStringExtra("name");

        binding.mainAdd.setOnClickListener(view -> {
            View view1 = LayoutInflater.from(BasicActivity.this).inflate(R.layout.item_add,null);
            Dialog dialog = new Dialog(BasicActivity.this);
            dialog.setContentView(view1);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            textView1 = view1.findViewById(R.id.hour_1);
            textView2 = view1.findViewById(R.id.hour_2);
            textView3 = view1.findViewById(R.id.item_yes);
            textView4 = view1.findViewById(R.id.item_no);

            textView1.setOnClickListener(e ->{
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener onTimeSetListener;
                onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i <10){
                            if (i1<10){
                                textView1.setText("0" + i + ":" + "0" +  i1);
                            }else {
                                textView1.setText("0" + i + ":" + i1);
                            }
                        }else {
                            if (i1<10){
                                textView1.setText(i + ":" + "0" + i1);
                            }else {
                                textView1.setText(i + ":" + i1);
                            }
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(BasicActivity.this,onTimeSetListener,hour,min,true);
                timePickerDialog.show();

            });
            textView2.setOnClickListener(e ->{
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener onTimeSetListener;
                onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        if (i <10){
                            if (i1<10){
                                textView2.setText("0" + i + ":" + "0" +  i1);
                            }else {
                                textView2.setText("0" + i + ":" + i1);
                            }
                        }else {
                            if (i1<10){
                                textView2.setText(i + ":" + "0" + i1);
                            }else {
                                textView2.setText(i + ":" + i1);
                            }
                        }
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(BasicActivity.this,onTimeSetListener,hour,min,true);
                timePickerDialog.show();

            });
            textView3.setOnClickListener(e ->{
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                Model model = new Model();
                model.setName(name);
                model.setHour1(textView1.getText().toString());
                model.setHour2(textView2.getText().toString());
                model.setData_tel(hour + ":" + min);
                String id = databaseReference.push().getKey().toString();
                model.setId(id);
                databaseReference.child(id).setValue(model);

                Toast.makeText(BasicActivity.this, "Add", Toast.LENGTH_LONG).show();
                dialog.hide();
            });
            textView4.setOnClickListener(e ->{
                dialog.hide();
            });

            dialog.create();
            dialog.show();

        });
        modelArrayList = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    model = dataSnapshot.getValue(Model.class);
                    modelArrayList.add(model);
                }
                modelAdapter = new ModelAdapter(BasicActivity.this, modelArrayList);
                binding.recycler.setLayoutManager(new LinearLayoutManager(BasicActivity.this));
                binding.recycler.setAdapter(modelAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}