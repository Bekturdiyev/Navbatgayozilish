package uz.noll.buyurtma_12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import uz.noll.buyurtma_12.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadText();

        binding.nextBtn.setOnClickListener(e -> {
            String name = binding.editName1.getText().toString();
            if (name.isEmpty()) {
                binding.text2.setVisibility(View.VISIBLE);
            } else {
                saveText();
                Intent intent = new Intent(MainActivity.this, BasicActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            }
        });
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("name",binding.editName1.getText().toString());
        ed.commit();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString("name", null);
        if (savedText != null){
            Intent intent = new Intent(MainActivity.this, BasicActivity.class);
            intent.putExtra("name", savedText);
            startActivity(intent);
            finish();
        }
    }
}