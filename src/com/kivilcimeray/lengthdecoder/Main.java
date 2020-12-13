package com.kivilcimeray.lengthdecoder;

public class Main {

    public static void main(String[] args) {
        String ex1 = "4132231421";

        RunLengthDecoder decoder = new RunLengthDecoder(ex1);

        StringBuilder sb = new StringBuilder();
        while(decoder.hasNext()) {
            sb.append(decoder.next());
        }

        System.out.println(sb.toString());
    }
}
