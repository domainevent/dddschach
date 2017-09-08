package com.javacook.dddschach.persistence;

import com.javacook.dddschach.domain.Position;
import com.javacook.dddschach.domain.Spalte;
import com.javacook.dddschach.domain.Zeile;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class ObjectSerializer {

        public static String objectToString(Serializable object) throws UnsupportedEncodingException {
            final byte[] bytes = SerializationUtils.serialize(object);
            return Arrays.toString(bytes);
        }

        @SuppressWarnings("unchecked")
        public static <T extends Serializable> T stringToObject(String string, Class<T> clazz) throws UnsupportedEncodingException {
            String[] byteValues = string.substring(1, string.length() - 1).split(", ");
            byte[] bytes = new byte[byteValues.length];
            for (int i=0, len=bytes.length; i<len; i++) {
                bytes[i] = Byte.parseByte(byteValues[i]);
            }
            return SerializationUtils.deserialize(bytes);
        }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Position position = new Position(Spalte.E, Zeile.II);
        final String s = objectToString(position);
        System.out.println(s);
        final Serializable reconstr = stringToObject(s, Position.class);
        System.out.println(reconstr);
    }
}
