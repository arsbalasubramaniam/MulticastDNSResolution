package com.example.multicastdnsresolution.Listener;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
/*MyDiscoveryListener to implement the NSDManager.DiscoveryListener
* */
public class MyDisCoveryListener implements NsdManager.DiscoveryListener {
    private NetworkDiscovery discovery = null;
    public MyDisCoveryListener(NetworkDiscovery discovery) {
        this.discovery = discovery;
    }
    public interface NetworkDiscovery {
        void onSeriveFound(NsdServiceInfo serviceInfo);

        void discoveryStatus(String message);
    }
    @Override
    public void onStartDiscoveryFailed(String s, int i) {
        discovery.discoveryStatus("Start discovery failed");
    }

    @Override
    public void onStopDiscoveryFailed(String s, int i) {
        discovery.discoveryStatus("Stop discovery failed");
    }

    @Override
    public void onDiscoveryStarted(String s) {
        discovery.discoveryStatus("Discovery started");
    }

    @Override
    public void onDiscoveryStopped(String s) {
        discovery.discoveryStatus("Discovery stop");
    }

    @Override
    public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
        discovery.onSeriveFound(nsdServiceInfo);
    }

    @Override
    public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
        discovery.discoveryStatus("Service lost");
    }
}
