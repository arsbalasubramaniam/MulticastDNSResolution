package com.example.multicastdnsresolution;
/*
Import package to do operations in activity
* */
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import com.example.multicastdnsresolution.Adapters.DnsListAdapter;
import com.example.multicastdnsresolution.Constants.Constants;
import com.example.multicastdnsresolution.Listener.MyDisCoveryListener;
import com.example.multicastdnsresolution.Listener.MyRegistrationListener;
import com.example.multicastdnsresolution.Listener.MyResolvedListener;
import com.example.multicastdnsresolution.Model.ScannedList;
import com.example.multicastdnsresolution.Utils.AppUtils;

/*
* Implements the MyRegistrationListener, MyDiscoveryListener, MyResolvedListener and OnClickListener
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, MyRegistrationListener.MyNewtorkRegistration, MyDisCoveryListener.NetworkDiscovery, MyResolvedListener.MyResolveListener{
    Button btn_Scan,btn_Publish;
    RecyclerView recyclerView;
    private NsdManager nsdManager;
    Context mContext;
    private DnsListAdapter dnsListAdapter = null;
    MyDisCoveryListener disCoveryListener = new MyDisCoveryListener(this);
    MyRegistrationListener mRegistrationListener = new MyRegistrationListener(this);
    private boolean isServicePublished;
    private boolean isDisCoveryRunning;
    CountDownTimer countDownTimer = null;
    boolean isPublishedClicked;
    boolean isScanClicked;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        nsdManager = AppUtils.initializeNSDManger(mContext);
        progressBar = new ProgressDialog(mContext);
        init();
    }

    //Initialize the variables
    private void init() {
        btn_Publish=(Button)findViewById(R.id.button_publish);
        btn_Scan=(Button)findViewById(R.id.button_scan);
        recyclerView=(RecyclerView)findViewById(R.id.list_dns_server);
        btn_Publish.setOnClickListener(this);
        btn_Scan.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        dnsListAdapter = new DnsListAdapter(mContext);
        recyclerView.setAdapter(dnsListAdapter);
        // creating progress bar dialog
        progressBar.setCancelable(true);
        progressBar.setMessage("Searching Multicast DNS Resolution in the local network.");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
    }

    @Override
    protected void onPause() {
        if (nsdManager != null) {
//            if (isPublishedClicked) {
            unRegisterService();
//            }
//            if (isScanClicked) {
            stopDisCoverService();
//            }
        }
        if (countDownTimer != null) {
            stopTimer();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nsdManager != null) {
            if (isPublishedClicked) {
                registerService(Constants.PORT);
            }
            if (isScanClicked) {
                disCoverService();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_publish:
                isPublishedClicked = true;
                isScanClicked = false;
                registerService(Constants.PORT);
                break;
            case R.id.button_scan:
                if (btn_Scan.getText().toString().equalsIgnoreCase("Scan")) {
                    isPublishedClicked = false;
                    isScanClicked = true;
                    disCoverService();
                }
                break;
        }
    }

    /**
     * Register service
     *
     * @param port
     */
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();
        serviceInfo.setServiceName(Constants.SERVICE_NAME);
        serviceInfo.setServiceType(Constants.SERVICE_TYPE);
        serviceInfo.setPort(port);
        if (!isServicePublished) {
            isServicePublished = true;
            nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                    mRegistrationListener);
        }
    }


    /**
     * Unregister service
     */
    public void unRegisterService() {
        if (isServicePublished) {
            isServicePublished = false;
            nsdManager.unregisterService(mRegistrationListener);
        }
    }

    /**
     * Discover service
     */
    public void disCoverService() {
        if (!isDisCoveryRunning) {
            isDisCoveryRunning = true;
            startTime();
            dnsListAdapter.refreshAdapter();
            nsdManager.discoverServices(Constants.SERVICE_TYPE,
                    NsdManager.PROTOCOL_DNS_SD, disCoveryListener);
        }
    }

    /**
     * Stop discoverService
     */
    public void stopDisCoverService() {
        if (isDisCoveryRunning) {
            isDisCoveryRunning = false;
            nsdManager.stopServiceDiscovery(disCoveryListener);
        }
    }

    void startTime() {
        progressBarStatus = 0;
        countDownTimer = new CountDownTimer(10000, 100) { // count down for 10seconds

            public void onTick(long millisUntilFinished) {
                progressBar.show();
                progressBarStatus = (int) millisUntilFinished / 100;
                progressBar.setProgress(progressBarStatus);
            }

            public void onFinish() {
                // close the progress bar dialog
                progressBar.dismiss();
                stopDisCoverService();
            }
        }.start();

    }

    void stopTimer() {
        btn_Scan.setText("Scan");
        countDownTimer.cancel();
    }

    @Override
    public void onSeriveFound(NsdServiceInfo serviceInfo) {
        nsdManager.resolveService(serviceInfo, new MyResolvedListener(this));
    }

    @Override
    public void discoveryStatus(String message) {
        AppUtils.showToast(mContext, message);
    }

    @Override
    public void onDeviceRegistration(String message) {
        AppUtils.showToast(mContext, message);
    }

    @Override
    public void onDeviceFound(ScannedList list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dnsListAdapter.updateList(list);

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
