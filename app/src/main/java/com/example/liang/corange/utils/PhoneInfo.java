package com.example.liang.corange.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.ActivityManager.MemoryInfo;

import com.example.liang.corange.R;
import com.example.liang.corange.ui.BaseActivity;

/**
 * @ClassName: PhoneInfo
 * @Description: 获取手机信息的工具类
 * @date 1.手机的IMEI 2.手机的制式类型，GSM OR CDMA 手机 /2.1手机的系统版本信息 3.手机网络国家编码
 * 4.手机网络运营商ID。 5.手机网络运营商名称 6.手机的数据链接类型 7.是否有可用数据链接 8.当前的数据链接类型 9.手机剩余内存
 * 10.手机总内存 11.手机CPU型号 12.手机名称 13.手机型号 14.手机设备制造商名称
 * <p>
 * 注意：需要如下权限 <uses-permission
 * android:name="android.permission.READ_PHONE_STATE"/> <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
 */

public class PhoneInfo extends BaseActivity {
    private static final String TAG = PhoneInfo.class.getSimpleName();
    private static final String FILE_MEMORY = "/proc/meminfo";
    private static final String FILE_CPU = "/proc/cpuinfo";
    public String mIMEI;
    public int mPhoneType;
    public int mSysVersion;
    public String mNetWorkCountryIso;
    public String mNetWorkOperator;
    public String mNetWorkOperatorName;
    public int mNetWorkType;
    public boolean mIsOnLine;
    public String mConnectTypeName;
    public long mFreeMem;
    public long mTotalMem;
    public String mCupInfo;
    public String mProductName;
    public String mModelName;
    public String mManufacturerName;
    public static Context context;

    /**
     * private constructor
     */
    private PhoneInfo() {

    }

    /**
     * get imei
     *
     * @return 手机的IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        // check if has the permission
        if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
                .checkPermission(Manifest.permission.READ_PHONE_STATE,
                        context.getPackageName())) {
            return manager.getDeviceId();
        } else {
            return null;
        }
    }

    /**
     * get phone type,like :GSM,CDMA,SIP,NONE
     *
     * @param context
     * @return 手机的制式类型，GSM OR CDMA 手机 1
     */
    public static int getPhoneType(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getPhoneType();
    }

    /**
     * get phone sys version
     *
     * @return 手机的系统版本信息. 9
     */
    public static int getSysVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Returns the ISO country code equivalent of the current registered
     * operator's MCC (Mobile Country Code).
     *
     * @param context
     * @return 手机网络国家编码 cn
     */
    public static String getNetWorkCountryIso(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkCountryIso();
    }

    /**
     * Returns the numeric name (MCC+MNC) of current registered operator.may not
     * work on CDMA phone
     *
     * @param context
     * @return 手机网络运营商ID。46001
     */
    public static String getNetWorkOperator(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkOperator();
    }

    /**
     * Returns the alphabetic name of current registered operator.may not work
     * on CDMA phone
     *
     * @param context
     * @return 手机网络运营商名称 china unicom
     */
    public static String getNetWorkOperatorName(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    /**
     * get type of current network
     *
     * @param context
     * @return 手机的数据链接类型 3
     */
    public static int getNetworkType(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Activity.TELEPHONY_SERVICE);

        return manager.getNetworkType();
    }

    /**
     * is webservice aviliable
     *
     * @param context
     * @return 是否有可用数据链接 true
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * get current data connection type name ,like ,Mobile/WIFI/OFFLINE
     *
     * @param context
     * @return 当前的数据链接类型 wifi
     */
    public static String getConnectTypeName(Context context) {
        if (!isOnline(context)) {
            return "OFFLINE";
        }
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {

            return info.getSubtypeName();
        } else {
            return "OFFLINE";
        }
    }

    /**
     * get free memory of phone, in M
     *
     * @param context
     * @return 手机剩余内存 1024M
     */
    public static long getFreeMem(Context context) {
        ActivityManager manager = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);

        MemoryInfo info = new MemoryInfo();
        manager.getMemoryInfo(info);
        long free = info.availMem / 1024 / 1024;

