package com.example.app_turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_turistico.HomeAdapter.FeaturedAdapter;
import com.example.app_turistico.HomeAdapter.FeaturedHelperClass;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.app_turistico.Constantes.ERROR_DIALOG_REQUEST;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.app_turistico.Constantes.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Tela_Pontos_Turisticos extends AppCompatActivity {

    FeaturedAdapter featuredAdapter;
    RecyclerView featuredRecycler;
    SearchView searchPtn;
    Spinner sLanguage;
    ArrayAdapter langAdapter;
    TextView tituloPtn;

    ImageButton btn_logout;
    FirebaseAuth mAuth;

    private boolean mLocalizacaoGarantida = false;
    private static final String Tag = "Tela_Pontos_Turisticos";
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__pontos__turisticos);

        tituloPtn = findViewById(R.id.txt_titulo);

        mAuth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intent = new Intent(Tela_Pontos_Turisticos.this, Tela_Login.class);
                startActivity(intent);
                finish();
            }
        });

        //Reciclador
        featuredRecycler = (RecyclerView) findViewById(R.id.featured_recycler);
        featuredRecycler();

        //Filtro
        searchPtn = (SearchView) findViewById(R.id.search_pnt_turst);
        Filtrar();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //Tradutor
        sLanguage = findViewById(R.id.sLanguage);
        langAdapter = new ArrayAdapter<String>(Tela_Pontos_Turisticos.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        sLanguage.setAdapter(langAdapter);

        if (LocaleHelper.getLanguage(Tela_Pontos_Turisticos.this).equalsIgnoreCase("pt_br")) {
            sLanguage.setSelection(langAdapter.getPosition("Português"));
        } else if (LocaleHelper.getLanguage(Tela_Pontos_Turisticos.this).equalsIgnoreCase("en")) {
            sLanguage.setSelection(langAdapter.getPosition("English"));
        } else {
            sLanguage.setSelection(langAdapter.getPosition("Español"));
        }

        sLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 0:
                        context = LocaleHelper.setLocale(Tela_Pontos_Turisticos.this, "pt_br");
                        resources = context.getResources();
                        tituloPtn.setText(resources.getString(R.string.titulo_pnts_turstcos));
                        searchPtn.setQueryHint(resources.getString(R.string.search_text));
                        featuredRecycler.setAdapter(featuredAdapter);
                        break;

                    case 1:
                        context = LocaleHelper.setLocale(Tela_Pontos_Turisticos.this, "en");
                        resources = context.getResources();
                        tituloPtn.setText(resources.getString(R.string.titulo_pnts_turstcos));
                        searchPtn.setQueryHint(resources.getString(R.string.search_text));
                        featuredRecycler.setAdapter(featuredAdapter);
                        break;

                    case 2:
                        context = LocaleHelper.setLocale(Tela_Pontos_Turisticos.this, "es");
                        resources = context.getResources();
                        tituloPtn.setText(resources.getString(R.string.titulo_pnts_turstcos));
                        searchPtn.setQueryHint(resources.getString(R.string.search_text));
                        featuredRecycler.setAdapter(featuredAdapter);
                        break;
                }
                ((TextView) view).setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioatual = FirebaseAuth.getInstance().getCurrentUser();
        if (usuarioatual == null){
            Intent intent = new Intent(Tela_Pontos_Turisticos.this, Tela_Login.class);
            startActivity(intent);
            finish();
        }
    }

    //Reciclador
    private void featuredRecycler() {

        //Nomes dos Locais
        String avenida_paulista = getString(R.string.avenida_paulista);
        String bairro_liberdade = getString(R.string.bairro_liberdade);
        String beco_do_batman = getString(R.string.beco_do_batman);
        String centro_cultural_bnco_brsl = getString(R.string.centro_cultural_bnco_brsl);
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


        //Coordenadas
        double avenida_paulistaX = Double.parseDouble(getString(R.string.coord_avenida_paulista_X));
        double avenida_paulistaY = Double.parseDouble(getString(R.string.coord_avenida_paulista_Y));
        double bairro_liberdadeX = Double.parseDouble(getString(R.string.coord_bairro_liberdade_X));
        double bairro_liberdadeY = Double.parseDouble(getString(R.string.coord_bairro_liberdade_Y));
        double beco_do_batmanX = Double.parseDouble(getString(R.string.coord_beco_do_batman_X));
        double beco_do_batmanY = Double.parseDouble(getString(R.string.coord_beco_do_batman_Y));
        double centro_cultural_bnco_brslX = Double.parseDouble(getString(R.string.coord_centro_cultural_bnco_brsl_X));
        double centro_cultural_bnco_brslY = Double.parseDouble(getString(R.string.coord_centro_cultural_bnco_brsl_Y));
        double estadio_morumbiX = Double.parseDouble(getString(R.string.coord_estadio_morumbi_X));
        double estadio_morumbiY = Double.parseDouble(getString(R.string.coord_estadio_morumbi_Y));
        double marco_zeroX = Double.parseDouble(getString(R.string.coord_marco_zero_X));
        double marco_zeroY = Double.parseDouble(getString(R.string.coord_marco_zero_Y));
        double mercado_municipalX = Double.parseDouble(getString(R.string.coord_mercado_municipal_X));
        double mercado_municipalY = Double.parseDouble(getString(R.string.coord_mercado_municipal_Y));
        double mosteiro_de_sao_bentoX = Double.parseDouble(getString(R.string.coord_mosteiro_de_sao_bento_X));
        double mosteiro_de_sao_bentoY = Double.parseDouble(getString(R.string.coord_mosteiro_de_sao_bento_Y));
        double maspX = Double.parseDouble(getString(R.string.coord_masp_X));
        double maspY = Double.parseDouble(getString(R.string.coord_masp_Y));
        double parque_ibirapueraX = Double.parseDouble(getString(R.string.coord_parque_ibirapuera_X));
        double parque_ibirapueraY = Double.parseDouble(getString(R.string.coord_parque_ibirapuera_Y));
        double pateo_do_colegioX = Double.parseDouble(getString(R.string.coord_pateo_do_colegio_X));
        double pateo_do_colegioY = Double.parseDouble(getString(R.string.coord_pateo_do_colegio_Y));
        double pico_jaraguaX = Double.parseDouble(getString(R.string.coord_pico_jaragua_X));
        double pico_jaraguaY = Double.parseDouble(getString(R.string.coord_pico_jaragua_Y));
        double pinacotecaX = Double.parseDouble(getString(R.string.coord_pinacoteca_X));
        double pinacotecaY = Double.parseDouble(getString(R.string.coord_pinacoteca_Y));
        double rua_25_de_marcoX = Double.parseDouble(getString(R.string.coord_rua_25_de_marco_X));
        double rua_25_de_marcoY = Double.parseDouble(getString(R.string.coord_rua_25_de_marco_Y));
        double solar_da_marquezaX = Double.parseDouble(getString(R.string.coord_solar_da_marqueza_X));
        double solar_da_marquezaY = Double.parseDouble(getString(R.string.coord_solar_da_marqueza_Y));
        double trilha_mata_atlanticaX = Double.parseDouble(getString(R.string.coord_trilha_mata_atlantica_X));
        double trilha_mata_atlanticaY = Double.parseDouble(getString(R.string.coord_trilha_mata_atlantica_Y));


        //Reciclador
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<FeaturedHelperClass> featuredLocation = new ArrayList<>();


        featuredLocation.add(new FeaturedHelperClass(R.drawable.avenida_paulista_sp, R.string.centro, avenida_paulista, R.string.desc_avenida_paulista, avenida_paulistaX, avenida_paulistaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.bairro_liberdade_sp, R.string.centro, bairro_liberdade, R.string.desc_bairro_liberdade, bairro_liberdadeX, bairro_liberdadeY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.beco_do_batman_sp, R.string.oeste, beco_do_batman, R.string.desc_beco_do_batman, beco_do_batmanX, beco_do_batmanY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.centro_cultural_bnco_brsl_sp, R.string.centro, centro_cultural_bnco_brsl, R.string.desc_centro_cultural_bnco_brsl, centro_cultural_bnco_brslX, centro_cultural_bnco_brslY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.estadio_morumbi_sp, R.string.suldoeste, estadio_morumbi, R.string.desc_estadio_morumbi, estadio_morumbiX, estadio_morumbiY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.marco_zero_sp, R.string.centro, marco_zero, R.string.desc_marco_zero, marco_zeroX, marco_zeroY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mercado_municipal_sp, R.string.centro, mercado_municipal, R.string.desc_mercado_municipal, mercado_municipalX, mercado_municipalY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.mosteiro_de_sao_bento_sp, R.string.centro, mosteiro_de_sao_bento, R.string.desc_mosteiro_de_sao_bento, mosteiro_de_sao_bentoX, mosteiro_de_sao_bentoY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.museu_de_arte_sp, R.string.centro, masp, R.string.desc_masp, maspX, maspY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.parque_ibirapuera_sp, R.string.sul, parque_ibirapuera, R.string.desc_parque_ibirapuera, parque_ibirapueraX, parque_ibirapueraY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pateo_do_colegio_sp, R.string.centro, pateo_do_colegio, R.string.desc_pateo_do_colegio, pateo_do_colegioX, pateo_do_colegioY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pico_jaragua_sp, R.string.sul, pico_jaragua, R.string.desc_pico_jaragua, pico_jaraguaX, pico_jaraguaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.pinacoteca_sp, R.string.centro, pinacoteca, R.string.desc_pinacoteca, pinacotecaX, pinacotecaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.rua_25_de_marco_sp, R.string.centro, rua_25_de_marco, R.string.desc_rua_25_de_marco, rua_25_de_marcoX, rua_25_de_marcoY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.solar_da_marqueza_de_santos_sp, R.string.centro, solar_da_marqueza, R.string.desc_solar_da_marqueza, solar_da_marquezaX, solar_da_marquezaY));
        featuredLocation.add(new FeaturedHelperClass(R.drawable.trilha_mata_atlantica_sp, R.string.sul, trilha_mata_atlantica, R.string.desc_trilha_mata_atlantica, trilha_mata_atlanticaX, trilha_mata_atlanticaY));


        featuredAdapter = new FeaturedAdapter(featuredLocation, getApplicationContext());
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

    private void GetLastKnowLocation() {
        Log.d(Tag, "getLastknowLocation: called");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null){
                    try
                    {
                        Geocoder geocoder = new Geocoder(Tela_Pontos_Turisticos.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    }
                    catch (IOException exception)
                    {
                        exception.printStackTrace();
                    }


                }

            }
        });
    }

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
        builder.setMessage(R.string.aviso_acesso_localizacao)
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
            GetLastKnowLocation();
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
                    GetLastKnowLocation();
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
                GetLastKnowLocation();
            }
            else{
                getLocationPermission();
            }
        }
    }

}