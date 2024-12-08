package com.example.idealunitpricecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editActualPrice, editQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editActualPrice = findViewById(R.id.editActualPrice);
        editQuantity = findViewById(R.id.editQuantity);
        Button btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String priceInput = editActualPrice.getText().toString();
                String quantityInput = editQuantity.getText().toString();

                if (priceInput.isEmpty() || quantityInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter valid inputs!", Toast.LENGTH_SHORT).show();
                    return;
                }

                double actualUnitPrice = Double.parseDouble(priceInput);
                int quantity = Integer.parseInt(quantityInput);

                // Start ResultActivity
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("actualUnitPrice", actualUnitPrice);
                intent.putExtra("quantity", quantity);
                startActivity(intent);
            }
        });
    }
}
