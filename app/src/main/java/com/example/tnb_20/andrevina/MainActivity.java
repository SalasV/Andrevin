package com.example.tnb_20.andrevina;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private int rango;
    public static List<Jugador> jugador = new ArrayList<>();
    private int intentos = 0;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startApp();


    }

    private void startApp() {
        preguntarNombre();
        final Button button = findViewById(R.id.button);
        final Button botonRecord = findViewById(R.id.button2);
        rango = generateRandom();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adivinarNumero();
            }
        });

        botonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tablaRecord();
            }
        });

    }

    private int generateRandom() {

        int numeroAleatorio = (int) (Math.random() * 100 + 1);

        return numeroAleatorio;
    }

    public void adivinarNumero() {
        final EditText editText = findViewById(R.id.editText);
        String st = String.valueOf(editText.getText());
        int numero = Integer.parseInt(st);
        if (numero > rango) {
            intentos++;
            Context context = getApplicationContext();
            CharSequence text = "Pon un numero mas pequeño";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else if (numero < rango) {
            Context context = getApplicationContext();
            CharSequence text = "Pon un numero mas grande";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            intentos++;
        } else if (numero == rango) {
            jugador.add(new Jugador(name, intentos));
            fichero(new Jugador(name, intentos));
            Context context = getApplicationContext();
            CharSequence text = "Lo has adivinado";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            rango = generateRandom();
            intentos = 0;
            preguntarNombre();
        }
    }

    public void tablaRecord() {
        Intent i = new Intent(this, HallOfFame.class);
        startActivity(i);
    }

    private String preguntarNombre() {
        final Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Registro de Usuario");
        dialog.show();
        Button register = dialog.findViewById(R.id.botonDialog);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textName = dialog.findViewById(R.id.etNombre);
                name = textName.getText().toString();
                dialog.dismiss();
            }
        });
        return name;
    }

    private void fichero(Jugador jug) {
        try {
            OutputStreamWriter osw =
                    new OutputStreamWriter(
                            openFileOutput("tablaRecord.txt", Context.MODE_APPEND));

            osw.write(jug.getName() + "-" + jug.getIntentos());
            osw.append("\r\n");
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}