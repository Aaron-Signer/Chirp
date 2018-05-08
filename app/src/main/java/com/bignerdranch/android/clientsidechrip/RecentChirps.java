package com.bignerdranch.android.clientsidechrip;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class RecentChirps extends AppCompatActivity
{

    private RecyclerView chirpRecyclerView;
    private RVAdapter rvAdapter;
    private ArrayList<Chirp> pq;
    private List<Chirp> chirpList;

    private User user;
    private String email;
    private Button newChirp;
    private Button addRemoveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
        newChirp = (Button) findViewById(R.id.create_chirp_button);
        addRemoveUser = (Button) findViewById(R.id.add_remove_user);

        //get user of current account
        email = getIntent().getExtras().getString("email");

        chirpRecyclerView = findViewById(R.id.ChirpList);
        chirpRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        newChirp.setOnClickListener(new View.OnClickListener(){
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
        // schedule request
        sendListChirpsRequest();
    }

    private void sendListChirpsRequest() {
        RequestManager.get()
                .sendListChirpsRequest(email,this,
                        (chirps) -> {
                            chirpList = chirps;
                            pq = getSortedChirpList();
                            updateUI();
                        });
    }

    public ArrayList<Chirp> getSortedChirpList()
    {
        ArrayList<Chirp> ch = new ArrayList<>();
        return ch;
    }

    private class ChirpHolder extends RecyclerView.ViewHolder
    {
        private TextView chirper;
        private TextView chirpTextContent;
        private int ind;


        public ChirpHolder(LayoutInflater inflater, ViewGroup parent)
        {
           super(inflater.inflate(R.layout.chirp, parent,false));
           chirper = itemView.findViewById(R.id.chirper_username);
           chirpTextContent = itemView.findViewById(R.id.chirp_text);
        }

        public void bind(int position)
        {
            Chirp c = chirpList.get(position);
            chirper.setText(c.handle);
            chirpTextContent.setText(c.message+c.date.toString());
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
