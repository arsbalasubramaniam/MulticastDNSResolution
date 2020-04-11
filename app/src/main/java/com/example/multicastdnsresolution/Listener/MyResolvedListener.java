package com.example.multicastdnsresolution.Listener;

import android.annotation.SuppressLint;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import com.example.multicastdnsresolution.Model.ScannedList;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/*
Implements resolvelistener
* */
public class MyResolvedListener implements NsdManager.ResolveListener {

    private MyResolveListener myResolveListener = null;
    private Observable<ScannedList> observable = null;

    public interface MyResolveListener {
        void onDeviceFound(ScannedList list);
    }

    public MyResolvedListener(MyResolveListener myResolveListener) {
        this.myResolveListener = myResolveListener;
    }


    @Override
    public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int i) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
        ScannedList list = new ScannedList();
        list.setIPAddress(nsdServiceInfo.getHost());
        list.setPort(nsdServiceInfo.getPort());
        list.setServiceName(nsdServiceInfo.getServiceName());
        list.setServiceType(nsdServiceInfo.getServiceType());
        observable = Observable.just(list);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> myResolveListener.onDeviceFound(result));
    }
}
