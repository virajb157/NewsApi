package com.rv.quantumit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rv.quantumit.R;
import com.rv.quantumit.dbhandler.DatabaseHandler;

public class activity_signUp extends AppCompatActivity {
    EditText etName, etContact, etEmail, etPass;
    CheckBox cbTerm;
    Button btnLogin, btnRegister;
    TextView tvLogin;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        cbTerm = findViewById(R.id.cbTerm);

        btnLogin = findViewById(R.id.btnLogin);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);
        databaseHandler = new DatabaseHandler(getApplicationContext());


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbTerm.isChecked()){
                    if(validation()){
                        insert();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "accept terms and condition",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void insert() {
        ContentValues c = new ContentValues();
        c.put("Name",etName.getText().toString());
        c.put("Contact", etContact.getText().toString());
        c.put("Email",etEmail.getText().toString());
        c.put("Password",etPass.getText().toString());
        if (databaseHandler.insertcustomerdetail(c) > 0 ) {
            Toast.makeText(this,"Registration Successfully",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), activity_news.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

    private boolean validation() {
        if(TextUtils.isEmpty(etName.getText().toString())) {
            etName.setError("Enter name");
            return false;
        }
        if(TextUtils.isEmpty(etContact.getText().toString())) {
            etContact.setError("Enter Contact Number");
            return false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())){
            etEmail.setError("Enter Email");
            return false;
        }
        if (TextUtils.isEmpty(etPass.getText().toString())){
            etPass.setError("Enter Password");
            return false;
        }

        if (etContact.length() < 10){
            etContact.setError("Enter Valid contact");
            return false;
        }
        return true;
    }
}