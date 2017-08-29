package com.iks.dddschach.domain.base;

/**
 * Repräsentiert ein Entity Object mit Id, d.h. es enthält ein Id-Attribute
 * vom Typ <code>I</code>. Die Identity basiert ausschließlich auf dieser Id.
 */
public interface EntityIdObject<I> extends EntityObject {
    I getId();

    /*
     * Die Implementierungen von equals und hashCode sollte folgendermaßen gestaltet sein:
     * <pre>
     * @Override
     * public boolean equals(Object o) {
     *    if (this == o) return true;
     *    if (o == null || getClass() != o.getClass()) return false;
     *    EntityIdObject<?> that = (EntityIdObject<?>) o;
     *    return id.equals(that.id);
     * }
     *
     * @Override
     * public int hashCode() {
     *    return id.hashCode();
     * }
     */
}
