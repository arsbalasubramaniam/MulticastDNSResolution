package com.example.multicastdnsresolution.Listener;

import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
/*Implents RegistrationListener
* */
public class MyRegistrationListener implements NsdManager.RegistrationListener {
    private MyNewtorkRegistration registration = null;
    public interface MyNewtorkRegistration {
        void onDeviceRegistration(String message);
    }
    public MyRegistrationListener(MyNewtorkRegistration registration) {
        this.registration = registration;
    }
    @Override
    public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        registration.onDeviceRegistration("Registration failed!, Check Wi-Fi Connection and Try Again!.");
    }
    @Override
    public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
        registration.onDeviceRegistration("Unregistration failed");
    }
    @Override
    public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {
        registration.onDeviceRegistration("Service registered");
    }
    @Override
    public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {
        registration.onDeviceRegistration("Service unregistered");
    }
}
