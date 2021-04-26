package com.example.app_turistico.HomeAdapter;

import com.google.android.gms.maps.model.LatLng;

public class FeaturedHelperClass {
    int Image;
    String RegiaoNome, LocalNome, Descricao;
    double CoordenadasX, CoordenadasY;

    public FeaturedHelperClass(int Image, String regiaoNome, String localNome, String descricao, double coordenadasX, double coordenadasY) {
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

    public String getRegiaoNome() {
        return RegiaoNome;
    }

    public String getLocalNome() {
        return LocalNome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public double getCoordenadasX() {
        return CoordenadasX;
    }

    public double getCoordenadasY() {
        return CoordenadasY;
    }
}
