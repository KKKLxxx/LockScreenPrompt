package com.example.lockscreenprompt;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;

// 仍然需要在AndroidManifest中申请权限
public class PermissionActivity extends AppCompatActivity
{

    private static final int NOT_NOTICE = 2;//如果勾选了不再询问
    private AlertDialog alertDialog;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        myRequetPermission();
    }

    private void myRequetPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else
        {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            for (int i = 0; i < permissions.length; i++)
            {
                if (grantResults[i] == PERMISSION_GRANTED)
                {//选择了“始终允许”
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]))
                    {//用户选择了禁止后不再询问

                        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                        builder.setTitle("permission")
                                .setMessage("点击允许才可以使用我们的app哦")
                                .setPositiveButton("去允许", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        if (mDialog != null && mDialog.isShowing())
                                        {
                                            mDialog.dismiss();
                                        }
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);//注意就是"package",不用改成自己的包名
                                        intent.setData(uri);
                                        startActivityForResult(intent, NOT_NOTICE);
                                    }
                                });
                        mDialog = builder.create();
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.show();


                    }
                    else
                    {//选择禁止
                        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
                        builder.setTitle("permission")
                                .setMessage("点击允许才可以使用我们的app哦")
                                .setPositiveButton("去允许", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        if (alertDialog != null && alertDialog.isShowing())
                                        {
                                            alertDialog.dismiss();
                                        }
                                        ActivityCompat.requestPermissions(PermissionActivity.this,
                                                                          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                                    }
                                });
                        alertDialog = builder.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    }

                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOT_NOTICE)
        {
            myRequetPermission();//由于不知道是否选择了允许所以需要再次判断
        }
    }
}