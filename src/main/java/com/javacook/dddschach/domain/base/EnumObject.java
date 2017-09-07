package com.javacook.dddschach.domain.base;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Enums sollten diese Interface implementieren.
 * @param <M> Klasse, in die die Enum-Literale transformiert werden (i.d.R. String)
 */
@XmlEnum
public interface EnumObject<M> {

    /**
     * Transformiert von der Domain-Bezeichnung (ubiquitäre Sprache) in die der technischen
     * Transportschicht (hier JSON). So kann die Schnittstelle unberührt bleiben, wenn sich
     * die Enum-Bezeichnungen ändern.
     * Jedes Enum muss dann im Client folgende statische Methode zum Unmarshallen
     * enthalten, wobei E für den Enum-Typ steht:
     * <pre>
     *   @JsonCreator
     *   static <E extends Enum> E unmarshal(M encoded);
     * </pre>
     * @return der kodierte Enum-Wert
     */
    @JsonValue
    M marshal();
}
