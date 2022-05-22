package com.upssitech.caesarihm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private String errorMsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Error message display
        errorMsg = getIntent().getStringExtra("errorReturned");
        if (errorMsg != null){
            Toast.makeText(this,errorMsg, Toast.LENGTH_SHORT).show();
        }

        // Bluetooth Setup
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get List of Paired Bluetooth Device
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        List<Object> deviceList = new ArrayList<>();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                DeviceInfoModel deviceInfoModel = new DeviceInfoModel(deviceName,deviceHardwareAddress);
                deviceList.add(deviceInfoModel);
            }
            // Display paired device using recyclerView
            RecyclerView recyclerView = findViewById(R.id.recyclerViewDevice);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DeviceListAdapter deviceListAdapter = new DeviceListAdapter(this,deviceList);
            recyclerView.setAdapter(deviceListAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        } else {
            View view = findViewById(R.id.recyclerViewDevice);
            Snackbar snackbar = Snackbar.make(view, "Veuillez activer le Bluetooth", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("C'est fait !", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = getApplicationContext();
                    PackageManager packageManager = context.getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                    ComponentName componentName = intent.getComponent();
                    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                    context.startActivity(mainIntent);
                    Runtime.getRuntime().exit(0);
                }
            });
            snackbar.show();
        }
    }
}