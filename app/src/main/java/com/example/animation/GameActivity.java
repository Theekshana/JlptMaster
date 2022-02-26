package com.example.animation;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.muddzdev.styleabletoast.StyleableToast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;



public class GameActivity extends AppCompatActivity {

    private static final int WAIT_TIME=500;
    private long backPressedTime;
    private Toast backToast;
    private TextView questionCount;
    private TextView question;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    private String rigthAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    final static protected int QUIZ_COUNT = 3;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String[][] quizData = {

            //{Kanji , right answer , choice1 , choice2, choice 3}
            {"日", "にち", "やま", "ほん", "かわ"},
            {"月", "つき", "ほん", "かね", "かわ"},
            {"火", "ひ", "かいしゃ", "かね", "いち"},
            {"水", "みず", "にち", "かね", "いち"},
            {"木", "き", "にち", "やま", "ひ"},
            {"金", "かね", "かいしゃ", "ひ", "いち"},
            {"土", "つち", "かいしゃ", "ひ", "き"},
            {"山", "やま", "かね", "ひ", "いち"},
            {"川", "かわ", "やま", "かね", "ひ"},
            {"ー", "いち", "かいしゃ", "かね", "ひ"},


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        questionCount = findViewById(R.id.Qnum);
        question = findViewById(R.id.Question);
        answerBtn1 = findViewById(R.id.ansBtn1);
        answerBtn2 = findViewById(R.id.ansBtn2);
        answerBtn3 = findViewById(R.id.ansBtn3);
        answerBtn4 = findViewById(R.id.ansBtn4);

        //create quiz array from quizData

        for (int i = 0; i < quizData.length; i++) {

            //prepare for array

            ArrayList<String> temArray = new ArrayList<>();

            temArray.add(quizData[i][0]); //kanji
            temArray.add(quizData[i][1]); //right answer
            temArray.add(quizData[i][2]); //choice1
            temArray.add(quizData[i][3]); //choice 2
            temArray.add(quizData[i][4]); //choice 3

            quizArray.add(temArray);

        }



        showNextQuiz();

    }

    public void showNextQuiz(){

        //update quizCount

        questionCount.setText("Q :" +quizCount);

        //generate random number from quizArray

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        //pick one quiz set
        ArrayList<String> quiz = quizArray.get(randomNum);

        //set question and right answer
        //array format //{Kanji , right answer , choice1 , choice2, choice 3}

        question.setText(quiz.get(0));
        rigthAnswer = quiz.get(1);

        //remove kanji and shuffle answers

        quiz.remove(0);
        Collections.shuffle(quiz);

        //set answers

        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        //remove this quiz from QuizArray

        quizArray.remove(randomNum);


    }



    @SuppressLint("ResourceType")
    public void checkAnswer(View view){

        //get pushed button

        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();


        answerBtn1.setEnabled(false);
        answerBtn2.setEnabled(false);
        answerBtn3.setEnabled(false);
        answerBtn4.setEnabled(false);

        String alert;

        if(btnText.equals(rigthAnswer)){

            //correct

            alert = "Correct";
            showToastOk(view);

            rightAnswerCount++;


            //quizCount++;
            //showNextQuiz();

            if (quizCount == QUIZ_COUNT){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),EndActivity.class);
                        intent.putExtra("RIGHT_ANSWER_COUNT",rightAnswerCount);
                        startActivity(intent);
                        finish();
                    }
                },WAIT_TIME);

                //show result

                //Intent intent = new Intent(getApplicationContext(),EndActivity.class);
                //intent.putExtra("RIGHT_ANSWER_COUNT",rightAnswerCount);
                //startActivity(intent);

            }else {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        quizCount++;
                        showNextQuiz();
                        answerBtn1.setEnabled(true);
                        answerBtn2.setEnabled(true);
                        answerBtn3.setEnabled(true);
                        answerBtn4.setEnabled(true);
                    }
                },WAIT_TIME);


                //quizCount++;
                //showNextQuiz();

            }

        }else {

            showToast(view);

            //showToast();

            //Toast.makeText(this, "Wrong.",Toast.LENGTH_SHORT).show();


            //answerBtn1.setBackgroundColor(getResources().getColor(R.color.colorRed));
            //answerBtn2.setBackgroundColor(getResources().getColor(R.color.colorRed));
            //answerBtn3.setBackgroundColor(getResources().getColor(R.color.colorRed));
            //answerBtn4.setBackgroundColor(getResources().getColor(R.color.colorRed));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT",rightAnswerCount);
                    startActivity(intent);
                    finish();

                }
            },WAIT_TIME);

            //wrong

            alert = "Wrong";

           // Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            //intent.putExtra("RIGHT_ANSWER_COUNT",rightAnswerCount);
            //startActivity(intent);

        }

        //create dailog box

       /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle(alert);
       builder.setMessage("Answer"+rigthAnswer);
       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               if (quizCount == QUIZ_COUNT){
                   //show result

                   Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
                   intent.putExtra("RIGHT_ANSWER_COUNT",rightAnswerCount);
                   startActivity(intent);

               }else {

                   quizCount++;
                   showNextQuiz();

               }

           }
       });

      builder.setCancelable(false);
       builder.show();*/




    }

   /* public void showToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_root));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        toast.show();

    }*/

    public void showToast(View v) {
        StyleableToast.makeText(this, "Wrong! Try again!", R.style.exampleToast).show();
    }

    public void showToastOk(View v) {
        StyleableToast.makeText(this, "Correct!", R.style.exampleToast).show();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    /*@Override
    protected void onSaveInstanceState( Bundle outState) {
        outState.putString("game_text",question.getText().toString());
        outState.putString("answer1",answerBtn1.getText().toString());
        outState.putString("answer2",answerBtn2.getText().toString());
        outState.putString("answer3",answerBtn3.getText().toString());
        outState.putString("answer4",answerBtn4.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        question.setText(savedInstanceState.getString("game_text"));
        answerBtn1.setText(savedInstanceState.getString("answer1"));
        answerBtn2.setText(savedInstanceState.getString("answer2"));
        answerBtn3.setText(savedInstanceState.getString("answer3"));
        answerBtn4.setText(savedInstanceState.getString("answer4"));
    }*/
}
