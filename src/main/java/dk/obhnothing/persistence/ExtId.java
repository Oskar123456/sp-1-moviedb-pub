package dk.obhnothing.persistence;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.ToString;

/**
 * UniId
 */
@MappedSuperclass
@ToString
public abstract class ExtId<T, U>
{
    @Id @NaturalId public U ext_id;
    @SuppressWarnings("unchecked") public T withUId(U extId) { this.ext_id = extId; return (T)this; }
}
