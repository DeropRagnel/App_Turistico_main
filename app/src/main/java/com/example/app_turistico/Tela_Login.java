package com.example.app_turistico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Tela_Login extends AppCompatActivity {

    TextView txt_titulo_login;
    EditText edit_email_login;
    EditText edit_senha_login;;
    Button btn_login;
    Button btn_criar_conta;
    Spinner sLanguage;
    ArrayAdapter langAdapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__login);

        txt_titulo_login = findViewById(R.id.txt_titulo_login);
        edit_email_login = findViewById(R.id.edit_email_login);
        edit_senha_login = findViewById(R.id.edit_senha_login);
        btn_login = findViewById(R.id.btn_login);
        btn_criar_conta = findViewById(R.id.btn_criar_conta);
        mAuth = FirebaseAuth.getInstance();

        //Tradutor
        sLanguage = findViewById(R.id.sLanguage);
        langAdapter = new ArrayAdapter<String>(Tela_Login.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        sLanguage.setAdapter(langAdapter);

        if (LocaleHelper.getLanguage(Tela_Login.this).equalsIgnoreCase("pt_br")) {
            sLanguage.setSelection(langAdapter.getPosition("Português"));
        } else if (LocaleHelper.getLanguage(Tela_Login.this).equalsIgnoreCase("en")) {
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
                        context = LocaleHelper.setLocale(Tela_Login.this, "pt_br");
                        resources = context.getResources();
                        txt_titulo_login.setText(resources.getString(R.string.titulo_Login));
                        edit_email_login.setHint(resources.getString(R.string.digite_seu_email));
                        edit_senha_login.setHint(resources.getString(R.string.digite_sua_senha));
                        btn_login.setText(resources.getString(R.string.botao_login));
                        btn_criar_conta.setText(resources.getString(R.string.botao_criar_conta));
                        break;

                    case 1:
                        context = LocaleHelper.setLocale(Tela_Login.this, "en");
                        resources = context.getResources();
                        txt_titulo_login.setText(resources.getString(R.string.titulo_Login));
                        edit_email_login.setHint(resources.getString(R.string.digite_seu_email));
                        edit_senha_login.setHint(resources.getString(R.string.digite_sua_senha));
                        btn_login.setText(resources.getString(R.string.botao_login));
                        btn_criar_conta.setText(resources.getString(R.string.botao_criar_conta));
                        break;

                    case 2:
                        context = LocaleHelper.setLocale(Tela_Login.this, "es");
                        resources = context.getResources();
                        txt_titulo_login.setText(resources.getString(R.string.titulo_Login));
                        edit_email_login.setHint(resources.getString(R.string.digite_seu_email));
                        edit_senha_login.setHint(resources.getString(R.string.digite_sua_senha));
                        btn_login.setText(resources.getString(R.string.botao_login));
                        btn_criar_conta.setText(resources.getString(R.string.botao_criar_conta));
                        break;
                }
                ((TextView)view).setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailLogin = edit_email_login.getText().toString();
                String senhaLogin = edit_senha_login.getText().toString();

                if (!TextUtils.isEmpty(emailLogin) || !TextUtils.isEmpty(senhaLogin))
                {
                    mAuth.signInWithEmailAndPassword(emailLogin, senhaLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                abrirTelaPontosTuristicos();
                            }
                            else {
                                String error = task.getException().getMessage();
                                Toast.makeText(Tela_Login.this, "" + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(Tela_Login.this, "Todos os campos precisam ser preenchidos", Toast.LENGTH_SHORT);
                }

            }
        });

        //Ir pra tela Cadastro
        btn_criar_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tela_Login.this, Tela_Cadastro.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void abrirTelaPontosTuristicos() {
        Intent intent = new Intent(Tela_Login.this, Tela_Pontos_Turisticos.class);
        startActivity(intent);
        finish();
    }
}