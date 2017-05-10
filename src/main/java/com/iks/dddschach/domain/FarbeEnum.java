package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Created by vollmer on 08.05.17.
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
