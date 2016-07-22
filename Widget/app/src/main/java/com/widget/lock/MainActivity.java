package com.widget.lock;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //variable declaration part
    private Button mBtnEnable;
    private DevicePolicyManager mDevicePolicyManager;
    private ActivityManager mActivityManager;
    private ComponentName mComponentName;
    static final int RESULT_ENABLE = 1;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initViews();

    }

    /**
     * @see this method used init the layout views and class objects.
     */
    private void initViews() {
        mBtnEnable = (Button) findViewById(R.id.btn_enable);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mActivityManager = (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);
        mBtnEnable.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enable:
                Intent intent = new Intent(DevicePolicyManager
                        .ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        mComponentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Additional text explaining why this needs to be added.");
                startActivityForResult(intent, RESULT_ENABLE);
                break;

            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i(TAG, TAG + "Admin enabled!");
                } else {
                    Log.i(TAG, TAG + "Admin enable FAILED!");
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
