package ru.otus;

import java.util.Random;
import java.util.UUID;

public class Data {

    private static final String DEFAULT_INFO = "data: №%s";

    private String info;
    private UUID uuid = UUID.randomUUID();

    public Data() {
        this.info = String.format(DEFAULT_INFO, uuid.toString());
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
