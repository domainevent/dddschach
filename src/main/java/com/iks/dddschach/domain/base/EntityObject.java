package com.iks.dddschach.domain.base;

import javax.xml.bind.annotation.XmlType;


/**
 * Created by vollmer on 17.05.17.
 */
@XmlType
public abstract class EntityObject<I> {

    protected final I id;

    public EntityObject() {
        this(null);
        throw new IllegalStateException("This constructor must not be called");
    }

    protected EntityObject(I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityObject<?> that = (EntityObject<?>) o;
        return id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
