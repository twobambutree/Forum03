package com.example.forum2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.forum2.Models.Announcement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class CreateAnnouncementActivity extends AppCompatActivity {

    Button submitButton;
    Button clearButton;
    EditText enterSubject;
    EditText enterDetail;

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        submitButton = findViewById(R.id.submitButton);
        clearButton = findViewById(R.id.clearButton);
        enterSubject = findViewById(R.id.enterSubject);
        enterDetail = findViewById(R.id.enterDetail);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Announcements");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                String subjectEnter = enterSubject.getText().toString();
                String detailEnter = enterDetail.getText().toString();
                int curDate = (int) (new Date().getTime()/1000);

                if (subjectEnter.isEmpty()) {
                    enterSubject.setError("The subject is required!");
                    enterSubject.requestFocus();
                }

                if (detailEnter.isEmpty()) {
                    enterDetail.setError("The discription is required!");
                    enterDetail.requestFocus();
                }

                mAuth.createUserWithEmailAndPassword(subjectEnter, detailEnter)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                Announcement announcement = new Announcement(subjectEnter, detailEnter, curDate);
                                FirebaseDatabase.getInstance().getReference("Announcement").setValue(announcement).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> Task){
                                        Toast.makeText(CreateAnnouncementActivity.this, "An announcerment has been added!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(CreateAnnouncementActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
            }
        });
    }
}