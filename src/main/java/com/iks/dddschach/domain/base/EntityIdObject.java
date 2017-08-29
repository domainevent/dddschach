package com.iks.dddschach.domain.base;

/**
 * Repräsentiert ein Entity Object mit Id, d.h. es enthält ein Id-Attribute
 * vom Typ <code>I</code>. Die Identity basiert ausschließlich auf dieser Id.
 */
public interface EntityIdObject<I> extends EntityObject {
    I getId();
}
