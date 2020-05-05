package com.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData [][] = {
            {"Jak se jmenuje Tetka celým jménem?", "Karolína Anna Měřínská", "Patrície Skulníková", "Malá Lasička", "Karolína Morbitzerová"},
            {"Čím se živí Čiča?", "Architekt", "Projektant", "Stavbyvedoucí", "Účetní"},
            {"Jaké auto má Jarda?", "Volvo XC90", "BMW X5", "VW Touareg", "Škoda Kodiaq"},
            {"Kdo je nejvyšší vedoucí?", "Tomko", "Staňa", "Čiča", "Tetka"},
            {"Jarda je původně", "Pražák", "Brňák", "Vyškovák", "Olomoučák"},
            {"Kdo hraje na flétnu?", "Staňa", "Denča", "Polko", "Dušan"},
            {"Jaký má Denča titul?", "MUDr.", "JUDr.", "PhDr.", "Mgr."},
            {"Morbi a mladej Dušan jsou", "Bratři", "Kamarádi", "Bratranci", "Milenci"},
            {"Jak se jmenoval úplně první Jaduto tábor?", "Cesta do pravěku", "Star Wars", "Putování Galaxií", "Ostrov pokladů"},
            {"Chata, kde má Jaduto tábory se jmenuje", "Camila", "Kamila", "Camilla", "Kamilla"},
            {"Co má Džejna nejsilnější na světě?", "Stehna", "Lýtka", "Břicho", "Malíčky"},
            {"Kolik táborů Jaduto udělalo?", "Sedm", "Osm", "Šest", "Pět"},
            {"Co má Tomko nejraději na mňamku?", "Brambůrky", "Oříšky", "Čokoládu", "Gumové medvídky"},
            {"Jak se jmenuje naše kuchařka?", "Jaruška", "Drahuška", "Maruška", "Dobruška"},
            {"Kolik roků má Staňa?", "23", "19", "27", "31"}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);

        Log.v("CATEGORY_TAG", quizCategory + "");


        for (int i = 0; i< quizData.length; i++){
            ArrayList<String> tempArray = new ArrayList<>();
            tempArray.add(quizData[i][0]);
            tempArray.add(quizData[i][1]);
            tempArray.add(quizData[i][2]);
            tempArray.add(quizData[i][3]);
            tempArray.add(quizData[i][4]);



            quizArray.add(tempArray);


        }
        showNextQuiz();
    }

    public void showNextQuiz() {
        countLabel.setText("Otázka"+ quizCount);

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        quizArray.remove(randomNum);


    }
    public void checkAnswer(View view){

        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)){
            alertTitle = "Správně";
            rightAnswerCount++;


        } else{
            alertTitle = "Špatně...";

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Odpověď je: " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizArray.size()<1) {
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();

                }
            }
        });
        builder.setCancelable(false);
        builder.show();

    }
}
