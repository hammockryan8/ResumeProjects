package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess.piece.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameList extends AppCompatActivity {


    ListView gameList;
    static ChessGame chessGame;
    RecordedGames rg;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_list);

        rg = MainActivity.rg;

        gameList = (ListView) findViewById(R.id.game_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.textview, getStringList(rg));

        gameList.setAdapter(adapter);
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToMatch(position);
            }
        });
    }

    public void goBack(View view){
        Button clicked = (Button)view;
        Intent chess_intent=new Intent(this, MainActivity.class);
        startActivity(chess_intent);
    }

    public void goToMatch(int index){
        Bundle bundle = new Bundle();
        bundle.putInt("Match", index);
        Intent intent = new Intent(this, WatchGame.class);
        chessGame = rg.rg.get(index);
        startActivity(intent);
    }

    public void dateSort(View view){
        Collections.sort(rg.rg, Comparator.comparing(cg -> cg.date));
        update();
    }

    public void titleSort(View view){
        Collections.sort(rg.rg, Comparator.comparing(cg -> cg.name));
        update();
    }

    public void update() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.textview, getStringList(rg));
        gameList.setAdapter(adapter);
    }

    public ArrayList<String> getStringList(RecordedGames rg) {
        ArrayList<String> list = new ArrayList<>();
        for (ChessGame cg : rg.rg) {
            list.add(cg.name + " [" + cg.date.getTime() + "]");
        }
        return list;
    }

}
