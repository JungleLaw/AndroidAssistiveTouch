package com.law.aat.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.law.aat.R;

/**
 * Created by Law on 2016/2/12.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private static final int BRIGHTNESS_LOW = 10;
    private static final int BRIGHTNESS_MID = 127;
    private static final int BRIGHTNESS_HIGH = 255;

    private Context mContext;
    //定义WifiManager对象
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;
    private BluetoothAdapter mBluetoothAdapter;
    private Button mWifiButton, mNetWorkButton, mBrightnessButton, mBlueToothButton;

    public MainFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mWifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container);
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    private void initView(View view) {
        mWifiButton = (Button) view.findViewById(R.id.btn_wifi);
        mNetWorkButton = (Button) view.findViewById(R.id.btn_net);
        mBrightnessButton = (Button) view.findViewById(R.id.btn_brightness);
        mBlueToothButton = (Button) view.findViewById(R.id.btn_bluetooth);
    }

    private void initListener() {
        mWifiButton.setOnClickListener(this);
        mNetWorkButton.setOnClickListener(this);
        mBrightnessButton.setOnClickListener(this);
        mBlueToothButton.setOnClickListener(this);
    }

    private void initData() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiButton.setText("wifi validate");
        } else {
            mWifiButton.setText("wifi invalidate");
        }
        switch (mBluetoothAdapter.getState()) {
            case BluetoothAdapter.STATE_ON:
                mBlueToothButton.setText("BlueTooth invalidate");
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                mBlueToothButton.setText("BlueTooth invalidate");
                break;
            case BluetoothAdapter.STATE_OFF:
                mBlueToothButton.setText("BlueTooth validate");
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                mBlueToothButton.setText("BlueTooth validate");
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_wifi:
                if (mWifiManager.isWifiEnabled()) {
                    mWifiManager.setWifiEnabled(false);
                    mWifiButton.setText("wifi invalidate");
                } else {
                    mWifiManager.setWifiEnabled(true);
                    mWifiButton.setText("wifi validate");
                }
                break;
            case R.id.btn_net:
//                mConnectivityManager.
                break;
            case R.id.btn_brightness:
                try {
                    if (android.provider.Settings.System.getInt(mContext.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE) == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                        android.provider.Settings.System.putInt(mContext.getContentResolver(),
                                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                        android.provider.Settings.System.putInt(mContext.getContentResolver(),
                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                BRIGHTNESS_LOW);
                        mBrightnessButton.setText("BRIGHTNESS_LOW");
                    } else {
                        int brightness = android.provider.Settings.System.getInt(mContext.getContentResolver(),
                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                255);
                        switch (brightness) {
                            case BRIGHTNESS_LOW:
                                //保存为系统亮度方法1
                                android.provider.Settings.System.putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                        BRIGHTNESS_MID);
                                mBrightnessButton.setText("BRIGHTNESS_MID");
                                break;
                            case BRIGHTNESS_MID:
                                android.provider.Settings.System.putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                        BRIGHTNESS_HIGH);
                                mBrightnessButton.setText("BRIGHTNESS_HIGH");
                                break;
                            case BRIGHTNESS_HIGH:
                                android.provider.Settings.System.putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                        Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                                mBrightnessButton.setText("SCREEN_BRIGHTNESS_MODE_AUTOMATIC");
                                break;
                        }
                    }
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_bluetooth:
                switch (mBluetoothAdapter.getState()) {
                    case BluetoothAdapter.STATE_ON:
                        mBluetoothAdapter.disable();
                        mBlueToothButton.setText("BlueTooth invalidate");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        mBluetoothAdapter.disable();
                        mBlueToothButton.setText("BlueTooth invalidate");
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        mBluetoothAdapter.enable();
                        mBlueToothButton.setText("BlueTooth validate");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        mBluetoothAdapter.enable();
                        mBlueToothButton.setText("BlueTooth validate");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
