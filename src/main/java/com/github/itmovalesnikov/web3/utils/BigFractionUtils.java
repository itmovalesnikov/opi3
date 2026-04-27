package com.github.itmovalesnikov.web3.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.apache.commons.math3.fraction.BigFraction;

public class BigFractionUtils {
    public static BigFraction fromDecimalString(String str) {
        str = str.trim();
        if (str.contains("/")) {
            String[] parts = str.split("/");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid fraction format: " + str);
            }
            BigInteger numerator = new BigInteger(parts[0].trim());
            BigInteger denominator = new BigInteger(parts[1].trim());
            return new BigFraction(numerator, denominator);
        } else {
            BigDecimal bd = new BigDecimal(str);
            return new BigFraction(bd.unscaledValue(), BigInteger.TEN.pow(bd.scale()));
        }
    }

    public static String toDecimalString(BigFraction fraction) {
        BigInteger num = fraction.getNumerator();
        BigInteger den = fraction.getDenominator();

        BigInteger gcd = num.gcd(den);
        num = num.divide(gcd);
        den = den.divide(gcd);

        if (isTerminating(fraction)) {
            BigDecimal numerator = new BigDecimal(num);
            BigDecimal denominator = new BigDecimal(den);
            BigDecimal result = numerator.divide(denominator);
            return result.stripTrailingZeros().toPlainString();
        } else {
            return num + "/" + den;
        }
    }

    public static boolean isTerminating(BigFraction fraction) {
        BigInteger den = fraction.getDenominator();
        BigInteger gcd = fraction.getNumerator().gcd(den);
        den = den.divide(gcd);

        while (den.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            den = den.divide(BigInteger.TWO);

        while (den.mod(BigInteger.valueOf(5)).equals(BigInteger.ZERO))
            den = den.divide(BigInteger.valueOf(5));

        return den.equals(BigInteger.ONE);
    }
}