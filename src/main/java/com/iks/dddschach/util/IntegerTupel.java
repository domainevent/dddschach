package com.iks.dddschach.util;

/**
 * Repräsentiert ein Tupel bestehende aus zwei Integer-Werten.
 */
public class IntegerTupel extends Tupel<Integer, Integer> {

    public IntegerTupel(int elem1, int elem2) {
        super(elem1, elem2);
    }

    public int x() {
        return elem1;
    }

    public int y() {
        return elem2;
    }

    public IntegerTupel plus(IntegerTupel t) {
        return new IntegerTupel(x() + t.x(), y() + t.y());
    }

    public IntegerTupel minus(IntegerTupel t) {
        return new IntegerTupel(x() - t.x(), y() - t.y());
    }

    public IntegerTupel abs() {
        return new IntegerTupel(Math.abs(x()), Math.abs(y()));
    }

    public static IntegerTupel middel(IntegerTupel t1, IntegerTupel t2) {
        if (t1.x() > t2.x()) {
            return middel(t2, t1);
        }
        if (t1.y() <= t2.y()) {
            return new IntegerTupel((t1.x() + t2.x())/2, (t1.y() + t2.y())/2);
        }
        else {
            return new IntegerTupel((t1.x() + t2.x())/2, ((t1.y() + t2.y() + 1)/2));
        }
    }

    public static int maxNorm(IntegerTupel t1, IntegerTupel t2) {
        return Math.max(Math.abs(t2.x() - t1.x()), Math.abs(t2.y() - t1.y()));
    }

}
