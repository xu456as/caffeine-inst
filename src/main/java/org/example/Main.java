package org.example;

import com.google.common.util.concurrent.RateLimiter;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        long ts = System.currentTimeMillis();
        System.out.println(ts);
        Date date = new Date(ts);
        date.toString();
        System.out.println(date);
    }
}