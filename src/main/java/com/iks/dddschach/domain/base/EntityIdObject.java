package com.iks.dddschach.domain.base;

/**
 * Repräsentiert ein Entity Object mit Id, d.h. es enthält ein Id-Attribute
 * vom Typ <code>I</code>. Die Identity basiert ausschließlich auf dieser Id.
 */
public abstract class EntityIdObject<I> extends EntityObject {

    protected final I id;

    protected EntityIdObject(I id) {
        this.id = id;
    }

    public I getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityIdObject<?> that = (EntityIdObject<?>) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
