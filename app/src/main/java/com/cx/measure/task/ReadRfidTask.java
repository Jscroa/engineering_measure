package com.cx.measure.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.Toast;

import com.cx.measure.MyApplication;
import com.cx.measure.comments.DataTransfer;
import com.senter.support.openapi.StUhf;

/**
 * Created by yyao on 2016/7/12.
 */
public class ReadRfidTask extends AsyncTask<Void, Void, String> {

    private Context context;


    public ReadRfidTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Looper.prepare();

            //TODO step1 初始化
            //TODO step2 自动选择模式
            StUhf.InterrogatorModel model = automaticallyModelChoose();
            if (model == null) {
                return "模式选择失败";
            }
            //TODO step3 自动设置参数
            StUhf.Bank bank = StUhf.Bank.User;
            int ptr = 0;
            int cnt = 1;
            //TODO step4 扫描
            onRead(bank, ptr, cnt);
            return "读取完成";
        }catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            Toast.makeText(context,e.getCause().getMessage(),Toast.LENGTH_SHORT).show();
            return "扫描时发生异常";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 自动选择模式
     * @return
     */
    protected StUhf.InterrogatorModel automaticallyModelChoose() {
        if (MyApplication.getUhfWithDetectionAutomaticallyIfNeed() != null) {
            return MyApplication.uhf().getInterrogatorModel();
        } else {
            Toast.makeText(context, "no uhf module detected", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    protected void onRead(final StUhf.Bank bank, final int ptr, final int cnt) {
        StUhf.Result.ReadResult rr = null;
        rr = MyApplication.uhfInterfaceAsModelA().readDataFromSingleTag( StUhf.AccessPassword.getNewInstance((byte)0,(byte)0,(byte)0,(byte)0), bank, ptr, (int) (byte) cnt);

        if (rr == null || rr.isSucceeded() == false) {

            Toast.makeText(context,"无法读取",Toast.LENGTH_SHORT).show();
        } else {
            StUhf.UII uii = rr.getUii();
            Toast.makeText(context,(uii != null ? DataTransfer.xGetString(uii.getBytes()) : "读取到为空"),Toast.LENGTH_SHORT).show();
        }
    }
}
