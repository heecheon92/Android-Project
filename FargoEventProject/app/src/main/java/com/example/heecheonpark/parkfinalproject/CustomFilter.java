package com.example.heecheonpark.parkfinalproject;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import model.Event;

public class CustomFilter extends Filter {

    CustomListAdapter adapter;
    List<Event> eventList, filterList;

    public CustomFilter(List<Event> filterList, CustomListAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<Event> filteredEvents = new ArrayList<>();

            for (int i=0; i < filterList.size(); i++)
            {
                //CHECK
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredEvents.add(filterList.get(i));
                }
            }

            results.count=filteredEvents.size();
            results.values=filteredEvents;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.eventList = (List<Event>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
