package ru.otus.helper;

public class Info {

    public int success = 0;
    public int failed = 0;
    public int total = 0;

    public Info() {
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void incSuccess() {
        success++;
    }

    public void incFailed() {
        failed++;
    }

    public void resetSuccess() {
        success = 0;
    }

    public void resetFailed() {
        failed = 0;
    }

    public void resetAll() {
        success = 0;
        failed = 0;
        total = 0;
    }

    @Override
    public String toString() {
        return "success=" + success + ", failed=" + failed + ", total=" + total;
    }
}
