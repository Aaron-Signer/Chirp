package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

public class RecentChirps extends AppCompatActivity
{

    private RecyclerView chirpRecyclerView;
    private RVAdapter rvAdapter;
    private ArrayList<Chirp> pq;
    private List<Chirp> chirpList;

    private User user;
    private String email;
    private Button newTextChirp;
    private Button newImageChirp;
    private Button addRemoveUser;

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
    protected void onCreate(Bundle savedInstanceState)
    {

        try
        {
            Database.load(new File(getFilesDir(), "info"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        if(!Database.get().isLoggedIn())
            finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);



        newTextChirp = (Button) findViewById(R.id.create_text_chirp_button);
        newImageChirp = (Button) findViewById(R.id.create_image_chirp_button);
        addRemoveUser = (Button) findViewById(R.id.add_remove_user);


        //get user of current account
        email = getIntent().getExtras().getString("email");

        chirpRecyclerView = findViewById(R.id.ChirpList);
        chirpRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newImageChirp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecentChirps.this, CreateImageChirp.class);
                Bundle ex = new Bundle();
                ex.putString("email", email);
                intent.putExtras(ex);
                startActivity(intent);
            }
        });

        newTextChirp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecentChirps.this, CreateChirpActivity.class);
                Bundle ex = new Bundle();
                ex.putString("email", email);
                intent.putExtras(ex);
                startActivity(intent);
            }
        });

            addRemoveUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecentChirps.this, WatchlistActivity.class);
                Bundle ex = new Bundle();
                ex.putString("email", email);
                intent.putExtras(ex);
                startActivity(intent);
            }
        });

            updateUI();

    }

    private void updateUI()
    {
        if (rvAdapter == null) {
            rvAdapter = new RVAdapter();
            chirpRecyclerView.setAdapter(rvAdapter);
        }
        rvAdapter.notifyDataSetChanged();

            sendListChirpsRequest();

        // schedule request
        //sendListChirpsRequest();
    }

    private void sendListChirpsRequest() {
            RequestManager.get()
                    .sendListChirpsRequest(email, this,
                            (chirps) -> {
                                chirpList = chirps;
                                chirpList = getSortedChirpList();
                                updateUI();
                            });


    }

    public ArrayList<Chirp> getSortedChirpList()
    {
        ArrayList<Chirp> ch = new ArrayList<>();
        ArrayList<Chirp> fin = new ArrayList<>();
        ChirpComparator comp = new ChirpComparator();
        PriorityQueue<Chirp> watchlist = new PriorityQueue<Chirp>(1, comp);
                    Log.d("Comparing", "\n");

        for(Chirp c: chirpList) {
            watchlist.add(c);
            Log.d("Comparing", watchlist.peek().getDate().toString());
        }

        while(watchlist.peek() != null)
            ch.add(watchlist.poll()) ;


//        for(Chirp c: watchlist) {
//            ch.add(c);
//        }

        return ch;
    }

    private class ChirpHolder extends RecyclerView.ViewHolder
    {
        private TextView chirper;
        private TextView chirpTextContent;
        private TextView chirpDate;
        private int ind;


        public ChirpHolder(LayoutInflater inflater, ViewGroup parent)
        {
           super(inflater.inflate(R.layout.chirp, parent,false));
           chirper = itemView.findViewById(R.id.chirper_username);
           chirpTextContent = itemView.findViewById(R.id.chirp_text);
           chirpDate = itemView.findViewById(R.id.chirp_date);

        }

        public void bind(int position)
        {
            Chirp c = chirpList.get(position);
//            chirpDate.setText(c.date.toString());
            Date cur = new Date();
            double curTime = cur.getTime() + (4*60*60*1000);
            double chirpTime = c.getDate().getTime();
            double dif = (curTime - chirpTime)*.001/60;
            int res = (int) dif;
            if(res<34560) {

                chirper.setText(c.handle);
                chirpTextContent.setText(c.message);

                if (res < 1)
                    chirpDate.setText("Just now.");
                else if (res > 1 && res < 60)
                    chirpDate.setText(Integer.toString(res) + " minutes ago.");
                else if (res >= 60 && res < (1440)) {
                    if (res / 60 == 1)
                        chirpDate.setText(Integer.toString(res / 60) + " hour ago.");
                    else
                        chirpDate.setText(Integer.toString(res / 60) + " hours ago.");
                } else if (res >= (1440) && res < 34560) {
                    if (res / 1440 == 1)
                        chirpDate.setText(Integer.toString(res / 60) + " day ago.");
                    else
                        chirpDate.setText(Integer.toString(res / 60) + " days ago.");
                }
            }
            this.ind = position;

        }

    }

    private class RVAdapter extends RecyclerView.Adapter < ChirpHolder >
    {
        @Override
        public int getItemCount()
        {
            if(chirpList==null)
                return 0;
            return chirpList.size();
        }

        @Override
        public ChirpHolder onCreateViewHolder( ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ChirpHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ChirpHolder holder, int position)
        {
            holder.bind(position);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(!Database.get().isLoggedIn())
            finish();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
    }


}
