package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class LoginActivity extends AppCompatActivity
{
    private Button login;
    private Button register;
    private TextView emailEntry;
    private TextView passwordEntry;
    private String email;
    private String password;
    int messageResId=0;
    final UserRepository userDatabase = UserRepository.getInstance();
    private File data;


    private void sendUserVerificationRequest() {

            RequestManager.get()
                    .sendUserVerificationRequest(email, password, this,
                            (gInput) -> {
                                Database.get().logIn(email);
                                try
                                {
                                    Log.d("getfilesdir",getFilesDir().toString());
                                    Database.get().save(new File(getFilesDir(), "info"));
                                }
                                catch(Exception e)
                                {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                Intent intent = new Intent(LoginActivity.this, RecentChirps.class);
                                Bundle ex = new Bundle();
                                ex.putString("email",email);
                                intent.putExtras(ex);
                                startActivity(intent);
                            });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        try
        {
            Database.load(new File(getFilesDir(), "info"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if(Database.get().isLoggedIn())
        {
            email=Database.get().getEmail();
            Intent intent = new Intent(LoginActivity.this, RecentChirps.class);
            Bundle ex = new Bundle();
            ex.putString("email",email);
            intent.putExtras(ex);
            startActivity(intent);

        }


        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                emailEntry = (EditText) findViewById(R.id.textEntryEmail);
                passwordEntry = (EditText) findViewById(R.id.textEntryPassword);
                email = emailEntry.getText().toString();
                password = passwordEntry.getText().toString();
                sendUserVerificationRequest();

            }
        });
    }



    @Override
    public void onPause()
    {
        super.onPause();
        finish();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try
        {
            Log.d("getfilesdir",getFilesDir().toString());
            Database.get().save(new File(getFilesDir(), "info"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }
}

