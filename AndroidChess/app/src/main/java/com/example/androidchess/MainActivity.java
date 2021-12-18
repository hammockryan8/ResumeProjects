package com.example.androidchess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static RecordedGames rg;

    public MainActivity() throws IOException, ClassNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (rg == null) rg = new RecordedGames();
    }

    public void switchToGameScreen(View view){
        Intent chess_intent=new Intent(this, ActualGame.class);
        startActivity(chess_intent);
    }

    public void switchToRecord(View view){
        Intent record_intent = new Intent(this, GameList.class);
        startActivity(record_intent);
    }

}