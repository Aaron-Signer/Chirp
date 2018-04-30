package com.bignerdranch.android.clientsidechrip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
    final ChirpRepository chirpDataBase = ChirpRepository.getInstance();
    final UserRepository userDataBase = UserRepository.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //get user of current account
        email = getIntent().getExtras().getString("email");
        user = userDataBase.getUserByEmail(email);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_chirps);
        chirpRecyclerView = findViewById(R.id.ChirpList);
        chirpRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            chirper.setText(c.email);
            chirpTextContent.setText(c.message);
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


}
