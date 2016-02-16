package com.law.aat.fragments;

import android.annotation.TargetApi;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.law.aat.LockSceenReceiver;
import com.law.aat.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Law on 2016/2/12.
 */
public class MainFragment extends Fragment implements View.OnClickListener {
    private static final int BRIGHTNESS_LOW = 10;
    private static final int BRIGHTNESS_MID = 127;
    private static final int BRIGHTNESS_HIGH = 255;

    private Context mContext;
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;
    private PowerManager mPowerManager;
    private DevicePolicyManager mDevicePolicyManager;
    private BluetoothAdapter mBluetoothAdapter;
    private Button mWifiButton, mNetWorkButton, mBrightnessButton,
            mBlueToothButton, mRotationButton, mAirPlaneModeButton, mGpsButton,
            mLockSceenButton, mTestButton, mGoHomeButton, mDialButton,
            mEmailButton;

    public MainFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext().getApplicationContext();
        mWifiManager = (WifiManager) getActivity().getSystemService(
                Context.WIFI_SERVICE);
        mConnectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mPowerManager = (PowerManager) mContext
                .getSystemService(Context.POWER_SERVICE);
        mDevicePolicyManager = (DevicePolicyManager) mContext
                .getSystemService(Context.DEVICE_POLICY_SERVICE);
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
        mTestButton = (Button) view.findViewById(R.id.btn_test);
        mWifiButton = (Button) view.findViewById(R.id.btn_wifi);
        mNetWorkButton = (Button) view.findViewById(R.id.btn_net);
        mBrightnessButton = (Button) view.findViewById(R.id.btn_brightness);
        mBlueToothButton = (Button) view.findViewById(R.id.btn_bluetooth);
        mRotationButton = (Button) view.findViewById(R.id.btn_rotation);
        mAirPlaneModeButton = (Button) view
                .findViewById(R.id.btn_airplane_mode);
        mGpsButton = (Button) view.findViewById(R.id.btn_gps);
        mLockSceenButton = (Button) view.findViewById(R.id.btn_lockscreen);
        mGoHomeButton = (Button) view.findViewById(R.id.btn_home);
        mDialButton = (Button) view.findViewById(R.id.btn_dial);
        mEmailButton = (Button) view.findViewById(R.id.btn_email);
    }

    private void initListener() {
        mTestButton.setOnClickListener(this);
        mWifiButton.setOnClickListener(this);
        mNetWorkButton.setOnClickListener(this);
        mBrightnessButton.setOnClickListener(this);
        mBlueToothButton.setOnClickListener(this);
        mRotationButton.setOnClickListener(this);
        mAirPlaneModeButton.setOnClickListener(this);
        mGpsButton.setOnClickListener(this);
        mLockSceenButton.setOnClickListener(this);
        mGoHomeButton.setOnClickListener(this);
        mDialButton.setOnClickListener(this);
        mEmailButton.setOnClickListener(this);
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

        if (getMobileDataStatus()) {
            mNetWorkButton.setText("net invalidate");
        } else {
            mNetWorkButton.setText("net validate");
        }

        if (getGpsStatus()) {
            mGpsButton.setText("GPS OFF");
        } else {
            mGpsButton.setText("GPS ON");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                test();
                break;
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
                // mConnectivityManager.
                if (getMobileDataStatus()) {
                    setMobileDataStatus(false);
                    mNetWorkButton.setText("net invalidate");
                } else {
                    setMobileDataStatus(true);
                    mNetWorkButton.setText("net validate");
                }
                break;
            case R.id.btn_brightness:
                try {
                    if (android.provider.Settings.System
                            .getInt(mContext.getContentResolver(),
                                    android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE) == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                        android.provider.Settings.System
                                .putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                        android.provider.Settings.System.putInt(
                                mContext.getContentResolver(),
                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                BRIGHTNESS_LOW);
                        mBrightnessButton.setText("BRIGHTNESS_LOW");
                    } else {
                        int brightness = android.provider.Settings.System.getInt(
                                mContext.getContentResolver(),
                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                255);
                        switch (brightness) {
                            case BRIGHTNESS_LOW:
                                // 保存为系统亮度方法1
                                android.provider.Settings.System
                                        .putInt(mContext.getContentResolver(),
                                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                                BRIGHTNESS_MID);
                                mBrightnessButton.setText("BRIGHTNESS_MID");
                                break;
                            case BRIGHTNESS_MID:
                                android.provider.Settings.System
                                        .putInt(mContext.getContentResolver(),
                                                android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                                BRIGHTNESS_HIGH);
                                mBrightnessButton.setText("BRIGHTNESS_HIGH");
                                break;
                            case BRIGHTNESS_HIGH:
                                android.provider.Settings.System
                                        .putInt(mContext.getContentResolver(),
                                                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                                mBrightnessButton
                                        .setText("SCREEN_BRIGHTNESS_MODE_AUTOMATIC");
                                break;
                            default:
                                android.provider.Settings.System
                                        .putInt(mContext.getContentResolver(),
                                                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                                mBrightnessButton
                                        .setText("SCREEN_BRIGHTNESS_MODE_AUTOMATIC");
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
            case R.id.btn_rotation:
                int screenRotation = android.provider.Settings.System.getInt(
                        mContext.getContentResolver(),
                        android.provider.Settings.System.ACCELEROMETER_ROTATION, 0);
                // 得到uri
                Uri uri = android.provider.Settings.System
                        .getUriFor(android.provider.Settings.System.ACCELEROMETER_ROTATION);
                switch (screenRotation) {
                    case 0:

                        // 沟通设置status的值改变屏幕旋转设置
                        android.provider.Settings.System
                                .putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.ACCELEROMETER_ROTATION,
                                        1);
                        break;
                    case 1:
                        // 沟通设置status的值改变屏幕旋转设置
                        android.provider.Settings.System
                                .putInt(mContext.getContentResolver(),
                                        android.provider.Settings.System.ACCELEROMETER_ROTATION,
                                        0);
                        break;
                    default:
                        break;
                }
                // 通知改变
                mContext.getContentResolver().notifyChange(uri, null);
                break;
            case R.id.btn_airplane_mode:
                try {
                    if (getAirplaneModeStatus()) {
                        setAirplaneMode(mContext, false);
                    } else {
                        setAirplaneMode(mContext, true);
                    }
                } catch (java.lang.SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Intent intent = new Intent(
                            android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                    startActivity(intent);
                }
                break;
            case R.id.btn_gps:
                if (getGpsStatus()) {
                    mGpsButton.setText("GPS OFF");
                } else {
                    mGpsButton.setText("GPS ON");
                }
                break;
            case R.id.btn_lockscreen:
                lockScreen();
                break;
            case R.id.btn_home:
                goHome();
                break;
            case R.id.btn_dial:
                goDial();
                break;
            case R.id.btn_email:
                gotoEmail();
                break;
            default:
                break;
        }
    }

    private void gotoEmail() {
        // TODO Auto-generated method stub
        Intent it = new Intent();
        it.setAction(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_APP_EMAIL);
        startActivity(it);
    }

    private void test() {
        // TODO Auto-generated method stub
        Intent it = new Intent();
        it.setAction(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(it);
    }

    // 获取飞行模式关闭或开启状态
    private boolean getAirplaneModeStatus() {
        boolean status = Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false;
        return status;
    }

    // 开启或关闭飞行模式
    private void setAirplaneMode(Context context, boolean enable) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, enable ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enable);
        context.sendBroadcast(intent);
    }

    // 获取移动数据开关状态
    private boolean getMobileDataStatus() {
        String methodName = "getMobileDataEnabled";
        Class cmClass = mConnectivityManager.getClass();
        Boolean isOpen = null;

        try {
            Method method = cmClass.getMethod(methodName, null);

            isOpen = (Boolean) method.invoke(mConnectivityManager, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOpen;
    }

    // 通过反射实现开启或关闭移动数据
    private void setMobileDataStatus(boolean enabled) {
        // Class<?> conMgrClass = Class.forName(mConnectivityManager
        // .getClass().getName());
        // // 得到ConnectivityManager类的成员变量mService（ConnectivityService类型）
        // Field iConMgrField = conMgrClass.getDeclaredField("mService");
        // iConMgrField.setAccessible(true);
        // // mService成员初始化
        // Object iConMgr = iConMgrField.get(mConnectivityManager);
        // // 得到mService对应的Class对象
        // Class<?> iConMgrClass =
        // Class.forName(iConMgr.getClass().getName());
        // /*
        // *
        // 得到mService的setMobileDataEnabled(该方法在android源码的ConnectivityService类中实现
        // * )， 该方法的参数为布尔型，所以第二个参数为Boolean.TYPE
        // */
        // Method setMobileDataEnabledMethod =
        // iConMgrClass.getDeclaredMethod(
        // "setMobileDataEnabled", Boolean.TYPE);
        // setMobileDataEnabledMethod.setAccessible(true);
        // /*
        // * 调用ConnectivityManager的setMobileDataEnabled方法（方法是隐藏的），
        // * 实际上该方法的实现是在ConnectivityService(系统服务实现类)中的
        // */
        // setMobileDataEnabledMethod.invoke(iConMgr, enabled);

        if (Build.VERSION.SDK_INT <= 20) {
            final ConnectivityManager conman = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            try {
                ConnectivityManager connectivityManager =

                        (ConnectivityManager) mContext
                                .getSystemService(Context.CONNECTIVITY_SERVICE);

                Method setMobileDataEnabled;

                setMobileDataEnabled = connectivityManager.getClass()
                        .getDeclaredMethod("setMobileDataEnabled",
                                boolean.class);

                setMobileDataEnabled.invoke(connectivityManager, enabled);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.v("TAG",
                        "Error setting"
                                + ((InvocationTargetException) e)
                                .getTargetException() + conman);
            }
        } else {
            TelephonyManager telephonyService = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method setMobileDataEnabledMethod = telephonyService.getClass()
                        .getDeclaredMethod("setDataEnabled", boolean.class);
                if (null != setMobileDataEnabledMethod) {
                    setMobileDataEnabledMethod
                            .invoke(telephonyService, enabled);
                }
            } catch (Exception e) {
                Log.v("TAG",
                        "Error setting"
                                + ((InvocationTargetException) e)
                                .getTargetException()
                                + telephonyService);
            }
        }
    }

    // 获取Gps开启或关闭状态
    private boolean getGpsStatus() {
        boolean status = false;
        if (Build.VERSION.SDK_INT < 19) {
            // try {
            // status = Secure.isLocationProviderEnabled(
            // mContext.getContentResolver(),
            // Secure.LOCATION_PROVIDERS_ALLOWED);
            // } catch (Exception e) {
            // Log.v("TAG",
            // "Error setting"
            // + ((InvocationTargetException) e)
            // .getTargetException() + " GpsStatus");
            // }
            try {
                Class secureClass = Class
                        .forName("android.provider.Settings$Secure");
                Method isMethod = secureClass
                        .getDeclaredMethod("isLocationProviderEnabled");
                status = (Boolean) isMethod.invoke(secureClass,
                        mContext.getContentResolver());
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                status = Settings.Secure.getInt(mContext.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED) == Settings.Secure.LOCATION_MODE_OFF ? false
                        : true;
            } catch (Settings.SettingNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                Log.v("TAG",
                        "Error setting"
                                + ((InvocationTargetException) e)
                                .getTargetException() + " GpsStatus");
            }
        }
        return status;
    }

    // 打开或关闭Gps
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setGpsStatus(boolean enabled) {
        if (Build.VERSION.SDK_INT < 19) {
            Settings.Secure.setLocationProviderEnabled(mContext.getContentResolver(),
                    LocationManager.GPS_PROVIDER, enabled);
        } else {
            Settings.Secure.putInt(mContext.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_HIGH_ACCURACY);
        }
    }

    private void lockScreen() {
        // 强制手机进入锁屏，这时候手机会灭屏，点亮后是处于锁屏状态
        // mPowerManager.goToSleep(SystemClock.uptimeMillis());
        // mDevicePolicyManager.lockNow();
        // 申请权限
        ComponentName componentName = new ComponentName(mContext,
                LockSceenReceiver.class);
        // 判断该组件是否有系统管理员的权限
        boolean isAdminActive = mDevicePolicyManager
                .isAdminActive(componentName);
        if (isAdminActive) {

            mDevicePolicyManager.lockNow(); // 锁屏

            // devicePolicyManager.resetPassword("123", 0); // 设置锁屏密码
            // devicePolicyManager.wipeData(0); 恢复出厂设置 (建议大家不要在真机上测试) 模拟器不支持该操作

        } else {
            Intent intent = new Intent();
            // 指定动作名称
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            // 指定给哪个组件授权
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    componentName);
            startActivity(intent);
        }
    }

    private void goHome() {
        Intent it = new Intent();
        it.setAction(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        startActivity(it);
    }

    private void goDial() {
        Intent it = new Intent(Intent.ACTION_DIAL);
        startActivity(it);
    }
}
