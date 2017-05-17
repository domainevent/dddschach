package com.iks.dddschach.domain;

/**
 * Farbe (schwarz, weiß) einer Schachfigur oder die eines Spielers
 */
public enum Farbe implements EnumObject<Farbe, Character> {

    /** weiß  */
    WEISS,
    /** schwarz */
    SCHWARZ;

    public Character abbreviation() {
        switch (this) {
            case WEISS: return 'w';
            case SCHWARZ: return 'b';
            default:
                throw new IllegalArgumentException("Unexpected enum " + this);
        }
    }

    public Farbe fromAbbrev(Character c) {
        switch (c) {
            case 'w': return WEISS;
            case 'b': return SCHWARZ;
        }
        throw new IllegalArgumentException("Unexpected abbreviation character " + this);
    }

};
