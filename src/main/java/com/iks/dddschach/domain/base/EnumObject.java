package com.iks.dddschach.domain.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;


@XmlEnum
public interface EnumObject<E, M> {

    /**
     * Transformiert von der Domain-Bezeichnung (ubiquitäre Sprache) in die der technischen
     * Transportschicht (hier JSON)
     * @return der kodierte Wert
     */
    @JsonValue
    M marshal();

    /**
     * Inverse Operation zu <code>marshal</code>
     * @param encoded
     * @return
     */
    @JsonCreator
    E unmarshal(M encoded);

}
