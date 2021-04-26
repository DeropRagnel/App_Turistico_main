package com.example.app_turistico.HomeAdapter;

import android.widget.Filter;

import com.example.app_turistico.HomeAdapter.FeaturedAdapter;
import com.example.app_turistico.HomeAdapter.FeaturedHelperClass;
import java.util.ArrayList;

public class CustomFilter extends Filter{

    ArrayList<FeaturedHelperClass> filterList;
    FeaturedAdapter featuredAdapter;

    public CustomFilter(ArrayList<FeaturedHelperClass> filterList, FeaturedAdapter featuredAdapter) {
        this.filterList = filterList;
        this.featuredAdapter = featuredAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();

            ArrayList<FeaturedHelperClass> filterClass = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++){
                if (filterList.get(i).getLocalNome().toUpperCase().contains(constraint)) {
                    filterClass.add(filterList.get(i));
                }
            }
            results.count = filterClass.size();
            results.values = filterClass;
        }

        else {
            results.count = filterList.size();
            results.values = filterList;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        featuredAdapter.featuredLocation = (ArrayList<FeaturedHelperClass>) filterResults.values;
        featuredAdapter.notifyDataSetChanged();

    }
}
