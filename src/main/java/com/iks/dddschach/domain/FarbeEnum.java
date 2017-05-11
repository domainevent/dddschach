package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Farbe einer Schachfigur oder die eines Spielers
 */
@XmlEnum
public enum FarbeEnum {
    WEISS, SCHWARZ;

    public Character abbreviation() {
        switch (this) {
            case WEISS: return 'w';
            case SCHWARZ: return 's';
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }

    public FarbeEnum swap() {
        switch (this) {
            case WEISS: return SCHWARZ;
            case SCHWARZ: return WEISS;
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }
};
