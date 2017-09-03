package com.iks.dddschach.util;

import java.lang.reflect.Array;


public class Misc {

    public  static <T> T[] invertArray(T[] obj) {
        if (obj == null || obj.length == 0) return obj;
        final int length = obj.length;
        T[] result = (T[]) Array.newInstance(obj[0].getClass(), length);
        for (int i = 0; i < length; i++) {
            result[length - i - 1] = obj[i];
        }
        return result;
    }

}
