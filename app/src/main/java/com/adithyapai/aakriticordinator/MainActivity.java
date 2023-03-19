package com.adithyapai.aakriticordinator;


import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(v -> scanQR());
        Button contactsButton = findViewById(R.id.contact);
        Button aakritiIdButton = findViewById(R.id.verify);
        aakritiIdButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ManualIdVerify.class)));

        contactsButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Contacts.class)));
        if (getIntent().getBooleanExtra("EXIT", false)) finish();
    }

    private void scanQR() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan QR Code");
        // set potrait orientation
        options.setOrientationLocked(true);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String url = result.getContents();
            startActivity(new Intent(this, StudentDetail.class).putExtra("data","https://aakriti23.canaraengineering.in/user.php?aid="+ url));
        }
    });
}
