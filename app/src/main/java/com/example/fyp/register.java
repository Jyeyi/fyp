package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class register extends AppCompatActivity {

    private EditText name, ic, email, phone, password, confirmPass;
    private Button register;
    private TextView login;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference dbref;

    String username, userIC, userEmail,userPhone, userPass, userConfirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.ed_name);
        ic = findViewById(R.id.ed_ic);
        email = findViewById(R.id.ed_email);
        phone = findViewById(R.id.ed_phone);
        password = findViewById(R.id.ed_password);
        confirmPass = findViewById(R.id.ed_confirm);
        register = findViewById(R.id.bt_register);
        login = findViewById(R.id.tx_login);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2login = new Intent(register.this, MainActivity.class);
                startActivity(intent2login);
                finish();
            }
        });
    }
        private void inputData(){
            username = name.getText().toString().trim();
            userIC = ic.getText().toString().trim();
            userPhone = phone.getText().toString().trim();
            userEmail = email.getText().toString().trim();
            userPass = password.getText().toString().trim();
            userConfirmPass = confirmPass.getText().toString().trim();

            if (username.isEmpty()){
                name.setError("Information missing!");
                return;
            }
            if (userIC.isEmpty()){
                ic.setError("Information missing!");
                return;
            }
            if (userPhone.isEmpty()){
                phone.setError("Information missing!");
                return;
            }
            if (userEmail.isEmpty()){
                email.setError("Information missing!");
                return;
            }
            if (userPass.isEmpty()){
                password.setError("Please fill in password");
                return;
            }
            if (userConfirmPass.isEmpty()){
                confirmPass.setError("Please fill in confirm password");
                return;
            }

            if (!userPass.equals(userConfirmPass)){
                confirmPass.setError("Confirm password does not match with password");
                return;
            }

            createAcc();


        }

        private void saveData(){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user_id","" + auth.getUid());
            hashMap.put("user_type","User");
            hashMap.put("user_name", "" + username);
            hashMap.put("user_ic", "" + "" + userIC);
            hashMap.put("user_phone", "" + userPhone);
            hashMap.put("user_email", "" + userEmail);
            hashMap.put("user_pass", "" + userPass);

            db = FirebaseDatabase.getInstance();
            dbref = db.getReference("Users");
            dbref.child(auth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(register.this,"Success to register", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(register.this).edit();
                    prefEditor.putString("Uid", auth.getUid());
                    prefEditor.commit();
                    Intent intent2main = new Intent(register.this, user_main.class);
                    startActivity(intent2main);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(register.this,"Failed to register, Please try again" ,Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void createAcc(){

            auth.createUserWithEmailAndPassword(userEmail, userPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    saveData();
                    name.getText().clear();
                    ic.getText().clear();
                    phone.getText().clear();
                    email.getText().clear();
                    password.getText().clear();
                    confirmPass.getText().clear();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(register.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }



}