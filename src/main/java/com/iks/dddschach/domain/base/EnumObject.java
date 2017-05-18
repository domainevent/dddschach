package com.iks.dddschach.domain.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;


@XmlEnum
public interface EnumObject<E, M> {

    /**
     * Transformiert von der Domain-Bezeichnung (ubiquitäre Sprache) in die der technischen
     * Transportschicht (hier JSON). Jedes Enum sollte (zumindest im Client) folgende
     * statische Methode zum Unmarshallen enthalten:
     * <pre>
     *   @JsonCreator
     *   static <E> E unmarshal(M encoded);
     * </pre>
     * @return der kodierte Enum-Wert
     */
    @JsonValue
    M marshal();

}
