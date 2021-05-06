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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Tela_Cadastro extends AppCompatActivity {

    TextView txt_titulo_cadastro;
    EditText edit_cadastrar_email;
    EditText edit_cadastrar_senha;
    EditText edit_cadastrar_senha_confirmar;
    Button btn_cadastro;
    ImageButton btn_voltar_telaLogin;
    Spinner sLanguage;
    ArrayAdapter langAdapter;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__cadastro);

        mAuth = FirebaseAuth.getInstance();

        txt_titulo_cadastro = findViewById(R.id.txt_titulo_cadastro);
        edit_cadastrar_email = findViewById(R.id.edit_cadastrar_email);
        edit_cadastrar_senha = findViewById(R.id.edit_cadastrar_senha);
        edit_cadastrar_senha_confirmar = findViewById(R.id.edit_cadastrar_senha_confirmar);
        btn_cadastro = findViewById(R.id.btn_cadastro);
        btn_voltar_telaLogin = findViewById(R.id.btn_voltar_telaLogin);


        //Tradutor
        sLanguage = findViewById(R.id.sLanguage);
        langAdapter = new ArrayAdapter<String>(Tela_Cadastro.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        sLanguage.setAdapter(langAdapter);

        if (LocaleHelper.getLanguage(Tela_Cadastro.this).equalsIgnoreCase("pt_br")) {
            sLanguage.setSelection(langAdapter.getPosition("Português"));
        } else if (LocaleHelper.getLanguage(Tela_Cadastro.this).equalsIgnoreCase("en")) {
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
                        context = LocaleHelper.setLocale(Tela_Cadastro.this, "pt_br");
                        resources = context.getResources();
                        txt_titulo_cadastro.setText(resources.getString(R.string.titulo_cadastro));
                        edit_cadastrar_email.setHint(resources.getString(R.string.digite_seu_email));
                        edit_cadastrar_senha.setHint(resources.getString(R.string.digite_sua_senha));
                        edit_cadastrar_senha_confirmar.setHint(resources.getString(R.string.confirme_sua_senha));
                        btn_cadastro.setText(resources.getString(R.string.botao_cadastro));
                        break;

                    case 1:
                        context = LocaleHelper.setLocale(Tela_Cadastro.this, "en");
                        resources = context.getResources();
                        txt_titulo_cadastro.setText(resources.getString(R.string.titulo_cadastro));
                        edit_cadastrar_email.setHint(resources.getString(R.string.digite_seu_email));
                        edit_cadastrar_senha.setHint(resources.getString(R.string.digite_sua_senha));
                        edit_cadastrar_senha_confirmar.setHint(resources.getString(R.string.confirme_sua_senha));
                        btn_cadastro.setText(resources.getString(R.string.botao_cadastro));
                        break;

                    case 2:
                        context = LocaleHelper.setLocale(Tela_Cadastro.this, "es");
                        resources = context.getResources();
                        txt_titulo_cadastro.setText(resources.getString(R.string.titulo_cadastro));
                        edit_cadastrar_email.setHint(resources.getString(R.string.digite_seu_email));
                        edit_cadastrar_senha.setHint(resources.getString(R.string.digite_sua_senha));
                        edit_cadastrar_senha_confirmar.setHint(resources.getString(R.string.confirme_sua_senha));
                        btn_cadastro.setText(resources.getString(R.string.botao_cadastro));
                        break;
                }
                ((TextView)view).setText(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Voltar para Tela Login
        btn_voltar_telaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaLogin();
            }
        });

        //Cadastrar
        btn_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailCadastro = edit_cadastrar_email.getText().toString();
                String senhaCadastro = edit_cadastrar_senha.getText().toString();
                String confirmar_senhaCadastro = edit_cadastrar_senha_confirmar.getText().toString();

                if (!TextUtils.isEmpty(emailCadastro) || !TextUtils.isEmpty(senhaCadastro) || !TextUtils.isEmpty(confirmar_senhaCadastro))
                {
                    if (senhaCadastro.equals(confirmar_senhaCadastro))
                    {
                        mAuth.createUserWithEmailAndPassword(emailCadastro, senhaCadastro).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    abrirTelaLogin();
                                }
                                else
                                {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(Tela_Cadastro.this, ""+error, Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else {
                        Toast.makeText(Tela_Cadastro.this, "A senha deve ser a mesma em ambos os campos!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Tela_Cadastro.this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(Tela_Cadastro.this, Tela_Login.class);
        startActivity(intent);
        finish();
    }
}