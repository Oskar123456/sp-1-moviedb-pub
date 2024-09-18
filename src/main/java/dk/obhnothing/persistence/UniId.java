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
public abstract class UniId<T, U>
{

    @Id @NaturalId public U id;
    @SuppressWarnings("unchecked") public T withUId(U uId) { this.id = uId; return (T)this; }
    public U getUId() { return id; };

}
