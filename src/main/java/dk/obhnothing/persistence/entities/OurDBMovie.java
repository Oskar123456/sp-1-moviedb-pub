package dk.obhnothing.persistence.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import dk.obhnothing.persistence.enums.OurDBStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Movie
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OurDBMovie
{
    @Id @GeneratedValue public Integer id;
    public Integer tmdb_id;
    public String backdrop_path;
    public String original_title;
    @Column(columnDefinition="TEXT") public String overview;
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
    /* ISO 2-letter codes */
    public String original_language_iso_639_1;
    @ElementCollection() public Set<String> spoken_languages_iso_639_1;
    @ElementCollection() public Set<String> origin_country_iso_3166_1;
    @ElementCollection() public Set<String> production_countries_iso_3166_1;
    /* RELATIONS */
    @ManyToMany() @JoinTable(name = "OurDBMovie_OurDBGenre") public Set<OurDBGenre> genres;
    @ManyToMany() @JoinTable(name = "OurDBMovie_OurDBKeyword") public Set<OurDBKeyword> keywords;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie") public Set<OurDBCast> cast;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie") public Set<OurDBCrew> crew;
    @ManyToMany() @JoinTable(name = "OurDBMovie_OurDBCmp") public Set<OurDBCmp> production_companies;
    @ManyToOne() public OurDBColl collection;
}













