package com.example.yourflavor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourflavor.service.LoginService;
import com.example.yourflavor.util.ApiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

        email.setText("first@example.com");
        password.setText("12345");
    }

    public void movePage(View v) {
        String st_email = email.getText().toString();
        String st_password = password.getText().toString();

        if (st_email.equals("") || st_password.equals("")) {
            Toast.makeText(getBaseContext(), "Enter Email and Password", Toast.LENGTH_SHORT).show();
        } else {
            login(st_email, st_password);
        }
    }

    private void login(String email, String password) {
        Retrofit retrofit = ApiHelper.getRetrofit(email, password);

        retrofit
                .create(LoginService.class)
                .login()
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Intent in = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(in);
                        } else {
                            Toast.makeText(getBaseContext(), "Wrong Email Or Password entered", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "login failed: " + throwable.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
