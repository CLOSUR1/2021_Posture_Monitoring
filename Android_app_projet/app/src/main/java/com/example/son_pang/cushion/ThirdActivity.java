package com.example.son_pang.cushion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

public class ThirdActivity extends Activity implements View.OnClickListener{
    private final static String TAG = "ThirdActivity";
    //设置绑定的蓝牙名称JDY-31-SPPMLT-BT05   ESP32test
    public static final String BLUETOOTH_NAME ="JDY-31-SPP";
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private StringBuilder mstringbuilder;
    private AlertDialog.Builder alert1,alert2,alert3,alert4,alert5,alert6,alert7;
    private Button mTest1;
    private int panduan=0;
    private char clean;
    //private Button mTest2;
    private TextView mBtConnectState;
    private TextView mTvChat;
    private ProgressDialog mProgressDialog;
    private BluetoothChatUtil mBlthChatUtil;
    private Button delete;
    private String readTxt="";

    private Handler mHandler;
    public ThirdActivity() {
        mHandler = new Handler(){
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case BluetoothChatUtil.STATE_CONNECTED:
                        String deviceName = msg.getData().getString(BluetoothChatUtil.DEVICE_NAME);
                        mBtConnectState.setText("已成功连接到设备" + deviceName);
                        if(mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                        break;
                    case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
                        if(mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                        if(mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                        mBtConnectState.setText("与设备断开连接");
                        break;
                    case BluetoothChatUtil.MESSAGE_READ:{
                        byte[] buf = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);
                        String str = new String(buf,0,buf.length);
                     //   String str1=new String(buf,1,buf.length);
                        Log.w(TAG,str);
                        if(str.startsWith("#")){
                          // Toast.makeText(getApplicationContext(), "接受成功" +str.replace("#",""), Toast.LENGTH_SHORT).show();
                            mTvChat.setText(str.replace("#",""));
                            writeToApp(str.replace("#",""));
                            Toast.makeText(getApplicationContext(), "接受成功" +str.replace("#",""), Toast.LENGTH_SHORT).show();

                        }
                        if ("0".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert1.show();
                            alert1.create();
                        }
                        else if ("1".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert2.show();
                            alert2.create();
                        }
                        else if ("2".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert3.show();
                            alert3.create();
                        }
                        else if ("3".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert4.show();
                            alert4.create();
                        }
                        else if ("4".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert5.show();
                            alert5.create();
                        }
                        else if ("5".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert6.show();
                            alert6.create();
                        }
                        else if ("6".equals(str))
                        {
                            mTvChat.setText(" ");
                            alert7.show();
                            alert7.create();
                        }
                        else if ("7".equals(str))
                        {
                            mTvChat.setText(" ");
                            mTvChat.setText(mTvChat.getText().toString()+"请等待");
                        }
                        //mTvChat.setText(mTvChat.getText().toString()+str);
                        //mTvChat.setText(mTvChat.getText().toString()+"\n"+str);  会自动换行
                        break;
                    }
                    case BluetoothChatUtil.MESSAGE_WRITE:{
                        byte[] buf = (byte[]) msg.obj;
                        String str = new String(buf,0,buf.length);
                        //    Toast.makeText(getApplicationContext(), "发送成功" + str, Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mstringbuilder=new StringBuilder();
        mContext = this;
        initView();
        initBluetooth();
        initDialog();
        mBlthChatUtil = BluetoothChatUtil.getInstance(mContext);
        mBlthChatUtil.registerHandler(mHandler);
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

    private void initView() {
        mTest1 = findViewById(R.id.button_6);
       // mTest2 = findViewById(R.id.button_8);
        mBtConnectState = findViewById(R.id.tv_connect_state);
        mTvChat = findViewById(R.id.tv_chat);
        delete=(Button)findViewById(R.id.clean);
        mTest1.setOnClickListener(this);
       // mTest2.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(this);
    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //注册广播接收者，监听扫描到的蓝牙设备
        IntentFilter filter = new IntentFilter();
        //发现设备
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver, filter);
    }
    private void initDialog() {
        alert1 = new AlertDialog.Builder(this);
        alert1.setTitle("温馨提示").setMessage("请保持正确腰姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //321初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
        alert2 = new AlertDialog.Builder(this);
        alert2.setTitle("温馨提示").setMessage("请保持左倾腰姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //321初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
        alert3 = new AlertDialog.Builder(this);
        alert3.setTitle("温馨提示").setMessage("请保持右倾腰姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //321初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
        alert4 = new AlertDialog.Builder(this);
        alert4.setTitle("温馨提示").setMessage("请保持弯腰腰姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //321初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
        alert5 = new AlertDialog.Builder(this);
        alert5.setTitle("温馨提示").setMessage("请保持弓背站姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //123初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
       alert6 = new AlertDialog.Builder(this);
        alert6.setTitle("温馨提示").setMessage("请保持正常站姿").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String b = String.valueOf(9); //123初始化
                        String messages = b;
                        mBlthChatUtil.write(messages.getBytes());
                    }
                });
        alert7 = new AlertDialog.Builder(this);
        alert7.setTitle("温馨提示").setMessage("初始化成功，请返回数据测量").setPositiveButton("确定",
                new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        }


    @Override
    protected void onResume() {
        super.onResume();
        if (mBlthChatUtil != null) {
            if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED){
                BluetoothDevice device = mBlthChatUtil.getConnectedDevice();
                if(null != device && null != device.getName()){
                    mBtConnectState.setText("已成功连接到设备" + device.getName());
                }else {
                    mBtConnectState.setText("已成功连接到设备");
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mBlthChatUtil = null;
        unregisterReceiver(mBluetoothReceiver);
    }

    @Override
    public void onClick(View arg0) {
        switch(arg0.getId()){
            case R.id.button_6://数据1
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    //测量数据1代码
                    String a = String.valueOf(5); //123初始化
                    String messages = a;
                    mBlthChatUtil.write(messages.getBytes());

                    mTvChat.setText("");
                }else {
                    Toast.makeText(mContext,"蓝牙未连接",Toast.LENGTH_SHORT).show();
                }
                break;
           /* case R.id.button_8://数据1
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    //测量数据1代码
                    String b = String.valueOf(321); //123初始化
                    String messages = b;
                    mBlthChatUtil.write(messages.getBytes());
                    //mTvChat.setText("");
                }else {
                    Toast.makeText(mContext,"蓝牙未连接",Toast.LENGTH_SHORT).show();
                }
                break;*/

            default:
                break;
        }
    }



    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG,"mBluetoothReceiver action ="+action);
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(scanDevice == null || scanDevice.getName() == null) return;
                Log.d(TAG, "name="+scanDevice.getName()+"address="+scanDevice.getAddress());
                //蓝牙设备名称
                String name = scanDevice.getName();
                if(name != null && name.equals(BLUETOOTH_NAME)){
                    mBluetoothAdapter.cancelDiscovery(); //取消扫描
                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));
                    mBlthChatUtil.connect(scanDevice);
                }
            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
            }
        }
    };
    @SuppressWarnings("unused")
    private void getBtDeviceInfo(){
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG,"bluetooth name ="+name+" address ="+address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size ="+devices.size());
        for(BluetoothDevice bonddevice:devices){
            Log.d(TAG, "bonded device name ="+bonddevice.getName()+
                    " address"+bonddevice.getAddress());
        }
    }

    public String readToApp(String fileName) throws IOException{

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
}

