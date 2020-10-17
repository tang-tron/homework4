package com.example.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizgame.model.WordItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class         GameActivity extends AppCompatActivity implements View.OnClickListener {
   private ImageView mQuestionImageView;
   private Button[] mButtons = new Button[4];

   private String mAnswerWord;
   private Random Random;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_game);

      mQuestionImageView = findViewById(R.id.question_image_view);
      mButtons[0] = findViewById(R.id.choice_1_button);
      mButtons[1] = findViewById(R.id.choice_2_button);
      mButtons[2] = findViewById(R.id.choice_3_button);
      mButtons[3] = findViewById(R.id.choice_4_button);

      mButtons[0].setOnClickListener(this);
      mButtons[1].setOnClickListener(this);
      mButtons[2].setOnClickListener(this);
      mButtons[3].setOnClickListener(this);

      Random = new Random();
      newQuiz();
   }

   private void newQuiz() {
      List<WordItem> mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));

      int answerIndex = Random.nextInt(mItemList.size());
      WordItem item = mItemList.get(answerIndex);
      mQuestionImageView.setImageResource(item.imageResId);

      mAnswerWord = item.word;
      int randomButton = Random.nextInt(4);
      mButtons[randomButton].setText(item.word);
      mItemList.remove(item);
      Collections.shuffle(mItemList);

      for (int i = 0; i < 4; i++) {
         if (i == randomButton) {
            continue;
         }
         mButtons[i].setText(mItemList.get(i).word);
      }
   }
   int count =0;
   int P=0;
   TextView C;
   @Override
   public void onClick(View view) {

      Button b = findViewById(view.getId());
      String buttonText = b.getText().toString();
      C= findViewById(R.id.C);

      if (buttonText.equals(mAnswerWord)) {
         //Toast.makeText(GameActivity.this, "ถูกต้องครับ", Toast.LENGTH_SHORT).show();
         count++;
         P++;
         C.setText(Integer.toString(P)+" คะแนน");
      } else {
         count++;

      }
      if(count==5){
         AlertDialog.Builder dialog = new AlertDialog.Builder(GameActivity.this);
         dialog.setTitle("สรุปผล");
         dialog.setMessage("คุณได้ "+P+" คะแนน\nต้องการเล่นเกมใหม่หรือไม่");
         dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               P=0;
               count=0;
               C.setText("0 คะแนน");
               newQuiz();
            }
         });
         dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               Intent intent = new Intent(GameActivity.this, MainActivity.class);
               startActivity(intent);
            }
         });
         dialog.show();
      }

      newQuiz();
   }
}
