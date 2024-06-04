package com.example.klk22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AppProductActivity extends AppCompatActivity {

    private EditText editTextId;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_product);

        editTextId = findViewById(R.id.editTextId);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });
    }

    private void saveProduct() {
        String id = editTextId.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        if (id.isEmpty() || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product(Integer.parseInt(id), title, description);

        Intent intent = new Intent();
        intent.putExtra("product", product);
        setResult(RESULT_OK, intent);
        finish();
    }
}