package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hirorock1103.template_01.Anken.Anken;
import com.example.hirorock1103.template_01.DB.AnkenManager;
import com.example.hirorock1103.template_01.Anken.Anken;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;
    private Button bt_5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bt_2 = findViewById(R.id.bt_2);
        bt_2.setText("Sample");
        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        bt_3 = findViewById(R.id.bt_3);
        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForthActivity.class);
                startActivity(intent);
            }
        });

        bt_4 = findViewById(R.id.bt_4);
        bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainDetailActivity.class);
                startActivity(intent);
            }
        });

        bt_5 = findViewById(R.id.bt_5);
        bt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainAnkenListActivity.class);
                startActivity(intent);
            }
        });




        AnkenManager manager = new AnkenManager(this);

        String[] ankens = {"ハルエネ","網元工業","スマ茂庭"};

        Random rand = new Random();
        int i = rand.nextInt(3);
        Anken anken = new Anken();
        anken.setAnkenName(ankens[i]);
        anken.setEndDate("2019/03/05");
        anken.setAnkenType(1);
        manager.addAnken(anken);

        List<Anken> list = manager.getList();

        for (Anken m : list){
            Log.i("INFO","m:" + m.getAnkenName());
        }


    }
}
