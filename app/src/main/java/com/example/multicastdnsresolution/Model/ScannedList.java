package com.example.multicastdnsresolution.Model;

import java.net.InetAddress;

/*Modal Class to implement the ScannedList
* */
public class ScannedList {

    private String serviceType;
    private String serviceName;
    private int port;
    private InetAddress IPAddress;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(InetAddress IPAddress) {
        this.IPAddress = IPAddress;
    }
}
