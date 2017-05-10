package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


@XmlType
public class SpielfigurValueObject extends ValueObject {

    @XmlEnum
    public enum FigureEnum {
        KOENIG, DAME, TURM, LAEUFER, PFERD, BAUER;

        public Character abbreviation() {
            switch (this) {
                case KOENIG: return 'K';
                case DAME: return 'D';
                case TURM: return 'T';
                case LAEUFER: return 'L';
                case PFERD: return 'P';
                case BAUER: return 'B';
            }
            throw new IllegalArgumentException("Unexpected enum " + this);
        }
    };


    public final FigureEnum figure;
    public final FarbeEnum color;


    public SpielfigurValueObject() {
        this(null, null);
    }


    public SpielfigurValueObject(FigureEnum figure, FarbeEnum color) {
        this.figure = figure;
        this.color = color;
    }


    public String abbreviation() {
        return "" + figure.abbreviation() + color.abbreviation();
    }

}
