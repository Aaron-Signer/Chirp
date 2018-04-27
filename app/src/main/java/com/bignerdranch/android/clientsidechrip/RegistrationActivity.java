package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity
{
    private Button makeUser;
    private TextView email;
    private TextView username;
    private TextView pass;
    private TextView cpass;
    final UserRepository userDatabase = UserRepository.getInstance();
    String p;
    String cp;
    String e;
    String u;

    public boolean fieldsCompleted()
    {
        return!(p.length()<1||cp.length()<1||e.length()<1||u.length()<1);
    }

    public boolean passwordsMatch()
    {
        return p.equals(cp);
    }

    public boolean goodLength()
    {
        return (p.length()<25);
    }

    public boolean userReal()
    {
        return(userDatabase.getUserByEmail(e)!=null);
    }

    public boolean goodUsername()
    {
        boolean goodUN = true;
        if(u.length()<1||u.length()>12)
        {
            goodUN = false;
        }
        else
        {
            for(int i = 0; i<u.length(); i++)
            {
                if(((int)u.charAt(i)<'a'||(int)u.charAt(i)>'z')&&((int)u.charAt(i)<'A'||(int)u.charAt(i)>'Z')&&((int)u.charAt(i)<'0'||(int)u.charAt(i)>'9'))
                {
                    goodUN=false;
                }
            }
        }
        return goodUN;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        makeUser = (Button) findViewById(R.id.createUser);

        makeUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                pass = (EditText) findViewById(R.id.textNewPassword);
                cpass = (EditText) findViewById(R.id.textNewPasswordConfirm);
                email = (EditText) findViewById(R.id.textNewEmail);
                username = (EditText) findViewById(R.id.textNewUsername);
                p = pass.getText().toString();
                cp = cpass.getText().toString();
                e = email.getText().toString();
                u =  username.getText().toString();

                int messageResId = 0;



                if(passwordsMatch())
                {
                    if(goodLength())
                    {
                        if(userReal())
                        {
                            messageResId = R.string.emailUsed;
                            Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(goodUsername())
                            {
                                if(fieldsCompleted())
                                {

                                    userDatabase.addUser(new User(u,e,p));
                                    Intent intent = new Intent(RegistrationActivity.this, RecentChirps.class);
                                    Bundle ex = new Bundle();
                                    ex.putString("email", e);
                                    intent.putExtras(ex);
                                    startActivity(intent);
                                }
                                else
                                {
                                    messageResId = R.string.completeFields;
                                    Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                messageResId = R.string.badUsername;
                                Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        messageResId = R.string.longPassword;
                        Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    messageResId = R.string.invalidPassword;
                    Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
