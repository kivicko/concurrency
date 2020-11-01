package com.kivilcimeray.complexcalculation;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) throws InterruptedException {
        BigInteger result = BigInteger.ZERO;
        PowerCalculatingThread pow1 = new PowerCalculatingThread(base1, power1);
        PowerCalculatingThread pow2 = new PowerCalculatingThread(base2, power2);

        pow1.start();
        pow2.start();

        pow1.join();
        pow2.join();

        result = pow1.getResult().add(pow2.getResult());

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            if(BigInteger.ONE.equals(base)) {
               result = BigInteger.ONE;
            }

            if(BigInteger.ZERO.equals(base)) {
                result = BigInteger.ZERO;
            }

            if(BigInteger.ZERO.equals(power)) {
                result = base;
            }

            for(BigInteger i = BigInteger.ZERO; power.compareTo(i) >= 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
        }

        public BigInteger getResult() { return result; }
    }
}