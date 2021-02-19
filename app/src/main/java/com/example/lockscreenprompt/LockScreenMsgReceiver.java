package com.example.lockscreenprompt;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * 监听锁屏消息的广播接收器
 */
public class LockScreenMsgReceiver extends BroadcastReceiver
{
    private static final String TAG = "LockScreenMsgReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "onReceive:收到了锁屏消息 ");
        String action = intent.getAction();
        if (action.equals("com.example.lockscreenprompt.LockScreenMsgReceiver"))
        {
            //管理锁屏的一个服务
            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            String text = km.inKeyguardRestrictedInputMode() ? "锁屏了" : "屏幕亮着的";
            Log.i(TAG, "text: " + text);
            if (km.inKeyguardRestrictedInputMode())
            {
                Log.i(TAG, "onReceive:锁屏了 ");
                //判断是否锁屏
                Intent alarmIntent = new Intent(context, MessageActivity.class);
                //在广播中启动Activity的context可能不是Activity对象，所以需要添加NEW_TASK的标志，否则启动时可能会报错。
                /*Context中有一个startActivity方法，Activity继承自Context，重载了startActivity方法。如果使用Activity的startActivity方法，不会有任何限制，
                而如果使用Context的startActivity方法的话，就需要开启一个新的task

                FLAG_ACTIVITY_NEW_TASK的意义可以这么理解，一般来说当我们从launcher中启动一个应用进入到ActivityA中，系统会为这个应用生成一个新任务堆栈并置于前台，
                ActivityA被放入栈底，之后从ActivityA启动另一个ActivityB，如果不设置什么附加属性，ActivityB默认也放到和ActivityA这个堆栈中，这样当你按返回时，
                B出栈，A呈现出来了，这个应该很好理解。那现在假如ActivityA启动一个Service或者发一个广播，这都是后台的活，和我们的任务栈没有关系，现在假如我们在广播中
                需要启动一个Activity，当然需要为这个Activity指定或分配一个任务栈*/
                alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 26)
                {
                    //没啥影响
                    alarmIntent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                }


                context.startActivity(alarmIntent); //启动显示锁屏消息的activity
            }
        }
    }
}