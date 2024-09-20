package dk.obhnothing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * MCredit
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBCast
{
    @Id @GeneratedValue public Integer id;
    public Integer order_of_appearance;
    public String character;
    /* RELATIONS */
    @JsonIgnore @Exclude @ManyToOne public OurDBPers person;
    @JsonIgnore @Exclude @ManyToOne public OurDBMovie movie;
}
