package com.example.cashflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailAddress;
    EditText password;
    EditText re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        emailAddress = findViewById(R.id.signin_emailText);
        password = findViewById(R.id.signin_passwordText);
        re_password = findViewById(R.id.re_passowrdText);


        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = emailAddress.getText().toString();
                String pw = password.getText().toString();
                String repw = re_password.getText().toString();
                if(pw.equals(repw)){
                    signUp(em, pw);
                }else{
                    Toast.makeText(Register.this, "Passwords do not match." + password.getText().toString(),Toast.LENGTH_SHORT).show();;
                }
                //TODO**MORE AUTHENTICATION NEEDS TO BE DONE HERE FOR REGISTRATION BEFORE FUNCTION SIGNUP(EMAIL,PASSWORD)

            }
        });
    }

    public void signUp(String email, String password){
        Toast.makeText(Register.this, "Validating....", Toast.LENGTH_LONG).show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //signin was successsful and shpuld update the UI
                    Log.d("Yes", "Created User with email: success");
                    Toast.makeText(Register.this, "Registration Successfull.",Toast.LENGTH_SHORT).show();;
                    FirebaseUser user = mAuth.getCurrentUser();
                    //TODO save user data to database
                    updateUI(user);
                }else{
                    //if signin fails
                    Log.w("No", "creating new user failed", task.getException());
                    Toast.makeText(Register.this, "Authentication failed.",Toast.LENGTH_SHORT).show();;
                    updateUI(null);

                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
