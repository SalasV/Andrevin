package com.example.tnb_20.andrevina;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HallOfFame extends Activity {
   private List<Jugador> jugadors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        mostrarDatos();
    }

    private void mostrarDatos() {
        jugadors = new ArrayList<>();
        leerFicheros();

        final TextView tablaRecord = findViewById(R.id.record);
        tablaRecord.setText("");
        if (jugadors.size() > 0) {
            Collections.sort(jugadors);
            for (Jugador jug :
                    jugadors) {
                tablaRecord.setText(tablaRecord.getText() + jug.toString());
            }
        } else {
            tablaRecord.setText(tablaRecord.getText() + "No hay datos registrados");
        }

    }

    private void leerFicheros() {
        try {
            BufferedReader br=
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput("tablaRecord.txt")));
            String linea;
            while ((linea=br.readLine())!=null){
                String[] cadena=linea.split("-");
                jugadors.add(new Jugador(cadena[0],Integer.parseInt(cadena[1])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}