package dk.obhnothing.persistence.entities;

import java.util.Set;

import dk.obhnothing.persistence.UniId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Language
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OurDBLang extends UniId<OurDBLang, String>
{
    public String english_name;
    public String name;
    /* RELATIONS */
    @ManyToMany public Set<OurDBMovie> moviesspoken;
    @OneToMany public OurDBMovie moviesorig;
}
