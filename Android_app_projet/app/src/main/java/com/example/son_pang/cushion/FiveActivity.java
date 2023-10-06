package com.example.son_pang.cushion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class FiveActivity extends AppCompatActivity{
    private Context mContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        mContext = this;
        Button but1 =(Button)findViewById(R.id.but1);
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button but11 =(Button)findViewById(R.id.but11);
        but11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"设置成功",Toast.LENGTH_SHORT).show();
            }
        });

        Switch k1=(Switch)findViewById(R.id.switch1);
        k1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"设置成功",Toast.LENGTH_SHORT).show();
            }
        });
        Switch k2=(Switch)findViewById(R.id.switch2);
        k2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"设置成功",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
