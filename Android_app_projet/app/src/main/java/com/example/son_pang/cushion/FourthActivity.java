package com.example.son_pang.cushion;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class FourthActivity extends AppCompatActivity  implements View.OnClickListener {
    private final static String TAG = "FourthActivity";
    //设置绑定的蓝牙名称JDY-31-SPP  MLT-BT05ESP32testMLT-BT05
    public static final String BLUETOOTH_NAME = "JDY-31-SPP";
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private Button mTest1;
    private TextView mBtConnectState;
    private TextView mTvChat;
    private ProgressDialog mProgressDialog;
    private BluetoothChatUtil mBlthChatUtil;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatUtil.STATE_CONNECTED:
                    String deviceName = msg.getData().getString(BluetoothChatUtil.DEVICE_NAME);
                    mBtConnectState.setText("已成功连接到设备" + deviceName);
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    break;
                case BluetoothChatUtil.STATAE_CONNECT_FAILURE:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothChatUtil.MESSAGE_DISCONNECTED:
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    mBtConnectState.setText("与设备断开连接");
                    break;
                case BluetoothChatUtil.MESSAGE_READ: {
                    byte[] buf = msg.getData().getByteArray(BluetoothChatUtil.READ_MSG);
                    String str = new String(buf, 0, buf.length);
                    Log.w(TAG, str);
                    if ("q".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "弯腰坐势");
                    } else if ("r".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "右倾腰姿");
                    } else if ("l".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "左倾腰姿");
                    } else if ("n".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "坐姿正确");
                    } else if ("z".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "弓背站姿");
                    } else if ("y".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "站姿正确");
                    } else if ("g".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "坐姿不正确");
                    } else if ("h".equals(str)) {
                        mTvChat.setText("");
                        mTvChat.setText(mTvChat.getText().toString() + "您已久坐，请站立休息");
                    }
                    else if ("o".equals(str)) {
                        putong();
                    }
                    //  Toast.makeText(getApplicationContext(), "读成功" + str, Toast.LENGTH_SHORT).show();
                    //mTvChat.setText(mTvChat.getText().toString()+str);
                    break;
                }
                case BluetoothChatUtil.MESSAGE_WRITE: {
                    byte[] buf = (byte[]) msg.obj;
                    String str = new String(buf, 0, buf.length);
                    //     Toast.makeText(getApplicationContext(), "发送成功" + str, Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        mContext = this;
        initView();
        initBluetooth();
        mBlthChatUtil = BluetoothChatUtil.getInstance(mContext);
        mBlthChatUtil.registerHandler(mHandler);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        mTest1 = findViewById(R.id.button_7);
        mBtConnectState = findViewById(R.id.tv_connect_state);
        mTvChat = findViewById(R.id.tv_chat);
        mTest1.setOnClickListener(this);
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


    @Override
    protected void onResume() {
        super.onResume();
        if (mBlthChatUtil != null) {
            if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                BluetoothDevice device = mBlthChatUtil.getConnectedDevice();
                if (null != device && null != device.getName()) {
                    mBtConnectState.setText("已成功连接到设备" + device.getName());
                } else {
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
        switch (arg0.getId()) {
            case R.id.button_7://数据2
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    //测量数据1代码
                    String b = String.valueOf(8);  //1234是开启
                    String messages = b;
                    mBlthChatUtil.write(messages.getBytes());
                } else {
                    Toast.makeText(mContext, "蓝牙未连接", Toast.LENGTH_SHORT).show();
                }
                break;
            /*
            case R.id.button_8://关
                if (mBlthChatUtil.getState() == BluetoothChatUtil.STATE_CONNECTED) {
                    Toast.makeText(getApplicationContext(),"关闭",Toast.LENGTH_SHORT);
                    String c = String.valueOf(2);  //2是关闭
                    String messages = c;
                    mBlthChatUtil.write(messages.getBytes());
                }else {
                    Toast.makeText(mContext,"蓝牙未连接",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FourthActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            */
            default:
                break;
        }
    }


    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "mBluetoothReceiver action =" + action);
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //获取蓝牙设备
                BluetoothDevice scanDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (scanDevice == null || scanDevice.getName() == null) return;
                Log.d(TAG, "name=" + scanDevice.getName() + "address=" + scanDevice.getAddress());
                //蓝牙设备名称
                String name = scanDevice.getName();
                if (name != null && name.equals(BLUETOOTH_NAME)) {
                    mBluetoothAdapter.cancelDiscovery(); //取消扫描
                    mProgressDialog.setTitle(getResources().getString(R.string.progress_connecting));
                    mBlthChatUtil.connect(scanDevice);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            }
        }
    };

    @SuppressWarnings("unused")
    private void getBtDeviceInfo() {
        //获取本机蓝牙名称
        String name = mBluetoothAdapter.getName();
        //获取本机蓝牙地址
        String address = mBluetoothAdapter.getAddress();
        Log.d(TAG, "bluetooth name =" + name + " address =" + address);
        //获取已配对蓝牙设备
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        Log.d(TAG, "bonded device size =" + devices.size());
        for (BluetoothDevice bonddevice : devices) {
            Log.d(TAG, "bonded device name =" + bonddevice.getName() +
                    " address" + bonddevice.getAddress());
        }
    }

    public void putong() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告!!!!!");
        builder.setMessage("已长时间保持不良腰姿，请及时改正");
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
        //调用show才能显示出来
        builder.show();
    }
}
