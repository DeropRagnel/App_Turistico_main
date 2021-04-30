package com.example.app_turistico.HomeAdapter;

import com.google.android.gms.maps.model.LatLng;

public class FeaturedHelperClass {
    int Image, Descricao, RegiaoNome;
    String LocalNome;
    double CoordenadasX, CoordenadasY;

    public FeaturedHelperClass(int Image, int regiaoNome, String localNome, int descricao, double coordenadasX, double coordenadasY) {
        this.Image = Image;
        this.RegiaoNome = regiaoNome;
        this.LocalNome = localNome;
        this.Descricao = descricao;
        this.CoordenadasX = coordenadasX;
        this.CoordenadasY = coordenadasY;
    }

    //Get
    public int getImage() {
        return Image;
    }

    public int getRegiaoNome() {
        return RegiaoNome;
    }

    public String getLocalNome() {
        return LocalNome;
    }

    public int getDescricao() {
        return Descricao;
    }

    public double getCoordenadasX() {
        return CoordenadasX;
    }

    public double getCoordenadasY() {
        return CoordenadasY;
    }
}
