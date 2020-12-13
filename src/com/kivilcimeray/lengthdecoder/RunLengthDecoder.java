package com.kivilcimeray.lengthdecoder;

import java.util.Iterator;

public class RunLengthDecoder implements Iterator {

    private String exactString;
    private int producerNumber;

    public RunLengthDecoder(String exactString) {
        this.exactString = exactString;
        producerNumber = Character.getNumericValue(exactString.charAt(0));
    }

    @Override
    public boolean hasNext() {
        return producerNumber != 0 || exactString.length() > 2;
    }

    @Override
    public String next() {
        if(producerNumber == 0 && exactString.length() > 2) {
            exactString = exactString.substring(2);
            producerNumber = Character.getNumericValue(exactString.charAt(0));
        }

        char element = exactString.charAt(1);

        producerNumber--;

        return String.valueOf(element);
    }
}
