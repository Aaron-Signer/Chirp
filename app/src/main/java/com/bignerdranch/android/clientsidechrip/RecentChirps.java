package com.bignerdranch.android.clientsidechrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecentChirps extends AppCompatActivity
{

    private RecyclerView chirpRecyclerView;
    private ChirpAdapter adapter;
    private LinearLayoutManager recyclerManager;

    final ChirpRepository dataBase = ChirpRepository.getInstance();

//    //@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        View view = inflater.inflate(R.layout.activity_recent_chirps, container, false);
//        chirpRecyclerView = (RecyclerView) view.findViewById(R.id.ChirpList);
//        chirpRecyclerView.setLayoutManager(new LinearLayoutManager(RecentChirps.this));
//        updateUI();
//        return view;
//    }

    private void updateUI()
    {
        List<Chirp> chirps = new ArrayList<Chirp>();
        dataBase.getChirps("signap22@wclive.westminster.edu");
        adapter = new ChirpAdapter(chirps);
        chirpRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
        chirpRecyclerView = (RecyclerView) findViewById(R.id.ChirpList);
        recyclerManager = new LinearLayoutManager(this);
        chirpRecyclerView.setLayoutManager(recyclerManager);
        updateUI();
    }

    private class ChirpHolder extends RecyclerView.ViewHolder
    {
        private TextView chirper;
        private TextView chirpTextContent;
        private int ind;

        public ChirpHolder(LayoutInflater inflater, ViewGroup parent)
        {
           super(inflater.inflate(R.layout.chirp, parent,false));
           chirper = item.findViewById(R.id.chirper_username);
           chirpTextContent = itemView.findViewById(R.id.chirp_text);
        }

    }

    private class ChirpAdapter extends RecyclerView.Adapter < ChirpHolder >
    {
        private List < Chirp > mChirps;
        public ChirpAdapter( List< Chirp > chirps)
        {
            mChirps = chirps;
        }

        @Override
        public int getItemCount()
        {
            return mChirps.size();
        }

        @Override
        public ChirpHolder onCreateViewHolder( ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(RecentChirps.this);
            return new ChirpHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ChirpHolder holder, int position)
        {

        }
    }


}
