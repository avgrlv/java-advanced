package org.avgrlv.domain;

import java.util.ArrayList;
import java.util.List;

public class Box {
    String value = "";
    List<String> values = new ArrayList<>();

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Box{ " + values + " }\n { " + value + " }";
    }

    public synchronized void append(String s) {
        System.out.println("Было: " + value + " + " + s);
        value += " " + s;
    }

    public void appendToList(String s) {
        System.out.println(Thread.currentThread().getName() + " было: " + values);
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " прибавляет " + s);
            values.add(s);
        }
        System.out.println(Thread.currentThread().getName() + " стало: " + values);
    }

    public List<String> getValues() {
        return values;
    }
}
