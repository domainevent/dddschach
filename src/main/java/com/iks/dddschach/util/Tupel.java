package com.iks.dddschach.util;

/**
 * Created by vollmer on 25.08.16.
 */
public class Tupel<E1, E2> {

    public final E1 elem1;
    public final E2 elem2;

    public Tupel(E1 elem1, E2 elem2) {
        this.elem1 = elem1;
        this.elem2 = elem2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tupel<?, ?> tupel = (Tupel<?, ?>) o;

        if (elem1 != null ? !elem1.equals(tupel.elem1) : tupel.elem1 != null) return false;
        return elem2 != null ? elem2.equals(tupel.elem2) : tupel.elem2 == null;

    }

    @Override
    public int hashCode() {
        int result = elem1 != null ? elem1.hashCode() : 0;
        result = 31 * result + (elem2 != null ? elem2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tupel{" +
                "elem1=" + elem1 +
                ", elem2=" + elem2 +
                '}';
    }
}
