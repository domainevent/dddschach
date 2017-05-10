package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlType;


@XmlType
public class ZugValueObject extends ValueObject {

    public final PositionValueObject from;
    public final PositionValueObject to;


    public ZugValueObject() {
        this((PositionValueObject)null, (PositionValueObject)null);
    }


    public ZugValueObject(PositionValueObject from, PositionValueObject to) {
        this.from = from;
        this.to = to;
    }

    public ZugValueObject(String from, String to) {
        this(new PositionValueObject(from), new PositionValueObject(to));
    }

    public ZugValueObject(String fromTo) {
        this(fromTo.split("-")[0], fromTo.split("-")[1]);
    }

    @Override
    public String toString() {
        return from + "-" + to;
    }
}
