package com.iks.dddschach.domain;

import javax.xml.bind.annotation.XmlType;
import java.util.UUID;


/**
 * Eine universelle (weltweit) eindeutige Id eines Schachspiels.
 */
@XmlType
public class SpielId extends ValueObject {

    public final String id;


    public SpielId(String id) {
        this.id = id;
    }

    public SpielId() {
        this(UUID.randomUUID().toString());
    }


    @Override
    public String toString() {
        return id;
    }

}
