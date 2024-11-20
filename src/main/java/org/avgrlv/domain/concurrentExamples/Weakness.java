package org.avgrlv.domain.concurrentExamples;

public class Weakness {
    private Boolean flag = true;

    public boolean getFlag() {
        return flag;
    }

    public void switchOff() {
        this.flag = false;
    }
}
