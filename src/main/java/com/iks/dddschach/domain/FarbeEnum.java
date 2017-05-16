package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

    @JsonValue
    public Character abbreviation() {
        switch (this) {
            case WEISS: return 'w';
            case SCHWARZ: return 'b';
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }

    @JsonCreator
    public FarbeEnum fromAbbrev(Character c) {
        switch (c) {
            case 'w': return WEISS;
            case 'b': return SCHWARZ;
        }
        throw new IllegalArgumentException("Unexpected abbreviation character " + this);
    }

};
