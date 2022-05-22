package com.upssitech.caesarihm;

        import static android.content.ContentValues.TAG;

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothSocket;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Looper;
        import android.os.Message;
        import android.util.Log;
        import android.view.MotionEvent;
        import android.view.View;
        import android.webkit.WebView;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.material.switchmaterial.SwitchMaterial;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.OutputStream;
        import java.util.UUID;

public class ControlRobotActivity extends AppCompatActivity implements  View.OnTouchListener, View.OnClickListener {

    private String deviceName = null;
    private String deviceAddress;
    public static Handler handler;
    public static BluetoothSocket mmSocket;
    public static ConnectedThread connectedThread;
    public static CreateConnectThread createConnectThread;
    private Context context;
    private boolean switchIsChecked = false;

    private final static int CONNECTING_STATUS = 1; // used in bluetooth handler to identify message status
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_robot);

        ImageView stopIm = (ImageView) findViewById(R.id.imageStop);
        stopIm.setVisibility(View.INVISIBLE);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.loadUrl("http://willy.ddns.info/caesarPFR/map.jpg");

        //Setup command buttons
        Button buttonUp = (Button) findViewById(R.id.buttonUp);
        Button buttonDown = (Button) findViewById(R.id.buttonDown);
        Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
        Button buttonRight= (Button) findViewById(R.id.buttonRight);
        SwitchMaterial switchManAuto = (SwitchMaterial) findViewById(R.id.switchManAuto);
        buttonUp.setOnTouchListener(this);
        buttonDown.setOnTouchListener(this);
        buttonLeft.setOnClickListener(this);
        buttonRight.setOnClickListener(this);
        switchManAuto.setOnClickListener(this);

        // UI Initialization
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        switchManAuto.setEnabled(false);

        // If a bluetooth device has been selected from SelectDeviceActivity
        deviceName = getIntent().getStringExtra("deviceName");
        if (deviceName != null){
            // Get the device address to make BT Connection
            deviceAddress = getIntent().getStringExtra("deviceAddress");
            // Show progree and connection status
            progressBar.setVisibility(View.VISIBLE);

            /*
            This is the most important piece of code. When "deviceName" is found
            the code will call a new thread to create a bluetooth connection to the
            selected device (see the thread code below)
             */
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            createConnectThread = new CreateConnectThread(bluetoothAdapter,deviceAddress);
            createConnectThread.start();

        }

        /*
        Second most important piece of Code. GUI Handler
         */
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg){
                SwitchMaterial switchManAuto = (SwitchMaterial) findViewById(R.id.switchManAuto);
                switch (msg.what){
                    case CONNECTING_STATUS:
                        switch(msg.arg1){
                            case 1:
                                progressBar.setVisibility(View.GONE);
                                switchManAuto.setEnabled(true);
                                reload();
                                break;
                            case -1:
                                progressBar.setVisibility(View.GONE);
                                //buttonConnect.setEnabled(true);
                                Intent intent = new Intent(context,MainActivity.class);
                                intent.putExtra("errorReturned", "échec de la connexion, réessayez");
                                context.startActivity(intent);
                                break;
                        }
                        break;

                    case MESSAGE_READ:
                        String arduinoMsg = msg.obj.toString(); // Read message from Arduino
                        switch (arduinoMsg.toLowerCase()) {
                            case "led is turned on":
                                //imageView.setBackgroundColor(getResources().getColor(R.color.colorOn));
                                //textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                            case "led is turned off":
                                //imageView.setBackgroundColor(getResources().getColor(R.color.colorOff));
                                //textViewInfo.setText("Arduino Message : " + arduinoMsg);
                                break;
                        }
                }

            }
            public void reload() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        WebView map = (WebView) findViewById(R.id.webview);
                        map.reload();
                        //Toast.makeText(ControlRobotActivity.this,"Map Reloaded", Toast.LENGTH_SHORT).show();
                        reload();
                    }
                }, 2000);
            }
        };

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView stopIm = (ImageView) findViewById(R.id.imageStop);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        if(progressBar.getVisibility() != View.GONE)
            return false;
        switch (v.getId()) {
            case R.id.buttonUp:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    // u = 117 in ASCII
                    //Toast.makeText(ControlRobotActivity.this,"Up Pushed", Toast.LENGTH_SHORT).show();
                    connectedThread.write("u");//Send "u" to arduino throught Bluetooth for making the car go on
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    //Toast.makeText(ControlRobotActivity.this,"Up Released", Toast.LENGTH_SHORT).show();
                    // s = 115 in ASCII
                    connectedThread.write("s");//Send "s" to arduino throught Bluetooth for making the car stop
                }
                return true;
            case R.id.buttonDown:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Pressed
                    //Toast.makeText(ControlRobotActivity.this,"Down Pushed", Toast.LENGTH_SHORT).show();
                    // d = 100 in ASCII
                    connectedThread.write("d");//Send "d" to arduino throught Bluetooth for making the car go backwards
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Released
                    //Toast.makeText(ControlRobotActivity.this,"Down Released", Toast.LENGTH_SHORT).show();
                    // s = 115 in ASCII
                    connectedThread.write("s");//Send "s" to arduino throught Bluetooth for making the car stop
                }
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button buttonUp = (Button) findViewById(R.id.buttonUp);
        Button buttonDown = (Button) findViewById(R.id.buttonDown);
        Button buttonLeft = (Button) findViewById(R.id.buttonLeft);
        Button buttonRight= (Button) findViewById(R.id.buttonRight);
        SwitchMaterial switchManAuto = (SwitchMaterial) findViewById(R.id.switchManAuto);
        if(progressBar.getVisibility() != View.GONE){}
        else{
            switch (v.getId()) {
                case R.id.buttonRight:
                    // r = 114 in ASCII
                    //Toast.makeText(ControlRobotActivity.this,"Right Clicked", Toast.LENGTH_SHORT).show();
                    connectedThread.write("r");//Send "r" to arduino throught Bluetooth for making the car turn right
                    break;
                case R.id.buttonLeft:
                    // l = 108 in ASCII
                    //Toast.makeText(ControlRobotActivity.this,"Left Clicked", Toast.LENGTH_SHORT).show();
                    connectedThread.write("l");//Send "l" to arduino throught Bluetooth for making the car turn left
                    break;
                case R.id.switchManAuto:
                    Toast.makeText(ControlRobotActivity.this,"Switch Clicked", Toast.LENGTH_SHORT).show();
                    connectedThread.write("m");//Send "m" to arduino throught Bluetooth for change mode (man or auto)
                    if(switchManAuto.isChecked()){
                        buttonUp.setEnabled(false);
                        buttonDown.setEnabled(false);
                        buttonLeft.setEnabled(false);
                        buttonRight.setEnabled(false);
                    }
                    else{
                        buttonUp.setEnabled(true);
                        buttonDown.setEnabled(true);
                        buttonLeft.setEnabled(true);
                        buttonRight.setEnabled(true);
                    }
            }
        }
    }


    /* ============================ Thread to Create Bluetooth Connection =================================== */
    public static class CreateConnectThread extends Thread {

        public CreateConnectThread(BluetoothAdapter bluetoothAdapter, String address) {
            /*
            Use a temporary object that is later assigned to mmSocket
            because mmSocket is final.
             */
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
            BluetoothSocket tmp = null;
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();

            try {
                /*
                Get a BluetoothSocket to connect with the given BluetoothDevice.
                Due to Android device varieties,the method below may not work fo different devices.
                You should try using other methods i.e. :
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
                 */
                tmp = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);

            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.cancelDiscovery();
            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
                Log.e("Status", "Device connected");
                handler.obtainMessage(CONNECTING_STATUS, 1, -1).sendToTarget();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                    Log.e("Status", "Cannot connect to device");
                    handler.obtainMessage(CONNECTING_STATUS, -1, -1).sendToTarget();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            connectedThread = new ConnectedThread(mmSocket);
            connectedThread.run();
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }

    /* =============================== Thread for Data Transfer =========================================== */
    public static class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes = 0; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    /*
                    Read from the InputStream from Arduino until termination character is reached.
                    Then send the whole String message to GUI Handler.
                     */
                    buffer[bytes] = (byte) mmInStream.read();
                    String readMessage;
                    if (buffer[bytes] == '\n'){
                        readMessage = new String(buffer,0,bytes);
                        Log.e("Arduino Message",readMessage);
                        handler.obtainMessage(MESSAGE_READ,buffer).sendToTarget();
                        bytes = 0;
                    } else {
                        bytes++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes(); //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e("Send Error","Unable to send message",e);
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    /* ============================ Terminate Connection at BackPress ====================== */
    @Override
    public void onBackPressed() {
        // Terminate Bluetooth Connection and close app
       /* if (createConnectThread != null){
            createConnectThread.cancel();
        }
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);*/
//        Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);
    }
}