package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidchess.Model.Piece;

public class GameList extends AppCompatActivity {


    private ListView gameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_list);
        gameList = (ListView)findViewById(R.id.game_list);
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getApplicationContext(), "You selected index " + String.valueOf(position) , Toast.LENGTH_SHORT).show();



                goToMatch(position);

            }});
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
        startActivity(intent);
    }

}
