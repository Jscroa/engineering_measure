package com.cx.measure.comments;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by yyao on 2016/6/8.
 */
public class LocationTask {

    private Context context;
    private OnLocationCallBack callBack;

    private double longitude;
    private double latitude;

    private LocationManager locationManager;
    private LocationListener locationListener;

    public LocationTask(Context context) {
        this.context = context;
    }

    public void setCallBack(OnLocationCallBack callBack) {
        this.callBack = callBack;
    }

    public void startLocation() {
        Log.i("TAG","startLocation======1");
        // 检查权限

        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            if(callBack!=null)
                callBack.onFailure("未获得定位权限");
            return;
        }
        Log.i("TAG","startLocation======2");
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            if (callBack != null)
                callBack.onFailure("未获得任何定位信息");
            return;
        }
        Log.i("TAG","startLocation======3");

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (callBack != null)
                    callBack.onGetLocation(location.getLongitude(), location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                if (callBack != null)
                    callBack.onLocationProvider(provider);
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    public void stopLocation() {
        if (locationManager != null) {
            if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * 获得定位点的回调
     */
    public interface OnLocationCallBack {

        /**
         * 使用的定位方式回调
         *
         * @param provider
         */
        void onLocationProvider(String provider);

        /**
         * @param longitude 经度
         * @param latitude  纬度
         */
        void onGetLocation(double longitude, double latitude);

        /**
         * 定位失败
         *
         * @param msg
         */
        void onFailure(String msg);
    }
}
