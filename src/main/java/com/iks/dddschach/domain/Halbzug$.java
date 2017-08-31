package com.iks.dddschach.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.iks.dddschach.domain.base.ValueObject;
import java.text.ParseException;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class")
@JsonSubTypes({ @JsonSubTypes.Type(value = Halbzug$.class) })
public class Halbzug$ extends Halbzug implements ValueObject {

    public Halbzug$() {
        super();
    }

    public Halbzug$(Position$ von, Position$ nach) {
        super(von, nach);
    }

    public Halbzug$(Halbzug$ halbzug) {
        this((Position$)halbzug.von, (Position$)halbzug.nach);
    }

    public Halbzug$(String vonNach) throws ParseException {
        this(SpielNotationParser.parse(vonNach));
    }

    @Override
    public Position$ getVon() {
        return (Position$)super.getVon();
    }

    @Override
    public Position$ getNach() {
        return (Position$)super.getNach();
    }

}
