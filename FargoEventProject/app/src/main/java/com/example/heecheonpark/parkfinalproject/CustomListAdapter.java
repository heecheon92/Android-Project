package com.example.heecheonpark.parkfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import activity.EventActivity;
import model.Event;
import model.LoginAPIService;
import rest.ItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomListAdapter extends RecyclerView.Adapter<CustomListAdapter.MyViewHolder> implements Filterable {

    public List<Event> eventList, filterList;
    static CustomFilter filter;

    @Override
    public Filter getFilter() {

        if(filter==null)
        {
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ItemClickListener itemClickListener;
        public ImageView imageView;
        public TextView eventTitle, eventLocation;
        public MyViewHolder(@NonNull View view)
        {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            eventTitle = view.findViewById(R.id.event_title);
            eventLocation = view.findViewById(R.id.event_location);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            this.itemClickListener.onItemClick(v, getLayoutPosition());

        }

        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
    }

    public CustomListAdapter(List<Event> eventList)
    {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public CustomListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {


        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist, parent, false);

        // Set image layout in recycler view fixed.
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull CustomListAdapter.MyViewHolder myViewHolder, int i)
    {
        Event event = eventList.get(i);
        Picasso.get().load(event.getImage_url()).into(myViewHolder.imageView);
        String token = "supersecrettoken";

        myViewHolder.eventTitle.setText(event.getTitle());
        myViewHolder.eventLocation.setText("Location:\n" + event.getLocation());
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

                LoginAPIService loginAPIService = new LoginAPIService();
                loginAPIService.getEachEvent(new Callback<Event>()
                {

                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        Event event = response.body();
                        //Toast.makeText(view.getContext(), "Response code: " + response.code() + " \n " +
                        // "Raw Response: " + response.body().String() + "\n" +
                        // response.body().getSpeakers().get(1).getId(), Toast.LENGTH_LONG).show();
                        ArrayList<Integer> speakersID = new ArrayList<Integer>();
                        for (int j = 0; j < event.getSpeakers().size(); j++)
                        {
                            speakersID.add(event.getSpeakers().get(j).getId());
                        }

                        Intent intent = new Intent(view.getContext(), EventActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("event", event);
                        bundle.putInt("id", event.getId());
                        bundle.putIntegerArrayList("speakerID", speakersID);
                        intent.putExtra("bundle", bundle);
                        view.getContext().startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Something went wrong: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }, token, event.getId());

                Snackbar.make(view, event.getTitle(),Snackbar.LENGTH_SHORT).show();

            }
        });

        /*

        I found this method is safer and independent from my custom interface.

        I will use this block for future. (myViewHolder.itemView <-- to retrieve selected item)

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(v, event.getTitle(),Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), EventActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        */

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


}
