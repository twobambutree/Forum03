package com.example.forum2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ArtsChatActivity extends AppCompatActivity {

    Button complaintsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arts_chat);

        complaintsButton = findViewById(R.id.complaintsButton);

        complaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtsChatActivity.this, ViewArtsComplaints.class);
                startActivity(intent);
            }
        });
    }
}