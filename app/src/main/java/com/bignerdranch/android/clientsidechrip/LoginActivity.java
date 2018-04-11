package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity
{

    private Button login;
    private Button register;
    private TextView usernameEntry;
    private TextView passwordEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Database d = Database.get();

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        usernameEntry = (TextView) findViewById(R.id.textEntryUsername);
        passwordEntry = (TextView) findViewById(R.id.textEntryPassword);


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
                usernameEntry = (TextView) findViewById(R.id.textEntryUsername);
                passwordEntry = (TextView) findViewById(R.id.textEntryPassword);
                if(d.userReal((String)usernameEntry.getText()))
                {
                    if(d.getUser((String)usernameEntry.getText()).correctPassword((String)passwordEntry.getText()))
                    {
                        //Start main activity
                    }
                }
                else
                {
                    // display toast invalid username or pass
                }
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }
}

