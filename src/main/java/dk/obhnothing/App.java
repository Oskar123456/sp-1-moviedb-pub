package dk.obhnothing;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.service.NetScrape;
import dk.obhnothing.persistence.service.NetScrape.SearchCriteria;
import dk.obhnothing.utilities.PrettyPrinter;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBPers;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBMovie;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class App
{

    static String apikey = System.getenv("API_KEY");
    static String apitoken = System.getenv("API_TOKEN");
    static String dbname = System.getenv("DB_NAME");
    static String dbuser = System.getenv("DB_USER");
    static String dbpw = System.getenv("DB_PW");
    static EntityManagerFactory EMF;

    public static void main(String[] args) throws Exception
    {
        /* INIT */
        HibernateConfig.Init(HibernateConfig.Mode.DEV, dbname, dbuser, dbpw);
        EMF = HibernateConfig.getEntityManagerFactory();

        NetScrape.Init(apitoken);
        OurDB.Init(EMF);

        /* TEST */
        System.out.printf("%n%n%n");

        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        OurDB.EnableCrew(true);
        int res = NetScrape.searchAndStore(
                SearchCriteria.builder().
                maxResults(1200).originCountry("DK").
                after(LocalDate.now().minusYears(5))
                .build());

        List<OurDBMovie> allMovies = OurDB.ourDBMovie_GetAll();
        List<OurDBPers> allPers = OurDB.ourDBPers_GetAll();
        List<OurDBGenre> allGenres = OurDB.ourDBGenre_GetAll();
        List<OurDBKeyword> allKeywords = OurDB.ourDBKeyword_GetAll();
        List<OurDBMovie> allHorrorMovies = OurDB.ourDBMovie_FindByGenre(new OurDBGenre("horror", null));
        List<OurDBMovie> mWDir = OurDB.ourDBMovie_FindByDirector(4453);
        List<OurDBMovie> mWActor = OurDB.ourDBMovie_FindByActor(4467);
        List<OurDBMovie> mWName = OurDB.ourDBMovie_FindByName("druk");
        List<OurDBMovie> mWNameVand = OurDB.ourDBMovie_FindByName("vand");
        Double avgRating = OurDB.ourDBMovie_GetAvgRating();

        PrettyPrinter.withColor(String.format(" >>> Database stats:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        PrettyPrinter.withColor(String.format("\tSize: %d movies, %d people%n", allMovies.size(), allPers.size()),  PrettyPrinter.ANSIColorCode.ANSI_RED);
        PrettyPrinter.withColor(String.format("\tGenres: %d", allGenres.size()), PrettyPrinter.ANSIColorCode.ANSI_RED);
        allGenres.stream().forEach(g -> System.out.printf("%n\t\t%s", g.name));
        PrettyPrinter.withColor(String.format("%n\tKeywords: %d%n", allKeywords.size()), PrettyPrinter.ANSIColorCode.ANSI_RED);
        PrettyPrinter.withColor(String.format(" >>> Printing contents:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);

        PrettyPrinter.withColor(String.format(" >>> All horror movies in database:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        for (OurDBMovie m : allHorrorMovies) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies directed by 'Thomas Vinterberg' in database:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        for (OurDBMovie m : mWDir) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies with 'Martin Brygmann' in database:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        for (OurDBMovie m : mWActor) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies with 'druk' in name in database:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        for (OurDBMovie m : mWName) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies with 'vand' in name in database:%n"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        for (OurDBMovie m : mWNameVand) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> Average rating (0 - 10) of movies in database: %s%n", avgRating.toString()), PrettyPrinter.ANSIColorCode.ANSI_RED);

        System.out.printf("%n%n%n");

        EMF.close();

    }

}























