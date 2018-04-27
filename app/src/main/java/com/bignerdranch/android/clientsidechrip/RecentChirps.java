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
import java.util.PriorityQueue;

public class RecentChirps extends AppCompatActivity
{

    private RecyclerView chirpRecyclerView;
    private ChirpAdapter adapter;
    private LinearLayoutManager recyclerManager;
    //private String name = ((TextView)findViewById(R.id.textEntryEmail)).toString();
    String name = "gurnmc22@wclive.westminster.edu";
    private ArrayList<Chirp> pq;

    final ChirpRepository chirpDataBase = ChirpRepository.getInstance();
    final UserRepository userDataBase = UserRepository.getInstance();


    private void updateUI()
    {
        if(adapter == null)
        {
            adapter = new ChirpAdapter();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        pq = new ArrayList<>();
        Chirp one = new Chirp("gurnmc22@wclive.westminster.edu","hi",null);
        Chirp two = new Chirp("gurnmc22@wclive.westminster.edu","hi",null);
        Chirp three = new Chirp("gurnmc22@wclive.westminster.edu","hi",null);
        pq.add(one);
        pq.add(two);
        pq.add(three);
        //pq = userDataBase.getUserByEmail(name).getSortedWatchList();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
        chirpRecyclerView = findViewById(R.id.ChirpList);
        recyclerManager = new LinearLayoutManager(this);
        chirpRecyclerView.setLayoutManager(recyclerManager);
        updateUI();
        chirpRecyclerView.setAdapter(adapter);

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
            Chirp c = pq.get(position);
            chirper.setText(userDataBase.getUserByEmail(name).getHandle());
            chirpTextContent.setText(c.message);
            this.ind = position;
        }

    }

    private class ChirpAdapter extends RecyclerView.Adapter < ChirpHolder >
    {

        @Override
        public int getItemCount()
        {
            return pq.size();
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


}
