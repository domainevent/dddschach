package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlType;


@XmlType
public class HalbzugValueObject extends ValueObject {

    public final PositionValueObject from;
    public final PositionValueObject to;


    public HalbzugValueObject() {
        this((PositionValueObject)null, (PositionValueObject)null);
    }


    public HalbzugValueObject(PositionValueObject from, PositionValueObject to) {
        this.from = from;
        this.to = to;
    }

    public HalbzugValueObject(String from, String to) {
        this(new PositionValueObject(from), new PositionValueObject(to));
    }

    public HalbzugValueObject(String fromTo) {
        this(fromTo.split("-")[0], fromTo.split("-")[1]);
    }

    @Override
    public String toString() {
        return from + "-" + to;
    }
}
