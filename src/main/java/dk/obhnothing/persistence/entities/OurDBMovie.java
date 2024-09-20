package dk.obhnothing.persistence.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import dk.obhnothing.persistence.ExtId;
import dk.obhnothing.persistence.enums.OurDBStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Movie
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OurDBMovie extends ExtId<OurDBMovie, Integer>
{
    @Id @GeneratedValue public Integer id;
    public String backdrop_path;
    public String original_title;
    public String overview;
    public Double popularity;
    public String poster_path;
    public LocalDate release_date;
    public String title;
    public Double vote_average;
    public Integer vote_count;
    public Double budget;
    public String homepage;
    public Double revenue;
    public Duration runtime;
    public String tagline;
    @Enumerated(EnumType.STRING) public OurDBStatus status;
    /* ISO 2-letter codes*/
    public String original_language_iso_639_1;
    public Set<String> spoken_languages_iso_639_1;
    public Set<String> origin_country_iso_3166_1;
    public Set<String> production_countries_iso_3166_1;
    /* RELATIONS */
    @ManyToMany public Set<OurDBGenre> genres;
    @ManyToMany public Set<OurDBKeyword> keywords;
    @OneToMany public Set<OurDBCast> cast;
    @OneToMany public Set<OurDBCrew> crew;
    @ManyToMany public Set<OurDBCmp> production_companies;
    @OneToOne public OurDBColl collection;
}













