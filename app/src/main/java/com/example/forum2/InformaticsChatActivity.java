package com.example.forum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.forum2.Models.InfoComplaint;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class InformaticsChatActivity extends AppCompatActivity {

    Button complaintsButton;
    Button submitButton;
    EditText enterSubject;
    EditText enterDetail;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatics_chat);

        complaintsButton = findViewById(R.id.complaintsButton);
        submitButton = findViewById(R.id.submitButton);
        enterSubject = findViewById(R.id.enterSubject);
        enterDetail = findViewById(R.id.enterDetail);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("InfoComplaints");

        complaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformaticsChatActivity.this, ViewInformaticsComplaints.class);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                InfoComplaint complaint = new InfoComplaint(subjectEnter, detailEnter, curDate);
                                FirebaseDatabase.getInstance().getReference("InfoComplaint").setValue(complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                        Toast.makeText(InformaticsChatActivity.this, "Your complaint has been added!", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(InformaticsChatActivity.this, ManagementChatActivity.class);
                                        startActivity(intent);

                                    }
                                });
                            }
                        });
            }
        });
    }
}