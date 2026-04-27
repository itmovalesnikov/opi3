package com.github.itmovalesnikov.web3.model;

import java.io.Serializable;

import org.apache.commons.math3.fraction.BigFraction;

import com.github.itmovalesnikov.web3.utils.BigFractionUtils;

public class Point implements Serializable {
    private final BigFraction x;
    private final BigFraction y;
    private final BigFraction r;
    private final boolean hit;

    public Point(BigFraction x, BigFraction y, BigFraction r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit(x, y, r);
    }

    private static boolean checkHit(BigFraction xCoord, BigFraction yCoord, BigFraction rCoord) {
        BigFraction xDivided = xCoord.divide(rCoord);
        BigFraction yDivided = yCoord.divide(rCoord);

        BigFraction zero = BigFraction.ZERO;
        BigFraction one = BigFraction.ONE;
        BigFraction half = new BigFraction(1, 2);
        BigFraction minusOne = new BigFraction(-1);
        BigFraction two = new BigFraction(2);

        boolean result;
        if (xDivided.compareTo(zero) >= 0 && yDivided.compareTo(zero) >= 0) { // 0-90
            result = false;
        } else if (xDivided.compareTo(zero) < 0 && yDivided.compareTo(zero) > 0) { // 90-180
            BigFraction expr = yDivided.subtract(xDivided.multiply(two));
            result = expr.compareTo(one) <= 0;
        } else if (xDivided.compareTo(zero) < 0 && yDivided.compareTo(zero) <= 0) { // 180-270
            BigFraction xSquared = xDivided.multiply(xDivided);
            BigFraction ySquared = yDivided.multiply(yDivided);
            BigFraction sumSquares = xSquared.add(ySquared);
            result = sumSquares.compareTo(half.multiply(half)) <= 0;
        } else {
            result = xDivided.compareTo(half) <= 0 && yDivided.compareTo(minusOne) >= 0;
        }
        return result;
    }

    public String getX() {
        return BigFractionUtils.toDecimalString(x);
    }

    public String getY() {
        return BigFractionUtils.toDecimalString(y);
    }

    public String getR() {
        return BigFractionUtils.toDecimalString(r);
    }

    public boolean isHit() {
        return hit;
    }
}
