package com.example.andrewgardiner.myhearthealth;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnterActivity extends AppCompatActivity {
    private final static String TAG = EnterActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_SYSTALICBP = "SYSTALICBP";
    public static final String EXTRAS_DISTALICBP = "DISTALICBP";
    public static final String EXTRAS_WEIGHT = "WEIGHT";
    public static final String EXTRAS_BLOOD_GLUCOSE = "BLOOD_GLUCOSE";
    private EditText SystolicBP;
    private EditText DiastolicBP;
    private EditText Weight;
    private EditText BloodGlucose;
    private String mSystolicBP;
    private String mDiastolicBP;
    private String mWeight;
    private String mBloodGlucose;
    private TextView mConnectionState;
    private TextView mDataField;
    private String mDeviceName;
    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    private BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private Button record;
    private DatabaseManager retriever;
    private TextView bpmComment;
    private TextView systolicComment;
    private TextView diastolicComment;
    private TextView weightComment;
    private TextView BLComment;
    private TextView riskComment;
    String data;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
           Log.d(TAG, "binded");
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.d(TAG, "services detected");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (mGattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                mGattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(
                                        mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(
                                    characteristic, true);
                        }
                        return true;
                    }
                    return false;
                }
            };

    private void clearUI() {
        mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        mDataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        mSystolicBP = intent.getStringExtra(EXTRAS_SYSTALICBP);
        mDiastolicBP = intent.getStringExtra(EXTRAS_DISTALICBP);
        mWeight = intent.getStringExtra(EXTRAS_WEIGHT);
        mBloodGlucose = intent.getStringExtra(EXTRAS_BLOOD_GLUCOSE);


        // Sets up UI references.
        record = (Button) findViewById(R.id.btnRecord);
       // ((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
        SystolicBP = (EditText) findViewById(R.id.txtSystalicBP);
        DiastolicBP = (EditText) findViewById(R.id.txtDiastolicBP);
        Weight = (EditText) findViewById(R.id.txtWeight);
        BloodGlucose = (EditText) findViewById(R.id.txtBL);
        //  mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        //  mGattServicesList.setOnChildClickListener(servicesListClickListner);
       // mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);
        bpmComment = (TextView) findViewById(R.id.bpmcomment);
        systolicComment=(TextView) findViewById(R.id.systolicComment);
        diastolicComment=(TextView) findViewById(R.id.diastolicComment);
        weightComment=(TextView) findViewById(R.id.weightComment);
        BLComment=(TextView) findViewById(R.id.BGcomment);
        riskComment=(TextView) findViewById(R.id.riskComment);

        final String systolic = SystolicBP.toString();
         final String Diastolic = DiastolicBP.toString();
       final  String weight = Weight.toString();
         final String BL = BloodGlucose.toString();
        SystolicBP.setText(mSystolicBP);
        DiastolicBP.setText(mDiastolicBP);
        Weight.setText(mWeight);
        BloodGlucose.setText(mBloodGlucose);
        retriever = new DatabaseManager(this);

        //  getActionBar().setTitle(mDeviceName);
      //  getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        Toast.makeText(this, "binded", Toast.LENGTH_LONG).show();
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriever.open();
                Profile profile= new Profile();
                int bpm = Integer.parseInt(mDataField.getText().toString());
                int sys=Integer.parseInt(SystolicBP.getText().toString());
                int dis=Integer.parseInt(DiastolicBP.getText().toString());
                int bl=Integer.parseInt(BloodGlucose.getText().toString());
                int we=Integer.parseInt(Weight.getText().toString());


                profile = retriever.createProfile(bpm,sys,dis,bl,we);
                Log.d(TAG,"Success");
                retriever.close();


            }
        });
    }

    public void comments(){
        Profile profile = new Profile();
        profile = retriever.getPrevious();
        VitalsAnalyser analyser = new VitalsAnalyser();
        String bpmHighwarning = "Your bpm is over 100, you should take a rest. if persists seek medical help";
        String bpmLow = "Your bpm is bellow 49, seek medical help";
        String bpmnorm = "Your bpm is normal";
        String SysHigh ="your Systolic blood pressure is high, over 140 which is high risk";
        String SysLow = "Your Systolic blood pressure is low, below 79 which is a high risk";
        String Sysnorm = "Your Systolic blood pressure is normal";
        String DiaHigh = "Your Diastolic blood pressure is high, over 90 which is a high risk";
        String DiaLow = "your Diastolic blood pressure is low, below 49 which is a high risk";
        String DiaNorm = "Your diastolic blood pressure is normal";
        String WeHigh = "your weight has gone up over 2lbs since last time, which isa high risk";
        String Welow = "Your wight has dropped by over 2lbs since last time, Which is a high Risk";
        String wenorm = "Your weight has not changed significantly";
        String BLHigh ="your blood gluscose level is over 250, which is a high risk";
        String BLLow = "Your blood glucose lever is below 50, which is a high risk";
        String BLNorm = "your Blood glucose level is normal";

        int bpmcode = analyser.analyseBPM(profile.getBpm());
        int systaliccode = analyser.analyseSYS(profile.getSystalicBP());
        int diacode = analyser.analyseDIA(profile.getDistolicBP());
        int wecode = analyser.analyseWeight(Integer.parseInt(Weight.getText().toString()), profile.getWeight());
        int blcode = analyser.analyseBL(profile.getBlood_glucose());

        if(bpmcode == 3) {
            if (profile.getBpm() < 49)
                bpmComment.setText(bpmLow);
            else {
                bpmComment.setText(bpmHighwarning);
            }
        }
        else if(bpmcode == 1)
            bpmComment.setText(bpmnorm);

        if(systaliccode == 3) {
            if (profile.getSystalicBP() <= 79)
                systolicComment.setText(SysLow);
            else {
                systolicComment.setText(SysHigh);
            }
        }
        else if(systaliccode == 1)
            systolicComment.setText(Sysnorm);

        if(diacode == 3) {
            if (profile.getDistolicBP() < 49)
                diastolicComment.setText(DiaLow);
            else {
                diastolicComment.setText(DiaHigh);
            }
        }
        else if(diacode == 1)
            diastolicComment.setText(DiaNorm);

        if(wecode == 3){
            if(Integer.parseInt(Weight.getText().toString())- profile.getWeight() > 2){
                weightComment.setText(WeHigh);
            }else{
                weightComment.setText(Welow);
            }
        }
        else if(wecode == 1)
            weightComment.setText(wenorm);
        if(blcode == 3){
            if(profile.getBlood_glucose() <  50){
                BLComment.setText(BLLow);
            }else{
                BLComment.setText(BLHigh);
            }
        }else if(blcode == 1)
            BLComment.setText(BLNorm);


       int decisionCode = analyser.analyse(profile.getBpm(),profile.getSystalicBP(),profile.getDistolicBP(),Integer.parseInt(Weight.getText().toString()),profile.getWeight(),profile.getBlood_glucose());

        // Toast.makeText(this,Integer.toString(decisionCode),Toast.LENGTH_LONG).show();
        if(decisionCode == 1){
            riskComment.setText("normal");
        }
        else if(decisionCode == 2){
            riskComment.setText("Medium Risk");
        }
        else if(decisionCode == 3){
            riskComment.setText("High risk, Action is recommended");

        }
        else {
            riskComment.setText("Not Enough Data");
        }

    }




    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }





    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnectionState.setText(resourceId);
            }
        });
    }

    private void displayData(String data) {

        this.data = data;

        if (data != null) {
            mDataField.setText(data);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, GattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, GattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
                if(uuid.equals(GattAttributes.HEART_RATE_MEASUREMENT)){
                    mBluetoothLeService.setCharacteristicNotification(gattCharacteristic,true);
                }
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }


    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

}
