package com.example.app_turistico.HomeAdapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_turistico.R;

public class FeaturedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView Image;
    TextView RegiaoNome, LocalNome;
    ItemClickListener itemClickListener;

    FeaturedViewHolder(@NonNull View itemView) {
        super(itemView);

        //Hooks
        Image=itemView.findViewById(R.id.card_pnt_turstco_image);
        RegiaoNome=itemView.findViewById(R.id.card_pnt_turstco_regiaoNome);
        LocalNome=itemView.findViewById(R.id.card_pnt_turstco_localNome);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClickListener(view, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