        return free;
    }

    /**
     * get total memory of phone , in M
     *
     * @param context
     * @return 手机总内存 2048M
     */
    public static long getTotalMem(Context context) {
        try {
            FileReader fr = new FileReader(FILE_MEMORY);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split("\\s+");
            Log.w(TAG, text);

            return Long.valueOf(array[1]) / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * get the cpu info
     *
     * @return 手机CPU型号 ARMv7 Processor
     */
    public static String getCpuInfo() {
        try {
            FileReader fr = new FileReader(FILE_CPU);
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
                Log.w(TAG, " .....  " + array[i]);
            }
            Log.w(TAG, text);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get product name of phone
     *
     * @return 手机名称 libra_mione_plus
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * get model of phone
     *
     * @return 手机型号 MI-ONE Plus
     */
    public static String getModelName() {

        return Build.MODEL;
    }

    /**
     * get Manufacturer Name of phone
     *
     * @return 手机设备制造商名称 xiaomi
     */
    public static String getManufacturerName() {
        return Build.MANUFACTURER;
    }

    // 以下测试用
    public static PhoneInfo getPhoneInfo(Context context) {
        PhoneInfo result = new PhoneInfo();

        result.mIMEI = getIMEI(context);

        result.mPhoneType = getPhoneType(context);

        result.mSysVersion = getSysVersion();

        result.mNetWorkCountryIso = getNetWorkCountryIso(context);

        result.mNetWorkOperator = getNetWorkOperator(context);

        result.mNetWorkOperatorName = getNetWorkOperatorName(context);

        result.mNetWorkType = getNetworkType(context);

        result.mIsOnLine = isOnline(context);

        result.mConnectTypeName = getConnectTypeName(context);

        result.mFreeMem = getFreeMem(context);

        result.mTotalMem = getTotalMem(context);

        result.mCupInfo = getCpuInfo();

        result.mProductName = getProductName();

        result.mModelName = getModelName();

        result.mManufacturerName = getManufacturerName();

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("IMEI : " + mIMEI + "\n");

        builder.append("mPhoneType : " + mPhoneType + "\n");

        builder.append("mSysVersion : " + mSysVersion + "\n");

        builder.append("mNetWorkCountryIso : " + mNetWorkCountryIso + "\n");

        builder.append("mNetWorkOperator : " + mNetWorkOperator + "\n");

        builder.append("mNetWorkOperatorName : " + mNetWorkOperatorName + "\n");

        builder.append("mNetWorkType : " + mNetWorkType + "\n");

        builder.append("mIsOnLine : " + mIsOnLine + "\n");

        builder.append("mConnectTypeName : " + mConnectTypeName + "\n");

        builder.append("mFreeMem : " + mFreeMem + "M\n");

        builder.append("mTotalMem : " + mTotalMem + "M\n");

        builder.append("mCupInfo : " + mCupInfo + "\n");

        builder.append("mProductName : " + mProductName + "\n");

        builder.append("mModelName : " + mModelName + "\n");

        builder.append("mManufacturerName : " + mManufacturerName + "\n");

        return builder.toString();

    }

    /**
     * 判断Network具体类型（联通移动wap，电信wap，其他net）
     */
    public static final String CTWAP = "ctwap";
    public static final String CTNET = "ctnet";
    public static final String CMWAP = "cmwap";
    public static final String CMNET = "cmnet";
    public static final String NET_3G = "3gnet";
    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    public static final String UNINET = "uninet";

    public static final int TYPE_CT_WAP = 5;
    public static final int TYPE_CT_NET = 6;
    public static final int TYPE_CT_WAP_2G = 7;
    public static final int TYPE_CT_NET_2G = 8;

    public static final int TYPE_CM_WAP = 9;
    public static final int TYPE_CM_NET = 10;
    public static final int TYPE_CM_WAP_2G = 11;
    public static final int TYPE_CM_NET_2G = 12;

    public static final int TYPE_CU_WAP = 13;
    public static final int TYPE_CU_NET = 14;
    public static final int TYPE_CU_WAP_2G = 15;
    public static final int TYPE_CU_NET_2G = 16;

    public static final int TYPE_OTHER = 17;

    public static Uri PREFERRED_APN_URI = Uri
            .parse("content://telephony/carriers/preferapn");

    /**
     * 没有网络
     */
    public static final int TYPE_NET_WORK_DISABLED = 0;

    /**
     * wifi网络
     */
    public static final int TYPE_WIFI = 4;

    /***
     *
     *
     *
     ***/
    public static String getNetworkName(Context context) {
        String NetworkName = "";
        int checkNetworkType = checkNetworkType(context);
        switch (checkNetworkType) {
            case TYPE_WIFI:
                Log.i("NetType", "================wifi");
                NetworkName = "WIFI";
                break;
            case TYPE_NET_WORK_DISABLED:
                Log.i("NetType", "================no network");
                NetworkName = "无网络";
                break;
            case TYPE_CT_WAP:
                Log.i("NetType", "================ctwap");
                NetworkName = "无网络";
                break;
            case TYPE_CT_WAP_2G:
                Log.i("NetType", "================ctwap_2g");
                break;
            case TYPE_CT_NET:
                Log.i("NetType", "================ctnet");
                break;
            case TYPE_CT_NET_2G:
                Log.i("NetType", "================ctnet_2g");
                NetworkName = "2G网络";
                break;
            case TYPE_CM_WAP:
                Log.i("NetType", "================cmwap");
                break;
            case TYPE_CM_WAP_2G:
                Log.i("NetType", "================cmwap_2g");
                break;
            case TYPE_CM_NET:
                Log.i("NetType", "================cmnet");
                break;
            case TYPE_CM_NET_2G:
                Log.i("NetType", "================cmnet_2g");
                break;
            case TYPE_CU_NET:
                Log.i("NetType", "================cunet");
                break;
            case TYPE_CU_NET_2G:
                Log.i("NetType", "================cunet_2g");
                break;
            case TYPE_CU_WAP:
                Log.i("NetType", "================cuwap");
                break;
            case TYPE_CU_WAP_2G:
                Log.i("NetType", "================cuwap_2g");
                break;
            case TYPE_OTHER:
                Log.i("NetType", "================other");
                break;
            default:
                break;
        }
        return NetworkName;

    }

    /***
     * 判断Network具体类型（联通移动wap，电信wap，其他net）
     */
    public static int checkNetworkType(Context mContext) {
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mobNetInfoActivity = connectivityManager
                    .getActiveNetworkInfo();
            if (mobNetInfoActivity == null || !mobNetInfoActivity.isAvailable()) {
                // 注意一：
                // NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
                // 但是有些电信机器，仍可以正常联网，
                // 所以当成net网络处理依然尝试连接网络。
                // （然后在socket中捕捉异常，进行二次判断与用户提示）。
                /** 自定义Tost */
                Toast toast = Toast.makeText(mContext, "当前无网络", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                ImageView imageView = new ImageView(mContext);// 创建视图对象
                imageView.setImageResource(R.drawable.not_network); // 设置图像
                LinearLayout toastView = (LinearLayout) toast.getView();// 获得toast的布局
                toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                toast.show();
                /** 自定义Tost结束 */
                return TYPE_NET_WORK_DISABLED;
            } else {
                // NetworkInfo不为null开始判断是网络类型
                int netType = mobNetInfoActivity.getType();

                if (netType == ConnectivityManager.TYPE_WIFI) {
                    // wifi net处理
                    /** 自定义Tost */
                    Toast toast = Toast.makeText(mContext, "当前是WIFI网络", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                    ImageView imageView = new ImageView(mContext);// 创建视图对象
                    imageView.setImageResource(R.drawable.wifi); // 设置图像
                    LinearLayout toastView = (LinearLayout) toast.getView();// 获得toast的布局
                    toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                    toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                    toast.show();
                    /** 自定义Tost结束 */
                    return TYPE_WIFI;
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    // 注意二：
                    // 判断是否电信wap:
                    // 不要通过getExtraInfo获取接入点名称来判断类型，
                    // 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
                    // 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
                    // 所以可以通过这个进行判断！

                    boolean is3G = isFastMobileNetwork(mContext);

                    final Cursor c = mContext.getContentResolver().query(
                            PREFERRED_APN_URI, null, null, null, null);
                    if (c != null) {
                        c.moveToFirst();
                        final String user = c.getString(c
                                .getColumnIndex("user"));
                        if (!TextUtils.isEmpty(user)) {
                            if (user.startsWith(CTWAP)) {
                                /** 自定义Tost */
                                Toast toast = Toast.makeText(mContext,
                                        "您的网络是电信CTWAP", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                                ImageView imageView = new ImageView(mContext);// 创建视图对象
                                imageView
                                        .setImageResource(R.drawable.china_telecom); // 设置图像
                                LinearLayout toastView = (LinearLayout) toast
                                        .getView();// 获得toast的布局
                                toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                                toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                                toast.show();
                                /** 自定义Tost结束 */
                                return is3G ? TYPE_CT_WAP : TYPE_CT_WAP_2G;
                            } else if (user.startsWith(CTNET)) {
                                /** 自定义Tost */
                                Toast toast = Toast.makeText(mContext,
                                        "您的网络是电信CTNET", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                                ImageView imageView = new ImageView(mContext);// 创建视图对象
                                imageView
                                        .setImageResource(R.drawable.china_telecom); // 设置图像
                                LinearLayout toastView = (LinearLayout) toast
                                        .getView();// 获得toast的布局
                                toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                                toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                                toast.show();
                                /** 自定义Tost结束 */
                                return is3G ? TYPE_CT_NET : TYPE_CT_NET_2G;
                            }
                        }
                    }
                    c.close();

                    // 注意三：
                    // 判断是移动联通wap:
                    // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
                    // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
                    // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
                    // 所以采用getExtraInfo获取接入点名字进行判断

                    String netMode = mobNetInfoActivity.getExtraInfo();
                    Log.i("", "==================netmode:" + netMode);
                    if (netMode != null) {
                        // 通过apn名称判断是否是联通和移动wap
                        netMode = netMode.toLowerCase();

                        if (netMode.equals(CMWAP)) {
                            /** 自定义Tost */
                            Toast toast = Toast.makeText(mContext,
                                    "当前是中国移动梦网2G网络", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                            ImageView imageView = new ImageView(mContext);// 创建视图对象
                            imageView.setImageResource(R.drawable.china_mobile); // 设置图像
                            LinearLayout toastView = (LinearLayout) toast
                                    .getView();// 获得toast的布局
                            toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                            toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                            toast.show();
                            /** 自定义Tost结束 */

                            return is3G ? TYPE_CM_WAP : TYPE_CM_WAP_2G;

                        } else if (netMode.equals(CMNET)) {
                            /** 自定义Tost */
                            Toast toast = Toast.makeText(mContext,
                                    "当前是中国移动互联网2G网络", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                            ImageView imageView = new ImageView(mContext);// 创建视图对象
                            imageView.setImageResource(R.drawable.china_mobile); // 设置图像
                            LinearLayout toastView = (LinearLayout) toast
                                    .getView();// 获得toast的布局
                            toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                            toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                            toast.show();
                            /** 自定义Tost结束 */

                            return is3G ? TYPE_CM_NET : TYPE_CM_NET_2G;
                        } else if (netMode.equals(NET_3G)
                                || netMode.equals(UNINET)) {

                            /** 自定义Tost */
                            Toast toast = Toast.makeText(mContext,
                                    "当前是中国联通NET3G网络", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                            ImageView imageView = new ImageView(mContext);// 创建视图对象
                            imageView.setImageResource(R.drawable.china_unicom); // 设置图像
                            LinearLayout toastView = (LinearLayout) toast
                                    .getView();// 获得toast的布局
                            toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                            toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                            toast.show();
                            /** 自定义Tost结束 */

                            return is3G ? TYPE_CU_NET : TYPE_CU_NET_2G;
                        } else if (netMode.equals(WAP_3G)
                                || netMode.equals(UNIWAP)) {

                            /** 自定义Tost */
                            Toast toast = Toast.makeText(mContext,
                                    "当前是中国联通WAP3G网络", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0); // 显示的坐标
                            ImageView imageView = new ImageView(mContext);// 创建视图对象
                            imageView.setImageResource(R.drawable.china_unicom); // 设置图像
                            LinearLayout toastView = (LinearLayout) toast
                                    .getView();// 获得toast的布局
                            toastView.setBackgroundColor(Color.BLACK);
                            toastView.setOrientation(LinearLayout.VERTICAL);// 设置此布局为横向的
                            toastView.addView(imageView, 0);// 将ImageView在加入到此布局中的第一个位置
                            toast.show();
                            /** 自定义Tost结束 */

                            return is3G ? TYPE_CU_WAP : TYPE_CU_WAP_2G;
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return TYPE_OTHER;
        }

        return TYPE_OTHER;

    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;

        }
    }

}
