package com.example.forum2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.forum2.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class CreateAccountActivity extends AppCompatActivity {

    Button loginButton2;
    Button createButton2;
    EditText enterEmail2;
    EditText enterPassword2;
    EditText enterName;

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        loginButton2 = findViewById(R.id.loginButton2);
        createButton2 = findViewById(R.id.createButton2);
        enterName = findViewById(R.id.enterName);
        enterEmail2 = findViewById(R.id.enterEmail2);
        enterPassword2 = findViewById(R.id.enterPassword2);


        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        loginButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        createButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String emailEnter = enterEmail2.getText().toString();
                String passwordEnter = enterPassword2.getText().toString();
                String nameEnter = enterName.getText().toString();

                if (nameEnter.isEmpty()){
                    enterName.setError("Your name is required!");
                    enterName.requestFocus();
                }

                if (emailEnter.isEmpty()){
                    enterEmail2.setError("Your email is required!");
                    enterEmail2.requestFocus();
                    return;
                }
                if (passwordEnter.isEmpty()){
                    enterPassword2.setError("You need to set a password!");
                    enterPassword2.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailEnter).matches()){
                    enterEmail2.setError("Please provide a valid email! ");
                    enterEmail2.requestFocus();
                    return;
                }
                if (passwordEnter.length() < 6){
                    enterPassword2.setError("Password length is to short. Needs to be more than 6 characters!");
                    enterPassword2.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailEnter, passwordEnter).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            User user = new User(emailEnter, nameEnter);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                                        @Override
                                        public void onComplete(@NonNull Task<Void> Task){
                                            if (task.isSuccessful()) {
                                                Toast.makeText(CreateAccountActivity.this, "Account has been created!", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                           // }
                                            //if{
                                            }else{
                                                Toast.makeText(CreateAccountActivity.this, "Failed to register user!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                            });

                        }else{
                            Toast.makeText(CreateAccountActivity.this, "Failed to register user!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });



    }
    //private void createAccount(){

   // }
}