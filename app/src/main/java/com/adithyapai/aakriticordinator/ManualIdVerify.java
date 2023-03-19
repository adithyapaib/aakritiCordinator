package com.adithyapai.aakriticordinator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ManualIdVerify extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_id_verify);
        Button aakritiIdButton = findViewById(R.id.aakritiIdVerify);
        EditText aakritiIdEditText = findViewById(R.id.id);
    aakritiIdButton.setOnClickListener(v -> {
        String text;
        String AakritiId = "AAK";
        text = aakritiIdEditText.getText().toString();
        // check if text is all numbers and 4 digits long and not empty
        if (text.matches("[0-9]+") && text.length() == 5 && !text.isEmpty()) {
            AakritiId += text;
        }else{
            Toast.makeText(this, "Please enter a valid Aakriti ID", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "https://aakriti23.canaraengineering.in/user.php?aid=" + AakritiId;
        startActivity(new Intent(this, StudentDetail.class).putExtra("data", url));












    });


    }
}