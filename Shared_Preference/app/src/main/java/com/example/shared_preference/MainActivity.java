package com.example.shared_preference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_status);
        Button button_write = findViewById(R.id.btn_write);
        Button button_read = findViewById(R.id.btn_read);

        String username_demo = "Test_user";
        String email_demo = "test@gmail.com";


        button_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SharePref_Name", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",username_demo);
                editor.putString("email", email_demo);
                editor.apply();
                textView.setText("Write Operation Success");

            }
        });

        button_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("SharePref_Name", MODE_PRIVATE);
                String username, eamil;
                username = sharedPreferences.getString("username"," ");
                eamil = sharedPreferences.getString("email"," ");
                textView.setText("Username: " + username + "Email: "+ eamil +"\n");






            }
        });


    }
}