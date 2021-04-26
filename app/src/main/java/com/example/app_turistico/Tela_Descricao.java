package com.example.app_turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.app_turistico.Constantes.ERROR_DIALOG_REQUEST;
import static com.example.app_turistico.Constantes.MAPVIEW_BUNDLE_KEY;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Tela_Descricao extends AppCompatActivity implements OnMapReadyCallback {

    ImageView descricao_image;
    TextView txt_nome_pnt_turstco, txt_descricao_pnt_turstco;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__descricao);
        getSupportActionBar().hide();

        descricao_image = findViewById(R.id.descricao_image);
        txt_nome_pnt_turstco = findViewById(R.id.txt_nome_pnt_turstco);
        txt_descricao_pnt_turstco = findViewById(R.id.txt_descricao_pnt_turstco);

        descricao_image.setImageResource(getIntent().getIntExtra("Imagem", 0));
        txt_nome_pnt_turstco.setText(getIntent().getStringExtra("LocalNome"));
        txt_descricao_pnt_turstco.setText(getIntent().getStringExtra("Descricao"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocal);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double coordenadasX, coordenadasY;
        coordenadasX = getIntent().getDoubleExtra("CoordenadasX",0);
        coordenadasY = getIntent().getDoubleExtra("CoordenadasY",0);

        mMap = googleMap;
        LatLng PntCoordenadas = new LatLng(coordenadasX, coordenadasY);
        mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PntCoordenadas,18));
    }

}