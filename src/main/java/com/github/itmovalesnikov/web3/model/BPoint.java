package com.github.itmovalesnikov.web3.model;

import java.io.Serializable;

import org.apache.commons.math3.fraction.BigFraction;

import com.github.itmovalesnikov.web3.utils.BigFractionConverter;
import com.github.itmovalesnikov.web3.utils.BigFractionUtils;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "points")
public class BPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = BigFractionConverter.class)
    @Column(nullable = false)
    private BigFraction x;

    @Convert(converter = BigFractionConverter.class)
    @Column(nullable = false)
    private BigFraction y;

    @Convert(converter = BigFractionConverter.class)
    @Column(nullable = false)
    private BigFraction r;

    @Column(nullable = false)
    private boolean hit;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    protected BPoint() {
    }

    public BPoint(BigFraction x, BigFraction y, BigFraction r, String sessionId) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit(x, y, r);
        this.sessionId = sessionId;
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

    public String getSessionId() {
        return sessionId;
    }

    public Long getId() {
        return id;
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