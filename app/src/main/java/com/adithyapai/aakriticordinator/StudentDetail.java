package com.adithyapai.aakriticordinator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class StudentDetail extends AppCompatActivity {

    TextView ttv;
    Button reverify;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ttv = findViewById(R.id.UserDetailsTextViews);
        reverify = findViewById(R.id.reverify);
        reverify.setOnClickListener(v -> startActivity(new Intent(StudentDetail.this, ManualIdVerify.class)));
        String url;

         url =  getIntent().getStringExtra("data");
         if (url == null || url.isEmpty()){
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {


                    try {

                        JSONObject jsonObject = null;
                        jsonObject = new JSONObject(response.toString());
                        String id, email, name, phone, inst, course, usn, branch, address, updated, paid, pending;
                        ImageView imageView = findViewById(R.id.userimg);
                        imageView.setClipToOutline(true);
                        String imgsrc =  "https://aakriti23.canaraengineering.in/user_images/"+jsonObject.getString("photo");
                        Picasso.get().load(imgsrc).into(imageView);
                        id = jsonObject.getString("id");
                        email = jsonObject.getString("email");
                        name = jsonObject.getString("Name");
                        phone = jsonObject.getString("phone");
                        inst = jsonObject.getString("inst");
                        course = jsonObject.getString("course");
                        usn = jsonObject.getString("usn");
                        branch = jsonObject.getString("branch");
                        address = jsonObject.getString("address");
                        updated = jsonObject.getString("updated");
                        paid = jsonObject.getString("paid");
                        pending = jsonObject.getString("pending");


                        if (Integer.parseInt(paid) >=300){
                            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.success);
                            View view = findViewById(R.id.view);
                            view.setBackgroundColor(Color.parseColor("#54B435"));
                            Button valid = findViewById(R.id.valid);
                            valid.setVisibility(View.VISIBLE);
                            valid.setText("Payment Done");
                            valid.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.valid, 0);
                            MediaPlayer mp = MediaPlayer.create(this, R.raw.success);
                            mp.start();

                        }else if (Integer.parseInt(paid) < 300){
                            ttv.setText(
                                    "Name: " + name +
                                            "\nEmail: " + email +
                                            "\nPhone: " + phone +
                                            "\nInstitute: " + inst.substring(0,20) +
                                            "\nCourse: " + course +
                                            "\nUSN: " + usn +
                                            "\nBranch: " + branch +
                                            "\nPaid: " + paid +
                                            "\nPending: " + pending
                            );





                            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wrong);
                            mediaPlayer.start();
                            invalidUser();




                        }else{
                            ttv.setText("User Details not Updated \n Please Contact Coordinator");

                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                        invalidUser();
                    }
                }, error -> invalidUser()
        );
        queue.add(stringRequest);


    }
    @SuppressLint("SetTextI18n")
    void invalidUser(){
        Button valid = findViewById(R.id.valid);
        valid.setVisibility(View.VISIBLE);
        View view = findViewById(R.id.view);
        view.setBackgroundColor(Color.RED);
        valid.setText("Payment Not Done");
        valid.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.invalid, 0);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
    }


}