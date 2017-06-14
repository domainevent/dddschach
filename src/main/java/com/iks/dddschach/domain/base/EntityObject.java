package com.iks.dddschach.domain.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlType;


/**
 * Marker für Entity Objects. Nicht jedes Entity Object besitzt eine explizite Id.
 */
@XmlType
public abstract class EntityObject {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
