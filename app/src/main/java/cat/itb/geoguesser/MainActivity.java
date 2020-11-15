package cat.itb.geoguesser;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    private TextView progresion, pregunta;
    private ProgressBar progressBar;
    private Button respuesta1, respuesta2, respuesta3, respuesta4, pista;
    private CountDownTimer cuentaAtras;
    private final QuestionModel[] questionBank = {
            new QuestionModel(R.string.Pregunta1, "8","5","1","6"),
            new QuestionModel(R.string.Pregunta2, "3","2","6","4"),
            new QuestionModel(R.string.Pregunta3, "2","1","8","5"),
            new QuestionModel(R.string.Pregunta4, "1","4","7","2"),
            new QuestionModel(R.string.Pregunta5, "4","1","9","10"),
            new QuestionModel(R.string.Pregunta6, "1","5","2","11"),
            new QuestionModel(R.string.Pregunta7, "8","12","10","3"),
            new QuestionModel(R.string.Pregunta8, "5","2","4","1"),
            new QuestionModel(R.string.Pregunta9, "4","7","9","2"),
            new QuestionModel(R.string.Pregunta10, "3","4","6","8"),
            new QuestionModel(R.string.Pregunta11, "1","2","5","3"),
            new QuestionModel(R.string.Pregunta12, "1","3","9","5"),
            new QuestionModel(R.string.Pregunta13, "3","4","6","8"),
            new QuestionModel(R.string.Pregunta14, "2","1","5","4"),
            new QuestionModel(R.string.Pregunta15, "1","5","10","3"),
            new QuestionModel(R.string.Pregunta16, "1","4","6","8"),
            new QuestionModel(R.string.Pregunta17, "1","2","3","4"),
    };
    int index = 0;
    double puntos = 0;
    int indexPista = 3;
    boolean pistaUsada;
    boolean temporizadorOff;
    int time = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pregunta = findViewById(R.id.pregunta);
        progressBar = findViewById(R.id.progressBar);
        respuesta1 = findViewById(R.id.respuesta1);
        respuesta2 = findViewById(R.id.respuesta2);
        respuesta3 = findViewById(R.id.respuesta3);
        respuesta4 = findViewById(R.id.respuesta4);
        progresion = findViewById(R.id.contadorPreguntas);
        pista = findViewById(R.id.pista);
        pista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarRespuestaCorrecta();
            }
        });

        if (index == 0){
            Collections.shuffle(Arrays.asList(questionBank));
        }
       mostrarPregunta();
    }
    
    public void mostrarPregunta(){
        ArrayList<String> test = new ArrayList<>();
        pregunta.setText(getResources().getString(questionBank[index].getPregunta()));
        String num = getString(R.string.contadorPreguntes, index +1);
        progresion.setText(num);

        test.add(questionBank[index].getRespuestaCorrecta());
        test.add(questionBank[index].getRespuestaMala1());
        test.add(questionBank[index].getRespuestaMala2());
        test.add(questionBank[index].getRespuestaMala3());

        Collections.shuffle(test);

        respuesta1.setBackgroundColor(Color.GRAY);  respuesta1.setText(test.get(0));
        respuesta2.setBackgroundColor(Color.GRAY);  respuesta2.setText(test.get(1));
        respuesta3.setBackgroundColor(Color.GRAY);  respuesta3.setText(test.get(2));
        respuesta4.setBackgroundColor(Color.GRAY);  respuesta4.setText(test.get(3));

        pistaUsada = false;
        temporizadorOff = false;
        time = 0;
        progressBar.setProgress(time);

       cuentaAtras =  new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time++;
                progressBar.setProgress(time*100/(10000/1000));
            }

            @Override
            public void onFinish() {
                temporizadorOff = true;
                mostrarRespuestaCorrecta();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        index = (index + 1) % questionBank.length;
                        if (index != 0){
                            mostrarPregunta();
                        } else {
                           finalizarPrograma();
                        }
                    }
                }, 3000);
            }
        };
        cuentaAtras.start();

        if (indexPista == 0){
            pista.setVisibility(View.INVISIBLE);
        }
    }

    public void checkAnswer(View v){
        cuentaAtras.cancel();
        Button respuesta = findViewById(v.getId());
        String btnText = respuesta.getText().toString();
        if (btnText.equals(questionBank[index].getRespuestaCorrecta())){
            respuesta.setBackgroundColor(Color.GREEN);
            if (!pistaUsada){
                puntos++;
            }
        } else {
            respuesta.setBackgroundColor(Color.RED);
            if (puntos > 0) {
               puntos = puntos - 0.5;
            }
        }
        Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    index = (index + 1) % questionBank.length;
                    if (index != 0){
                        mostrarPregunta();
                    } else {
                        finalizarPrograma();
                    }
                }
            }, 3000);
    }

    public void mostrarRespuestaCorrecta(){
        cuentaAtras.cancel();
        if (respuesta1.getText().toString().equals(questionBank[index].getRespuestaCorrecta())){
            respuesta1.setBackgroundColor(Color.GREEN);
        } else if (respuesta2.getText().toString().equals(questionBank[index].getRespuestaCorrecta())){
            respuesta2.setBackgroundColor(Color.GREEN);
        } else if (respuesta3.getText().toString().equals(questionBank[index].getRespuestaCorrecta())){
            respuesta3.setBackgroundColor(Color.GREEN);
        } else if (respuesta4.getText().toString().equals(questionBank[index].getRespuestaCorrecta())){
            respuesta4.setBackgroundColor(Color.GREEN);
        }
        if (!temporizadorOff){
           indexPista --;
           pistaUsada = true;
            if (indexPista ==0){
                Toast.makeText(MainActivity.this,"No te quedan usos del hint", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this,"Quedan " + indexPista + " usos del hint", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void finalizarPrograma(){
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("Has acabado el test!");
        builder1.setMessage("Puntuación: " + (puntos * 10) + " de 170");
        builder1.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder1.setNegativeButton("Volver a intentarlo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Collections.shuffle(Arrays.asList(questionBank));
                puntos = 0;
                indexPista = 3;
                pista.setVisibility(View.VISIBLE);
                mostrarPregunta();
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

}