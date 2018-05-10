package com.bignerdranch.android.clientsidechrip;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class WatchlistActivity extends AppCompatActivity {

    private String username;
    private Button addUser;
    private Button removeUser;
    private TextView addU;
    private String email;

    private RecyclerView userRecyclerView;
    private WatchlistActivity.RVAdapter rvAdapter;
    private ArrayList<String> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        addU=(TextView)findViewById(R.id.handle_to_watch);

        email = this.getIntent().getExtras().getString("email");
        addUser = (Button)findViewById(R.id.add_user);
        removeUser = (Button)findViewById(R.id.remove_user);

        userRecyclerView = findViewById(R.id.UserList);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
            updateUI();
    }

    public void addUserRequest()
    {
        RequestManager.get()
                .addUserWatchlistRequest(email,username , this,
                        (added) -> {
                            finish();
                        });
    }

    public void removeUserRequest()
    {
        RequestManager.get()
                .removeUserWatchlistRequest(email,username , this,
                        (removed) -> {
                            finish();
                        });
    }


    private void updateUI()
    {
        if (rvAdapter == null) {
            rvAdapter = new WatchlistActivity.RVAdapter();
            userRecyclerView.setAdapter(rvAdapter);
        }
        rvAdapter.notifyDataSetChanged();
        // schedule request
        sendUserListRequest();
    }

    private void sendUserListRequest() {
        RequestManager.get()
                .sendUserListRequest(email,this,
                        (users) -> {
                            userList = getList(users);
                            updateUI();
                        });
    }

    private ArrayList<String> getList(List<String> l)
    {
        ArrayList<String> list = new ArrayList<>();

        for(String s: l){
            list.add(s);
        }
        return list;
    }
    private class UserHolder extends RecyclerView.ViewHolder
    {
        private TextView user;
        private int ind;


        public UserHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.user, parent,false));
            user =itemView.findViewById(R.id.user);
        }

        public void bind(int position)
        {
            String u = userList.get(position);
            user.setText(u);
            this.ind = position;
        }

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
    private class RVAdapter extends RecyclerView.Adapter <WatchlistActivity.UserHolder>
    {
        @Override
        public int getItemCount()
        {
            if(userList==null)
                return 0;
            return userList.size();
        }

        @Override
        public WatchlistActivity.UserHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new WatchlistActivity.UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WatchlistActivity.UserHolder holder, int position)
        {
            holder.bind(position);
        }
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
    public void onPause()
    {
        super.onPause();
        finish();
    }
}
