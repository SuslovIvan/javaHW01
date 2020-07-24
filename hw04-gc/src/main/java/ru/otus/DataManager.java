package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class DataManager implements DataManagerMBean, Runnable {

    private Thread thread;
    private boolean running = false;

    private static final int DEFAULT_SLEEP_DURATION_MS = 100;

    private List<Data> dataList = new ArrayList<>();

    private static final int DEFAULT_PUSH_SIZE = 3000;
    private static final int DEFAULT_REMOVE_SIZE = 1500;
    private static final int DEFAULT_TIMEOUT_MS = -1;

    private int pushSize;
    private int removeSize;
    private int timeout;

    private long startTimeMS;
    private long stopTimeMS;
    private long timeoutTimeMS;

    public DataManager() {
        this(DEFAULT_PUSH_SIZE, DEFAULT_REMOVE_SIZE, DEFAULT_TIMEOUT_MS);
    }

    public DataManager(int pushSize, int removeSize, int timeout) {
        this.removeSize = removeSize;
        this.pushSize = pushSize;
        this.timeout = timeout;
    }

    public void run() {
        startTimeMS = System.currentTimeMillis();
        timeoutTimeMS = startTimeMS + timeout;

        while (running) {
            pushData(pushSize);
            removeData(removeSize);

            checkTime();
        }
    }

    public void start() {
        if (thread == null || thread.isInterrupted()) {
            System.out.println("start: creating new thread---");
            thread = new Thread(this);
            running = true;
            thread.start();
        } else {
            System.out.println("start: error, thread is already started");
        }
    }

    private void sleep() {
        if (thread == null) {
            System.out.println("sleep: error, thread is null");
            return;
        }
        try {
            thread.sleep(DEFAULT_SLEEP_DURATION_MS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void stop() {
        thread.interrupt();
        running = false;
        stopTimeMS = System.currentTimeMillis();
    }

    public void checkTime() {
        if (timeout <= 0) {
            return;
        }
        long currentTimeMS = System.currentTimeMillis();
        if (currentTimeMS >= timeoutTimeMS) {
            stop();
        }
    }

    public void pushData(int size) {
        for (int i = 0; i < size; i++) {
            dataList.add(new Data());
        }

        sleep();
    }

    public void removeData(int size) {
        for (int i = 0; i < size; i++) {
            dataList.remove(i % dataList.size());
        }

        sleep();
    }


    @Override
    public void setPushSize(int size) {
        this.pushSize = size;
    }

    @Override
    public void setRemoveSize(int size) {
        this.removeSize = removeSize;
    }
}


