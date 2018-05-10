package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class CreateChirpActivity extends AppCompatActivity
{
    private String email;
    private Button sendChirp;
    private EditText chirp;
    private String chirpString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chirp);

        email = getIntent().getExtras().getString("email");


        sendChirp = (Button)findViewById(R.id.send_chirp_to_server);
        sendChirp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                chirp = (EditText)findViewById(R.id.chirp_contents);
                String s = chirp.getText().toString();

                if(s.length()<282) {
                    try {
                        chirpString = java.net.URLEncoder.encode(chirp.getText().toString(), "UTF-8");
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    addChirpRequest();
                }
                else{
                    int messageResId = R.string.max_chirp_length;
                    Toast.makeText(getApplicationContext(), messageResId, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void addChirpRequest() {
            RequestManager.get()
                    .addChirpRequest(email, chirpString, this,
                            (added) -> {
                               finish();
                            });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Database.get().logOut();
        try
        {
            Log.d("getfilesdir",getFilesDir().toString());
            Database.get().save(new File(getFilesDir(), "info"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
        return true;
    }

    @Override
    public void onPause()
    {
        super.onPause();
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
    }
}
