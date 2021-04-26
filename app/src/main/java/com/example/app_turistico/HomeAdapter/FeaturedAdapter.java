package com.example.app_turistico.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_turistico.R;
import com.example.app_turistico.Tela_Descricao;

import java.util.ArrayList;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedViewHolder> implements Filterable {

    ArrayList<FeaturedHelperClass> featuredLocation, filterList;
    Context context;
    CustomFilter filter;

    public FeaturedAdapter(ArrayList<FeaturedHelperClass> featuredLocation, Context context) {
        this.featuredLocation = featuredLocation;
        this.context = context;
        this.filterList = featuredLocation;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pnt_turstco_design, parent, false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        FeaturedHelperClass featuredHelperClass = featuredLocation.get(position);

        holder.Image.setImageResource(featuredHelperClass.getImage());
        holder.RegiaoNome.setText(featuredHelperClass.getRegiaoNome());
        holder.LocalNome.setText(featuredHelperClass.getLocalNome());


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent intent = new Intent(context, Tela_Descricao.class);
                intent.putExtra("Imagem", featuredHelperClass.getImage());
                intent.putExtra("LocalNome", featuredHelperClass.getLocalNome());
                intent.putExtra("Descricao", featuredHelperClass.getDescricao());
                intent.putExtra("CoordenadasX", featuredHelperClass.getCoordenadasX());
                intent.putExtra("CoordenadasY", featuredHelperClass.getCoordenadasY());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return featuredLocation.size();
    }

    @Override
    public Filter getFilter() {

        if (filter == null){
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }
}
