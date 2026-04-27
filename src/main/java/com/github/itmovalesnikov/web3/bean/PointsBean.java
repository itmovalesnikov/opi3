package com.github.itmovalesnikov.web3.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.fraction.BigFraction;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.github.itmovalesnikov.web3.model.BPoint;
import com.github.itmovalesnikov.web3.model.Point;
import com.github.itmovalesnikov.web3.utils.BigFractionUtils;
import com.github.itmovalesnikov.web3.utils.HibernateUtil;
import com.github.itmovalesnikov.web3.utils.SessionUtils;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("pointsBean")
@SessionScoped
public class PointsBean implements Serializable {

    private BigFraction x = BigFraction.ZERO;
    private BigFraction y = BigFraction.ZERO;
    private BigFraction r = BigFraction.ONE;
    private final List<Point> points = new ArrayList<>();

    public void submit() {
        Point point = new Point(x, y, r);
        points.add(point);

        BPoint bpoint = new BPoint(x, y, r, SessionUtils.getSessionId());
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(bpoint);
            session.getTransaction().commit();
        }
    }

    public String getX() {
        return BigFractionUtils.toDecimalString(x);
    }

    public void setX(String xStr) {
        this.x = BigFractionUtils.fromDecimalString(xStr);
    }

    public String getY() {
        return BigFractionUtils.toDecimalString(y);
    }

    public void setY(String yStr) {
        this.y = BigFractionUtils.fromDecimalString(yStr);
    }

    public String getR() {
        return BigFractionUtils.toDecimalString(r);
    }

    public void setR(String rStr) {
        this.r = BigFractionUtils.fromDecimalString(rStr);
    }

    public List<BPoint> getPoints() {
        List<BPoint> points = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BPoint> query = session.createQuery(
                    "FROM BPoint p WHERE p.sessionId = :sessionId", BPoint.class);
            query.setParameter("sessionId", SessionUtils.getSessionId());
            points = query.list();
            points.forEach((p) -> {
                System.out.println(p.getX() + " " + p.getY() + " " + p.getSessionId());
            });
        }
        return points;
    }
}
