package com.cx.measure;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.cx.measure.comments.CrashHandler;
import com.cx.measure.comments.LocationTask;
import com.senter.support.openapi.StUhf;

import org.xutils.x;

/**
 * Created by yyao on 2016/6/6.
 */
public class MyApplication extends Application {
    public static final String TAG="MeasureApp";
    public LocationTask locationTask;
    private static StUhf uhf;
    private static MyApplication mSinglton;
    private static StUhf.InterrogatorModel uhfInterfaceAsModel;
    private static Configuration mAppConfiguration;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        mSinglton = this;
        locationTask = new LocationTask(getApplicationContext());
        x.Ext.init(this);
    }
    public static MyApplication AppInstance()
    {
        return mSinglton;
    }
    /**
     * create a uhf instance with the specified model if need
     */
    public static StUhf getUhf(StUhf.InterrogatorModel interrogatorModel){
        if (uhf==null) {
            uhf=StUhf.getUhfInstance(interrogatorModel);
            uhf.init();
            uhfInterfaceAsModel=interrogatorModel;
        }
        return uhf;
    }
    public static StUhf getUhfWithDetectionAutomaticallyIfNeed()
    {
        if (uhf == null)
        {
            StUhf rf = null;
//            if (appCfgSavedModel()==null)
//            {
                rf = StUhf.getUhfInstance();
//            }else {
//                rf=StUhf.getUhfInstance(appCfgSavedModel());
//            }

            if (rf == null)
            {
                Log.e(TAG, "Rfid instance is null,exit");
                return null;
            }

            boolean b = rf.init();
            if (b == false)
            {
                Log.e(TAG, "cannot init rfid");
                return null;
            }
            StUhf.InterrogatorModel model=rf.getInterrogatorModel();
            uhf = rf;
            uhfInterfaceAsModel=model;

//            appCfgSaveModel(model);
            switch (model)
            {
                case InterrogatorModelA:
                case InterrogatorModelB:
                case InterrogatorModelC:
                case InterrogatorModelD2:
                case InterrogatorModelE:
                    break;
                default:
                    throw new IllegalStateException("new rfid model found,please check your code for compatibility.");
            }
        }
        return uhf;
    }

    public static StUhf uhf()
    {
        return uhf;
    }
    public static void uhfClear()
    {
        uhf=null;
        uhfInterfaceAsModel=null;
    }
    public static StUhf.InterrogatorModel uhfInterfaceAsModel(){
        if (uhf==null||uhfInterfaceAsModel==null) {	throw new IllegalStateException();	}
        return uhfInterfaceAsModel;
    }
    public static StUhf.InterrogatorModelA uhfInterfaceAsModelA()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelA);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelA.class);
    }
    public static StUhf.InterrogatorModelB uhfInterfaceAsModelB()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelB);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelB.class);
    }
    public static StUhf.InterrogatorModelC uhfInterfaceAsModelC()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelC);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelC.class);
    }
    public static StUhf.InterrogatorModelDs.InterrogatorModelD2 uhfInterfaceAsModelD2()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelD2);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelDs.InterrogatorModelD2.class);
    }
    public static StUhf.InterrogatorModelE uhfInterfaceAsModelE()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelE);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelE.class);
    }
    public static StUhf.InterrogatorModelF uhfInterfaceAsModelF()
    {
        assetUhfInstanceObtained();
        assert(uhfInterfaceAsModel()== StUhf.InterrogatorModel.InterrogatorModelF);
        return uhf.getInterrogatorAs(StUhf.InterrogatorModelF.class);
    }
    private static void assetUhfInstanceObtained(){
        if (uhf==null||uhfInterfaceAsModel==null) {	throw new IllegalStateException();	}
    }

    /**
     * stop the operation excuting by module,three times if need.
     *
     */
    public static boolean stop()
    {
        if (uhf != null)
        {
            if (uhf.isFunctionSupported(com.senter.support.openapi.StUhf.Function.StopOperation))
            {
                for (int i = 0; i < 3; i++)
                {
                    if (uhf().stopOperation())
                    {
                        Log.e(TAG, "stop  success");
                        return true;
                    }
                }
                Log.e(TAG, "stop  failed");
                return false;
            }
        }
        return true;
    }

    /**
     * clear both mask settings
     *
     */
    public static void clearMaskSettings()
    {
        if (uhf.isFunctionSupported(StUhf.Function.DisableMaskSettings))
        {
            uhf.disableMaskSettings();
        }
    }

//    public static final StUhf.InterrogatorModel appCfgSavedModel()
//    {
//        String modelName=appConfiguration().getString("modelName", "");
//        if (modelName.length()!=0)
//        {
//            try {
//                return StUhf.InterrogatorModel.valueOf(modelName);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static final void appCfgSaveModelClear()
//    {
//        appConfiguration().setString("modelName", "");
//    }
//    public static final void appCfgSaveModel(StUhf.InterrogatorModel model)
//    {
//        if (model==null)
//        {
//            throw new NullPointerException();
//        }
//        appConfiguration().setString("modelName", model.name());
//    }
//    private static final Configuration appConfiguration()
//    {
//        if (mAppConfiguration==null)
//        {
//            mAppConfiguration=new Configuration(mSinglton, "settings", Context.MODE_PRIVATE);
//        }
//        return mAppConfiguration;
//    }
}
