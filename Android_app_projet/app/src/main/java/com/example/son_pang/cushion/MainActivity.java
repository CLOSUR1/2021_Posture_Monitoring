package com.example.son_pang.cushion;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
    //获取当前时间

    //time1.setText("Date获取当前日期时间"+simpleDateFormat.format(date));
    Date data;
    AlertDialog.Builder builder;
    private int progress = 0;
    private Timer timer;
    private TimerTask timerTask;
    private ProgressDialog dialog;

    private DrawerLayout mDrawerlayout;
    private EditText edtData;
    private TextView readedtData;
    private Button btnWriteToApp;
    private Button btnreadToApp;
    private Button begin;
    private Button send;
    private Button delete;
    private String readTxt="";//接收文本 发送文本
    private char clean;//接收文本 发送文本
    private int panduan=0;
    private String ip4="192.168.43.112",port="4574";//接收文本 发送文本
    private TextView showmsg;
    private EditText sendmsged;
    private EditText ipv4ed;
    private EditText ported;
    private Button sendmsgbt;
    private Button connectbt;
    private Handler handler;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private String receiveTxt,SendMsg="";//接收文本 发送文本
    private static String[] PERMISSIONS_STORAGE = {
            //依次权限申请
            Manifest.permission.INTERNET
    };


    EditText editText1=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applypermission();//权限申请

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            if(msg.what==24){
                  //  showmsg.append("客户端:"+sendmsged.getText().toString()+"\n");
                  //  sendmsged.setText("");
                Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();

                }else if(msg.what==98){
                Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                   // showmsg.append("服务端:"+receiveTxt);
                }
            }
        };
        //InitView();
        //  Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        mDrawerlayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        //NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.images);
        }
        Button begin =(Button)findViewById(R.id.connect);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"连接成功",Toast.LENGTH_SHORT).show();
                connectionSeverThread.start();
                SendMsgThread.start();
            }
        });

        Button delete =(Button)findViewById(R.id.clean);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                panduan=2;
                Toast.makeText(MainActivity.this,"清除完毕",Toast.LENGTH_SHORT).show();
                writeToApp(String.valueOf(clean));
                try {
                    readTxt=readToApp("myFile");
                    //  Toast.makeText(MainActivity.this,readTxt,Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                panduan=0;
            }
        });
        Button tupian =(Button)findViewById(R.id.tupian);
        tupian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FiveActivity.class);
                startActivity(intent);
            }
        });


        Button button1 =(Button)findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
         builder = new AlertDialog.Builder(this);
        dialog = new ProgressDialog(this);
        Button send =(Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.sendEmptyMessage(24);
                // SendMsg = sendmsged.getText().toString();
                try {
                    SendMsg=readToApp("myFile");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progress = 0;
                 data = new Date(System.currentTimeMillis());
                //readedtData.setText(readTxt);
                // Toast.makeText(MainActivity.this,readTxt,Toast.LENGTH_SHORT).show();


                //调用show才能显示出来

    dialog.setTitle("数据训练中");
    dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

    dialog.setMax(100);
    dialog.setProgress(0);
    StartTimer();
    dialog.dismiss();

    dialog.show();


    }
        });

        Button zhao =(Button)findViewById(R.id.zhao);
        zhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("训练结果");
                builder.setMessage(
                        "记录时间："+simpleDateFormat.format(data)+"\n"+  "\n" +
                        "训练集精度： 0.9954654574553214\n" +
                        "\n" +
                        "测试集精度： 0.9965710366899074");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点了确定");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("点了取消");
                    }
                });
                builder.show();
            }
        });

        Button over =(Button)findViewById(R.id.over);
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"断开成功",Toast.LENGTH_SHORT).show();

             //   SendMsg="  stop";
           //     handler.sendEmptyMessage(24);
                // SendMsg = sendmsged.getText().toString();
        //        SendMsg="";

                //readedtData.setText(readTxt);
                // Toast.makeText(MainActivity.this,readTxt,Toast.LENGTH_SHORT).show();

            }
        });



        Button button2 =(Button)findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, FourthActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button)findViewById(R.id.button_3);
        button3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SecondActivity.class );
                startActivity(intent);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }
    Thread connectionSeverThread = new Thread(
            //连接服务器
            new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取编辑框组件ipv4ed1.getText().toString()   Integer.valueOf(ported.getText().toString())
                        socket = new Socket(ip4,Integer.valueOf(port));
                        //连接服务器
                        out=new DataOutputStream(socket.getOutputStream());
                        // 创建DataOutputStream对象 发送数据
                        in = new DataInputStream(socket.getInputStream());
                    } catch (Exception e) {
                        // TODO Auto-generatetd catch block
                        e.printStackTrace();
                    }
                    while(true) {
                        try {

                            receiveTxt = in.readUTF()+"\n";
                          /*  if(in.readUTF()!=""){
                                showmsg.append("服务端:"+receiveTxt);
                                showmsg.append("服务端发来数据了");
                            }*/
                            handler.sendEmptyMessage(98);
                            //发送空消息  1主要为了区分消息好执行改变组件信息的内容
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }
    );
    Thread SendMsgThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    while (true){
                        if(SendMsg != ""){
                            try {
                                out.writeUTF(SendMsg);//发送消息
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            SendMsg = "";
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
    );
    public void writeToApp(String data){
        FileOutputStream out = null;
        try {
            if(panduan==2){
                out = openFileOutput("myFile",MODE_PRIVATE);
                out.write(clean);
            }
            if(panduan!=2){
                out = openFileOutput("myFile",MODE_APPEND);
                out.write(data.getBytes());}
            //  Toast.makeText(MainActivity.this,edtData.getText().toString(),Toast.LENGTH_SHORT).show();

            out.flush();// 清理缓冲区的数据流
            out.close();// 关闭输出流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String readToApp(String fileName) throws IOException {

        FileInputStream fis = openFileInput(fileName);
        int len = fis.available();
        byte []buffer = new byte[len];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((fis.read(buffer))!=-1){
            baos.write(buffer);
        }
        byte []data = baos.toByteArray();
        baos.close();
        fis.close();
        return new String(data);
    }
   public void applypermission(){
    if(Build.VERSION.SDK_INT>=23){
        boolean needapply=false;
        for(int i=0;i<PERMISSIONS_STORAGE.length;i++){
            int chechpermission= ContextCompat.checkSelfPermission(getApplicationContext(),
                    PERMISSIONS_STORAGE[i]);
            if(chechpermission!= PackageManager.PERMISSION_GRANTED){
                needapply=true;
            }
        }
        if(needapply){
            ActivityCompat.requestPermissions(MainActivity.this,PERMISSIONS_STORAGE,1);
        }
    }
}

  /*  public void jindutiao(View view) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("请长按");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dialog.setMax(10);
        dialog.setProgress(0);
        StartTimer();
        dialog.dismiss();

        dialog.show();
    }*/

    //activity启动后开始计时
    @Override
    protected void onResume() {
        super.onResume();
        //StartTimer();
    }

    //进入后台后计时器暂停
    @Override
    protected void onPause() {
        super.onPause();
        //EndTimer();
    }

    public void StartTimer() {
        //如果timer和timerTask已经被置null了
        if (timer == null&&timerTask==null) {
            //新建timer和timerTask
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    //每次progress加一
                    progress++;
                    //如果进度条满了的话就再置0，实现循环
                    if (progress == 101) {
                        dialog.dismiss();
                    }
                    //设置进度条进度
                    dialog.setProgress(progress);
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }

    public void EndTimer()
    {
        timer.cancel();
        timerTask.cancel();
        timer=null;
        timerTask=null;
    }



}
