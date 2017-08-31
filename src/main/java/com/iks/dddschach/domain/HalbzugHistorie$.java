package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iks.dddschach.domain.base.ValueObject;

import java.util.List;
import java.util.stream.Collectors;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = HalbzugHistorie$.class) })
public class HalbzugHistorie$ extends HalbzugHistorie implements ValueObject {

    public HalbzugHistorie$() {
    }


    public HalbzugHistorie$(List<Halbzug> halbzuege) {
        super(halbzuege);
    }


    public List<Halbzug$> getHalbzuege$() {
        return super.getHalbzuege().stream().map(h -> (Halbzug$)h).collect(Collectors.toList());
    }
}
