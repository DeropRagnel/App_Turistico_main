package com.example.app_turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.app_turistico.HomeAdapter.FeaturedAdapter;
import com.example.app_turistico.HomeAdapter.FeaturedHelperClass;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static com.example.app_turistico.Constantes.ERROR_DIALOG_REQUEST;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Tela_Pontos_Turisticos extends AppCompatActivity {

    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    SearchView searchPtn;

    FeaturedAdapter featuredAdapter;

    private boolean mLocalizacaoGarantida = false;
    private static final String Tag = "Tela_Pontos_Turisticos";
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__pontos__turisticos);
        getSupportActionBar().hide();

        featuredRecycler = (RecyclerView) findViewById(R.id.featured_recycler);
        featuredRecycler();

        searchPtn = (SearchView) findViewById(R.id.search_pnt_turst);
        Filtrar();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    //Reciclador
    private void featuredRecycler() {

        //Regioes
        String rg_central = getString(R.string.centro);
        String rg_norte = getString(R.string.norte);
        String rg_sul = getString(R.string.sul);
        String rg_leste = getString(R.string.leste);
        String rg_oeste = getString(R.string.oeste);

        //Nomes dos Locais
        String avenida_paulista = getString(R.string.avenida_paulista);
        String bairro_liberdade = getString(R.string.bairro_liberdade);
        String beco_do_batman = getString(R.string.beco_do_batman);
        String centro_cultural_bnco_brsl =getString(R.string.centro_cultural_bnco_brsl);
        String estadio_morumbi = getString(R.string.estadio_morumbi);
        String marco_zero = getString(R.string.marco_zero);
        String mercado_municipal = getString(R.string.mercado_municipal);
        String mosteiro_de_sao_bento = getString(R.string.mosteiro_de_sao_bento);
        String masp = getString(R.string.masp);
        String parque_ibirapuera = getString(R.string.parque_ibirapuera);
        String pateo_do_colegio = getString(R.string.pateo_do_colegio);
        String pico_jaragua = getString(R.string.pico_jaragua);
        String pinacoteca = getString(R.string.pinacoteca);
        String rua_25_de_marco = getString(R.string.rua_25_de_marco);
        String solar_da_marqueza = getString(R.string.solar_da_marqueza);
        String trilha_mata_atlantica = getString(R.string.trilha_mata_atlantica);

        //Descrições
        String desc_avenida_paulista = getString(R.string.desc_avenida_paulista);
        String desc_bairro_liberdade = getString(R.string.desc_bairro_liberdade);
        String desc_beco_do_batman = getString(R.string.desc_beco_do_batman);
        String desc_centro_cultural_bnco_brsl = getString(R.string.desc_centro_cultural_bnco_brsl);
        String desc_estadio_morumbi = getString(R.string.desc_estadio_morumbi);
        String desc_marco_zero = getString(R.string.desc_marco_zero);
        String desc_mercado_municipal = getString(R.string.desc_mercado_municipal);
        String desc_mosteiro_de_sao_bento = getString(R.string.desc_mosteiro_de_sao_bento);
        String desc_masp = getString(R.string.desc_masp);
        String desc_parque_ibirapuera = getString(R.string.desc_parque_ibirapuera);
        String desc_pateo_do_colegio = getString(R.string.desc_pateo_do_colegio);
        String desc_pico_jaragua = getString(R.string.desc_pico_jaragua);
        String desc_pinacoteca = getString(R.string.desc_pinacoteca);
        String desc_rua_25_de_marco = getString(R.string.desc_rua_25_de_marco);
        String desc_solar_da_marqueza = getString(R.string.desc_solar_da_marqueza);
        String desc_trilha_mata_atlantica = getString(R.string.desc_trilha_mata_atlantica);

        //Coordenadas
        double avenida_paulistaX = Double.parseDouble(getString(R.string.coord_avenida_paulista_X));    double avenida_paulistaY = Double.parseDouble(getString(R.string.coord_avenida_paulista_Y));
        double bairro_liberdadeX = Double.parseDouble(getString(R.string.coord_bairro_liberdade_X));  double bairro_liberdadeY = Double.parseDouble(getString(R.string.coord_bairro_liberdade_Y));
        double beco_do_batmanX = Double.parseDouble(getString(R.string.coord_beco_do_batman_X));    double beco_do_batmanY = Double.parseDouble(getString(R.string.coord_beco_do_batman_Y));
        double centro_cultural_bnco_brslX = Double.parseDouble(getString(R.string.coord_centro_cultural_bnco_brsl_X)); double centro_cultural_bnco_brslY = Double.parseDouble(getString(R.string.coord_centro_cultural_bnco_brsl_Y));
        double estadio_morumbiX = Double.parseDouble(getString(R.string.coord_estadio_morumbi_X)); double estadio_morumbiY = Double.parseDouble(getString(R.string.coord_estadio_morumbi_Y));
        double marco_zeroX = Double.parseDouble(getString(R.string.coord_marco_zero_X)); double marco_zeroY = Double.parseDouble(getString(R.string.coord_marco_zero_Y));
        double mercado_municipalX = Double.parseDouble(getString(R.string.coord_mercado_municipal_X)); double mercado_municipalY = Double.parseDouble(getString(R.string.coord_mercado_municipal_Y));
        double mosteiro_de_sao_bentoX = Double.parseDouble(getString(R.string.coord_mosteiro_de_sao_bento_X)); double mosteiro_de_sao_bentoY = Double.parseDouble(getString(R.string.coord_mosteiro_de_sao_bento_Y));
        double maspX = Double.parseDouble(getString(R.string.coord_masp_X)); double maspY = Double.parseDouble(getString(R.string.coord_masp_Y));
        double parque_ibirapueraX = Double.parseDouble(getString(R.string.coord_parque_ibirapuera_X)); double parque_ibirapueraY = Double.parseDouble(getString(R.string.coord_parque_ibirapuera_Y));
        double pateo_do_colegioX = Double.parseDouble(getString(R.string.coord_pateo_do_colegio_X)); double pateo_do_colegioY = Double.parseDouble(getString(R.string.coord_pateo_do_colegio_Y));
        double pico_jaraguaX = Double.parseDouble(getString(R.string.coord_pico_jaragua_X)); double pico_jaraguaY = Double.parseDouble(getString(R.string.coord_pico_jaragua_Y));
        double pinacotecaX = Double.parseDouble(getString(R.string.coord_pinacoteca_X)); double pinacotecaY = Double.parseDouble(getString(R.string.coord_pinacoteca_Y));
        double rua_25_de_marcoX = Double.parseDouble(getString(R.string.coord_rua_25_de_marco_X)); double rua_25_de_marcoY = Double.parseDouble(getString(R.string.coord_rua_25_de_marco_Y));
        double solar_da_marquezaX = Double.parseDouble(getString(R.string.coord_solar_da_marqueza_X)); double solar_da_marquezaY = Double.parseDouble(getString(R.string.coord_solar_da_marqueza_Y));
        double trilha_mata_atlanticaX = Double.parseDouble(getString(R.string.coord_trilha_mata_atlantica_X)); double trilha_mata_atlanticaY = Double.parseDouble(getString(R.string.coord_trilha_mata_atlantica_Y));


        //Reciclador
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<FeaturedHelperClass> featuredLocation = new ArrayList<>();

        featuredLocation.add(new FeaturedHelperClass(R.drawable.avenida_paulista_sp, "Região " + rg_central, avenida_paulista, desc_avenida_paulista, avenida_paulistaX, avenida_paulistaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.bairro_liberdade_sp, "Região " + rg_central, bairro_liberdade, desc_bairro_liberdade, bairro_liberdadeX, bairro_liberdadeY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.beco_do_batman_sp, "Região " + rg_oeste, beco_do_batman, desc_beco_do_batman, beco_do_batmanX, beco_do_batmanY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.centro_cultural_bnco_brsl_sp, "Região " + rg_central, centro_cultural_bnco_brsl, desc_centro_cultural_bnco_brsl, centro_cultural_bnco_brslX, centro_cultural_bnco_brslY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.estadio_morumbi_sp, "Região " + rg_oeste + "/" + rg_sul, estadio_morumbi, desc_estadio_morumbi, estadio_morumbiX, estadio_morumbiY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.marco_zero_sp, "Região " + rg_central, marco_zero, desc_marco_zero, marco_zeroX, marco_zeroY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mercado_municipal_sp, "Região " + rg_central, mercado_municipal, desc_mercado_municipal, mercado_municipalX, mercado_municipalY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mosteiro_de_sao_bento_sp, "Região " + rg_central, mosteiro_de_sao_bento, desc_mosteiro_de_sao_bento, mosteiro_de_sao_bentoX, mosteiro_de_sao_bentoY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.museu_de_arte_sp, "Região " + rg_central, masp, desc_masp, maspX, maspY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.parque_ibirapuera_sp, "Região " + rg_sul,parque_ibirapuera, desc_parque_ibirapuera, parque_ibirapueraX, parque_ibirapueraY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pateo_do_colegio_sp, "Região " + rg_central, pateo_do_colegio, desc_pateo_do_colegio, pateo_do_colegioX, pateo_do_colegioY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pico_jaragua_sp, "Região " + rg_oeste, pico_jaragua, desc_pico_jaragua, pico_jaraguaX, pico_jaraguaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pinacoteca_sp, "Região " + rg_central, pinacoteca, desc_pinacoteca, pinacotecaX, pinacotecaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.rua_25_de_marco_sp, "Região " + rg_central, rua_25_de_marco, desc_rua_25_de_marco, rua_25_de_marcoX, rua_25_de_marcoY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.solar_da_marqueza_de_santos_sp, "Região " + rg_central, solar_da_marqueza, desc_solar_da_marqueza, solar_da_marquezaX, solar_da_marquezaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.trilha_mata_atlantica_sp, "Região " + rg_sul, trilha_mata_atlantica, desc_trilha_mata_atlantica, trilha_mata_atlanticaX, trilha_mata_atlanticaY));


        featuredAdapter = new FeaturedAdapter(featuredLocation,getApplicationContext());
        featuredRecycler.setAdapter(featuredAdapter);

    }

    //Filtro
    private void Filtrar() {

        ArrayList<FeaturedHelperClass> featuredLocation = new ArrayList<>();

        searchPtn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                featuredAdapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                featuredAdapter.getFilter().filter(newText);

                return false;
            }
        });

    }



    //GoogleMaps Configuração

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esse Aplicativo requer o uso de GPS para funcionar corretamente, você gostaria de permitir?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocalizacaoGarantida = true;
            //Pode colocar o FeaturedReclycler
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    public boolean isServicesOK(){
        Log.d(Tag, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Tela_Pontos_Turisticos.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(Tag, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(Tag, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Tela_Pontos_Turisticos.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocalizacaoGarantida = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // Se o request é cancelado os arrays do resultado ficam vazios.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocalizacaoGarantida = true;
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Tag, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocalizacaoGarantida){
                    //Pode colocar o FeaturedReclycler
                }
                else{
                    getLocationPermission();
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(mLocalizacaoGarantida){
                //Pode colocar o FeaturedReclycler
            }
            else{
                getLocationPermission();
            }
        }
    }

}