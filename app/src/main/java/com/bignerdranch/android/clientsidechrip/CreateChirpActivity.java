package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                try {
                    chirpString = java.net.URLEncoder.encode(chirp.getText().toString(), "UTF-8");
                }
                catch(Exception e)
                {

                }
                addChirpRequest();

            }
        });

    }

    private void addChirpRequest() {
            RequestManager.get()
                    .addChirpRequest(email, chirpString, this,
                            (added) -> {
                                Intent intent = new Intent(CreateChirpActivity.this, RecentChirps.class);
                                Bundle ex = new Bundle();
                                ex.putString("email",email);
                                intent.putExtras(ex);
                                startActivity(intent);
                            });


    }
}
