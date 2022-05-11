package com.example.bluetooth_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView mstatusbluetoothTV,mpairedTv;
    ImageView mbluetothIv;
    Button mOnbtn,moffbtn,mdiscoverablebtn,mparidbtn;

    BluetoothAdapter mblueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mstatusbluetoothTV = findViewById(R.id.statusbluetoothTV);
        mpairedTv = findViewById(R.id.pairedTv);
        mbluetothIv = findViewById(R.id.bluetothIv);
        mOnbtn = findViewById(R.id.Onbtn);
        moffbtn = findViewById(R.id.offbtn);
        mdiscoverablebtn = findViewById(R.id.discoverablebtn);
        mparidbtn = findViewById(R.id.paridbtn);

        mblueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mblueAdapter == null){
            mstatusbluetoothTV.setText("Bluetooth is not available");
        }else{
            mstatusbluetoothTV.setText("Bluetooth is available");
        }

        if(mblueAdapter.isEnabled()){
            mbluetothIv.setImageResource(R.drawable.ic_action_on);
        }else{
            mbluetothIv.setImageResource(R.drawable.ic_action_off);
        }

        mOnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mblueAdapter.isEnabled()){
                    showToast("Turning on Bluetooth ...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_ENABLE_BT);

                }else{
                    showToast("Bluetooth is already on .....");

                }

            }
        });
        moffbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mblueAdapter.isEnabled()){
                    mblueAdapter.disable();
                    showToast("Turning off Bluetooth ...");
                    mbluetothIv.setImageResource(R.drawable.ic_action_off);
                }else{
                    showToast("Bluetooth is already off ....");
                }

            }
        });
        mdiscoverablebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mblueAdapter.isDiscovering()){
                    showToast("Making Your Device Discoverable ....");
                    Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,REQUEST_DISCOVER_BT);
                }

            }
        });
        mparidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mblueAdapter.isEnabled()){
                    mpairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mblueAdapter.getBondedDevices();
                    for(BluetoothDevice device: devices){
                        mpairedTv.append("\nDevice "+ device.getName()+","+device);
                    }
                }else {
                    showToast("Turn on bluetooth get paired devices..");
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode==RESULT_OK){
                    mbluetothIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }
                else{
                    showToast("could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}