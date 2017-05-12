package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Farbe (schwarz, weiß) einer Schachfigur oder die eines Spielers
 */
@XmlEnum
public enum FarbeEnum {
    /** weiß  */
    WEISS,
    /** schwarz */
    SCHWARZ;

    public Character abbreviation() {
        switch (this) {
            case WEISS: return 'w';
            case SCHWARZ: return 's';
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }


    /**
     * Liefert die jeweils andere Farbe
     */
    public FarbeEnum swap() {
        switch (this) {
            case WEISS: return SCHWARZ;
            case SCHWARZ: return WEISS;
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }
};
