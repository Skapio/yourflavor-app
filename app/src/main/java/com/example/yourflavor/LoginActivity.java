package com.example.yourflavor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourflavor.ui.home.HomeFragment;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button clk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getBaseContext(), "LoginActivity", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        clk = (Button) findViewById(R.id.login);
    }

    public void movePage(View v)
    {
        String st_email = email.getText().toString();
        String st_password = password.getText().toString();

        if(st_email.equals("first@example.com") && st_password.equals("12345"))
        {
            Intent in = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(in);
        }
        else if (st_email.equals("") && st_email.equals(""))
        {
            Toast.makeText(getBaseContext(), "Enter Email and Password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getBaseContext(), "Wrong Email Or Password entered", Toast.LENGTH_SHORT).show();
        }
    }
}
