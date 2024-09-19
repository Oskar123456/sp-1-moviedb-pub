package dk.obhnothing.persistence.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MBaseDTO
 */
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class MBaseDTO
{

      public Boolean adult;
      public String backdrop_path;
      public Integer[] genre_ids;
      public Integer id;
      public String original_language;
      public String original_title;
      public String overview;
      public Double popularity;
      public String poster_path;
      public LocalDate release_date;
      public String title;
      public Boolean video;
      public Double vote_average;
      public Integer vote_count;

}
