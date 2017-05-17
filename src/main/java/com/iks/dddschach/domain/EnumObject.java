package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.xml.bind.annotation.XmlEnum;


/**
 * Created by vollmer on 17.05.17.
 */
@XmlEnum
public interface EnumObject<S, T> {

    @JsonValue
    T abbreviation();

    @JsonCreator
    S fromAbbrev(T c);

}
