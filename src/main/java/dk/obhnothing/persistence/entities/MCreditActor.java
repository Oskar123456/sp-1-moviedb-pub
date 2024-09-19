package dk.obhnothing.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MCredit
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MCreditActor
{

    @Id @GeneratedValue public Integer id;
    public Integer order;
    public String character;
    /* RELATIONS */
    @ManyToOne public Person person;
    @ManyToOne public Movie movie;

}
