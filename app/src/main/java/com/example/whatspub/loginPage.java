package com.example.whatspub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatspub.Models.Users;
import com.example.whatspub.databinding.ActivityLoginPageBinding;
import com.example.whatspub.databinding.ActivitySingInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class loginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding =ActivityLoginPageBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot() );


        mAuth = FirebaseAuth.getInstance();
    database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();

    progressDialog = new ProgressDialog(loginPage.this);
    progressDialog.setTitle("Creating Account");
    progressDialog.setMessage("We're creating your account");
    binding.pubSingUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (!binding.txtUsername.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() &&!binding.txtPassword.getText().toString().isEmpty())
          {
              progressDialog.show();
            mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                       if(task.isSuccessful()){

                           Users user  = new Users(binding.txtUsername.getText().toString(),binding.txtEmail.getText().toString(),binding.txtPassword.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);



                           Toast.makeText(loginPage.this, "Sing Up Succesful", Toast.LENGTH_SHORT).show();
                       }else{
                           Toast.makeText(loginPage.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                       }
                        }
                    });
          }else {
              Toast.makeText(loginPage.this, "Enter Crentials", Toast.LENGTH_LONG).show();
          }
        }
    });


    binding.txtAllreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(loginPage.this,SingInActivity.class);
            startActivity(intent);

        }
    });
    }
}