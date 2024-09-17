package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Locale;
import java.util.Locale.IsoCountryCode;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dto.MBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MService
 */
public class MService
{

    private static String baseurl = "https://api.themoviedb.org/3/discover/movie?";

    public static MBaseDTO[] fetch(SearchCriteria sc)
    {
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
            uri(buildURI(sc)).GET().build();
        System.err.println("fetching: " + request.uri().toString());
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String responseStr = response.body();
        MResults results = jsonMapper.readValue(responseStr, MResults.class);

    }

    private static URI buildURI(SearchCriteria sc)
    {
        String uriStr = baseurl;
        uriStr += "page=" + sc.pageIndex.toString();
        uriStr += "include_adult=" + sc.includeAdult.toString();
        uriStr += "&include_video=" + sc.includeVideo.toString();
        uriStr += "&language=" + sc.language;
        uriStr += "&sort_by=" + sc.sortingCriteria + ((sc.sortAsc) ? ".asc" : ".desc");
        uriStr += "&with_origin_country=" + sc.originCountry;
        return URI.create(uriStr);
    }

    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SearchCriteria
    {

        public Boolean includeAdult;
        public Boolean includeVideo;
        public String language;
        public String originCountry;
        public String sortingCriteria;
        public Boolean sortAsc;
        public Integer pageIndex;
        public Integer pageNumber;

    }


}

/**
 * MResults
 */
@EqualsAndHashCode
class MResults
{

    public Integer page;
    public Integer total_pages;
    public Integer total_results;
    public MBaseDTO[] results;

}





















