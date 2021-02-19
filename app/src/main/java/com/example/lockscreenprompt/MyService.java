package com.example.lockscreenprompt;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * 模拟推送，在退出APP后的一段时间发送消息
 */
public class MyService extends Service
{
    private static final String TAG = "MyService";

    public MyService()
    {
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.i(TAG, "onBind: ");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        Log.i(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.i(TAG, "onStartCommand: ");
        sendMessage();
        return START_STICKY;
    }


    @Override
    public void onDestroy()
    {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }


    /**
     * 模仿推送，发消息
     */
    private void sendMessage()
    {
        System.out.println("sendMessage");
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                //setAction()：表明我们想要启动能够响应设置的这个action的活动，并在清单文件AndroidManifest.xml中设置action属性
                intent.setAction("com.example.lockscreenprompt.LockScreenMsgReceiver");
                //Android O版本对后台进程做了限制广播的发送，对隐式广播也做了限制；必须要指定接受广播类的包名和类名
                //解决Background execution not allowed-----8.0以上发送的隐式广播无法被收到问题
                //"指定接受广播类的包名和类名"
                intent.setComponent(new ComponentName("com.example.lockscreenprompt", "com.example.lockscreenprompt.LockScreenMsgReceiver"));
                sendBroadcast(intent); //发送广播
            }
        }).start();
    }
}