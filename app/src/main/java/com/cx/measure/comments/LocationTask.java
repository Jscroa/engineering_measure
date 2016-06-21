package com.cx.measure.comments;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by yyao on 2016/6/8.
 */
public class LocationTask {

    private Context context;
    private OnLocationCallBack callBack;

    private double longitude;
    private double latitude;

//    private LocationManager locationManager;
//    private LocationListener locationListener;

    LocationClient myLocationClient;

    public LocationTask(Context context) {
        this.context = context;
    }

    public void setCallBack(OnLocationCallBack callBack) {
        this.callBack = callBack;
    }

    public void startLocation() {
        try {
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//            option.setCoorType("wgs84");//可选，默认gcj02，设置返回的定位结果坐标系
            option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            option.setOpenGps(true);//可选，默认false,设置是否使用gps
            option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要

            myLocationClient = new LocationClient(context, option);
            myLocationClient.registerLocationListener(new BDLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    Toast.makeText(context,"获得定位信息",Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,"经度："+bdLocation.getLongitude()+"，纬度："+bdLocation.getLatitude(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,bdLocation.getCountry()+" "+ bdLocation.getCity()+" "+bdLocation.getDistrict(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,bdLocation.getAddrStr(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,bdLocation.getLocationDescribe(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(context,bdLocation.getLocationDescribe(),Toast.LENGTH_SHORT).show();
                    if (callBack != null) {
                        callBack.onGetLocation(bdLocation.getLongitude(), bdLocation.getLatitude());
                    }
                }
            });
            myLocationClient.start();
        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                Toast.makeText(context,"定位失败",Toast.LENGTH_SHORT).show();
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                callBack.onFailure("定位失败");
            }
        }
//        Log.i("TAG","startLocation======1");
//        // 检查权限
//
//        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            if(callBack!=null)
//                callBack.onFailure("未获得定位权限");
//            return;
//        }
//        Log.i("TAG","startLocation======2");
//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        String provider = null;
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            provider = LocationManager.GPS_PROVIDER;
//        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            provider = LocationManager.NETWORK_PROVIDER;
//        } else {
//            if (callBack != null)
//                callBack.onFailure("未获得任何定位信息");
//            return;
//        }
//        Log.i("TAG","startLocation======3");
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                if (callBack != null)
//                    callBack.onGetLocation(location.getLongitude(), location.getLatitude());
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                if (callBack != null)
//                    callBack.onLocationProvider(provider);
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//
//            }
//        };
//
//        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    public void stopLocation() {
        if (myLocationClient != null) {
            myLocationClient.stop();
        }

//        if (locationManager != null) {
//            if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//                return;
//            }
//            locationManager.removeUpdates(locationListener);
//        }
    }

    /**
     * 获得定位点的回调
     */
    public interface OnLocationCallBack {

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
