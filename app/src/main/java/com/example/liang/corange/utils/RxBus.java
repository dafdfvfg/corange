package com.example.liang.corange.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liang on
 * 17/5/18.
 */

public class RxBus {

    public interface GlobalListener {
        void onGlobalEvent(int eventId, Object... args);
    }

    private static final String TAG = RxBus.class.getSimpleName();
    public static boolean DEBUG = false;
    private static RxBus instance;
    private ConcurrentHashMap<Integer, List<GlobalListener>> listeners = new ConcurrentHashMap<>();
//    private ConcurrentHashMap<Object, CompositeSubscription> subscriptionMapper = new ConcurrentHashMap<>();

    private RxBus() {
    }

    public static synchronized RxBus get() {
        if (null == instance) {
            instance = new RxBus();
        }
        return instance;
    }


    public GlobalListener register(int eventId, GlobalListener listener) {
        synchronized (listeners) {
            List<GlobalListener> subject = listeners.get(eventId);

            if (null == subject) {
                subject = new ArrayList<>();
                listeners.put(eventId, subject);

            }
            subject.remove(listener);
            subject.add(listener);
            return listener;
        }
    }

    public void unregisterAll(GlobalListener listener) {
        synchronized (listeners) {
            for (List<GlobalListener> listenersOfEvent : listeners.values()) {
                listenersOfEvent.remove(listener);
            }
        }

        if (DEBUG) Log.d(TAG, "[unregister]listeners: " + listeners);
    }

    public void post(final int eventId, final Object... args) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized (listeners) {
                    if (!listeners.containsKey(eventId)) {
                        return;
                    }
                    List<GlobalListener> listenersOfEvent = listeners.get(eventId);
                    for (GlobalListener l : listenersOfEvent) {
                        l.onGlobalEvent(eventId, args);
                    }
                }
            }
        });
    }

}
