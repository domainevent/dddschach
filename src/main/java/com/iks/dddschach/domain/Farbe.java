package com.iks.dddschach.domain;

import com.iks.dddschach.domain.base.EnumObject;


/**
 * Farbe (schwarz, weiß) einer Schachfigur oder die eines Spielers
 */
public enum Farbe implements EnumObject<Farbe, Character> {

    /** weiß  */
    WEISS,
    /** schwarz */
    SCHWARZ;

    public Character marshal() {
        switch (this) {
            case WEISS: return 'w';
            case SCHWARZ: return 'b';
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }

    public Farbe unmarshal(Character encoded) {
        switch (encoded) {
            case 'w': return WEISS;
            case 'b': return SCHWARZ;
        }
        throw new IllegalArgumentException("Unexpected marshal character " + this);
    }

};
