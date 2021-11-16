package com.example.forum2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    Button createAccountButton;
    EditText enterEmail1;
    EditText enterPassword1;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createButton1);
        enterEmail1 = findViewById(R.id.enterEmail1);
        enterPassword1 = findViewById(R.id.enterPassword1);

        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEnter = enterEmail1.getText().toString();
                String passwordEnter = enterPassword1.getText().toString();

                if (emailEnter.isEmpty()){
                    enterEmail1.setError("Your email is required!");
                    enterEmail1.requestFocus();
                    return;
                }
                if (passwordEnter.isEmpty()){
                    enterPassword1.setError("Your password is needed!");
                    enterPassword1.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(emailEnter,passwordEnter).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{

                            Toast.makeText(LoginActivity.this, "Login failed! Try again!", Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

    }
}