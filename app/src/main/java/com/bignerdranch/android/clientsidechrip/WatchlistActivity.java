package com.bignerdranch.android.clientsidechrip;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class WatchlistActivity extends AppCompatActivity {

    private String username;
    private Button addUser;
    private Button removeUser;
    private TextView addU;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);
        addU=(TextView)findViewById(R.id.handle_to_watch);

        email = this.getIntent().getExtras().getString("email");
        addUser = (Button)findViewById(R.id.add_user);
        removeUser = (Button)findViewById(R.id.remove_user);

            addUser.setOnClickListener(new android.view.View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                username = addU.getText().toString();
                addUserRequest();
            }
        });

            removeUser.setOnClickListener(new android.view.View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    username = addU.getText().toString();
                    removeUserRequest();
                }
            });
    }

    public void addUserRequest()
    {
        RequestManager.get()
                .addUserWatchlistRequest(email,username , this,
                        (added) -> {
                            Intent intent = new Intent(WatchlistActivity.this, RecentChirps.class);
                            Bundle ex = new Bundle();
                            ex.putString("email",email);
                            intent.putExtras(ex);
                            startActivity(intent);
                        });
    }

    public void removeUserRequest()
    {
        RequestManager.get()
                .removeUserWatchlistRequest(email,username , this,
                        (removed) -> {
                            Intent intent = new Intent(WatchlistActivity.this, RecentChirps.class);
                            Bundle ex = new Bundle();
                            ex.putString("email",email);
                            intent.putExtras(ex);
                            startActivity(intent);
                        });
    }
}
