package com.example.lockscreenprompt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 仿qq，微信，支付宝锁屏消息
 * 使用步骤，点击这个按钮以后，按返回键退出APP，关闭手机屏幕，5s以后会受到锁屏消息，可以点击进入消息详情页面
 */
public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.permission_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
                startActivity(intent); //启动权限申请页面
            }
        });

        findViewById(R.id.start_service_btn).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent); //启动后台服务
            }
        });
    }
}