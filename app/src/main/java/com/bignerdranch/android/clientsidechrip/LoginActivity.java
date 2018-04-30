package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    private Button login;
    private Button register;
    private TextView emailEntry;
    private TextView passwordEntry;
    private String email;
    private String password;
    final UserRepository userDatabase = UserRepository.getInstance();
    private boolean goodInput=false;


    private void sendUserVerificationRequest() {
        try {
            RequestManager.get()
                    .sendUserVerificationRequest(email, password, this,
                            (gInput) -> {
                                goodInput = gInput;
                            });
        }
        catch(Exception e)
        {
            goodInput = false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

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

                int messageResId=0;
                if(goodInput)
                {
                    goodInput = false;
                    Intent intent = new Intent(LoginActivity.this, RecentChirps.class);
                    Bundle ex = new Bundle();
                    ex.putString("email",email);
                    intent.putExtras(ex);
                    startActivity(intent);
                }
                else
                {
                    messageResId = R.string.invalidEmailPassword;
                    Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
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
        goodInput = false;
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

