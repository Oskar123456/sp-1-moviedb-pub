package dk.obhnothing.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.ToString;

/**
 * UniId
 */
@MappedSuperclass
@ToString
public abstract class ExtId<T, U>
{
    @Column(unique = true) public U ext_id;
    @SuppressWarnings("unchecked") public T withUId(U extId) { this.ext_id = extId; return (T)this; }
}
