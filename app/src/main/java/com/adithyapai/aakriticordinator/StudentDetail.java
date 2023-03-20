package com.adithyapai.aakriticordinator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Locale;

public class StudentDetail extends AppCompatActivity {

    TextView ttv;
    Button reverify, rescan;
    ActivityResultLauncher<ScanOptions> barLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ttv = findViewById(R.id.UserDetailsTextViews);
        reverify = findViewById(R.id.reverify);
        reverify.setOnClickListener(v -> startActivity(new Intent(StudentDetail.this, ManualIdVerify.class)));
        rescan = findViewById(R.id.rescan);
        // Open qrcode scanner on rescan button click
        rescan.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan QR Code");
            // set potrait orientation
            options.setOrientationLocked(true);
            barLauncher.launch(options);
        });

      barLauncher  = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                String url = result.getContents();
                startActivity(new Intent(this, StudentDetail.class).putExtra("data","https://aakriti23.canaraengineering.in/user.php?aid="+ url));
            }
        });



        String url;

         url =  getIntent().getStringExtra("data");
         if (url == null || url.isEmpty()){
            Toast.makeText(this, "Invalid ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        @SuppressLint({"DefaultLocale", "SetTextI18n"}) StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {

                        JSONObject jsonObject;
                        jsonObject = new JSONObject(response);
                        String id, email, name, phone, inst, course, usn, branch, paid, pending;
                        ImageView imageView = findViewById(R.id.userimg);
                        imageView.setClipToOutline(true);
                        String imgsrc =  "https://aakriti23.canaraengineering.in/user_images/"+jsonObject.getString("photo");
                        Picasso.get().load(imgsrc).into(imageView);
                        email = jsonObject.getString("email");
                        name = jsonObject.getString("Name");
                        phone = jsonObject.getString("phone");
                        inst = jsonObject.getString("inst");
                        course = jsonObject.getString("course");
                        usn = jsonObject.getString("usn");
                        branch = jsonObject.getString("branch");
                        paid = jsonObject.getString("paid");
                        pending = jsonObject.getString("pending");
                        id = String.format("%05d", Integer.parseInt(jsonObject.getString("id")));
                        // check if size of branch is greater than 20
                        if (branch.length() > 20){
                            branch = branch.substring(0,20);
                        }
                        if (inst.length() > 20){
                            inst = inst.substring(0,20);
                        }


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

                          try {
                              String formattedText =
                                      "  <h2> ID: AAK" + id + "</h2>\n" +
                                              "  Name:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + name +
                                              "  <br />\n" +
                                              "  Email:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + email +
                                              "  <br />\n" +
                                              "  Phone:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href=tel:" + phone + ">" + phone + "</a>" +
                                              "  <br />\n" +
                                              "  Institute:&nbsp;&nbsp;" + inst.substring(0,20) +
                                              "  <br />\n" +
                                              "  Course:&nbsp;&nbsp;" + course +
                                              "  <br />\n" +
                                              "  USN:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + usn.toUpperCase(Locale.ROOT) +
                                              "  <br />\n" +
                                              "  Branch:&nbsp;&nbsp;" + branch+
                                              "  <br />\n" +
                                              "  Paid:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +  paid +
                                              "  <br />\n" +
                                              "  Pending:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + pending;
                              ttv.setText(Html.fromHtml(formattedText));
                              // setline height to 1.5

                              ttv.setLineSpacing(0,1.5f);

                            }catch (Exception e){
                                e.printStackTrace();

                            }



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