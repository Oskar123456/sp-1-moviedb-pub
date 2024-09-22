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

        System.out.println("Working...");

        Locale loc = Locale.US;
        Locale[] locales = Locale.getAvailableLocales();
        System.out.println(locales.length);
        for (Locale locale : locales) {
            if (locale.getCountry().contains("DK"))
                loc = locale;
        }
        Faker nameGen = new Faker(loc);
        Random rng = new Random();

        System.err.println(loc.getCountry().toString());
        System.err.println(nameGen.name().fullName());
        /* TEST */

        System.out.println("env vars:");
        System.out.println(apikey);
        System.out.println(apitoken);
        System.out.println(dbname);
        System.out.println(dbuser);
        System.out.println(dbpw);
        System.out.println();

        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        //tMDBFullDesc deadpool = NetScrape.fetchDets(533535);
        //OurDBMovie deadpoolOur = Mapping.tMDBFullDesc_OurDBMovie(deadpool);

        //System.out.println("CREATING");
        //System.out.println("CREATING");
        //System.out.println("CREATING");

        //OurDB.ourDBMovie_Create(deadpoolOur);

        //System.out.println("FETCHING FROM DB");
        //System.out.println("FETCHING FROM DB");
        //System.out.println("FETCHING FROM DB");

        //OurDBMovie fromDB = OurDB.ourDBMovie_FindById(533535);

        //System.out.println("PRINTING");
        //System.out.println("PRINTING");
        //System.out.println("PRINTING");

        //fromDB = OurDB.ourDBMovie_Touch(fromDB);
        //System.out.println(jsonMapper.writeValueAsString(fromDB));

        OurDB.EnableCrew(true);
        int res = NetScrape.searchAndStore(
                SearchCriteria.builder().
                maxResults(1200).originCountry("DK").
                after(LocalDate.now().minusYears(5))
                .build());

        List<OurDBMovie> allMovies = OurDB.ourDBMovie_GetAll();
        List<OurDBGenre> allGenres = OurDB.ourDBGenre_GetAll();
        List<OurDBKeyword> allKeywords = OurDB.ourDBKeyword_GetAll();
        List<OurDBMovie> allHorrorMovies = OurDB.ourDBMovie_FindByGenre(new OurDBGenre("horror", null));
        List<OurDBMovie> mWDir = OurDB.ourDBMovie_FindByDirector(4453);
        List<OurDBMovie> mWActor = OurDB.ourDBMovie_FindByActor(4467);

        PrettyPrinter.withColor(" >>> Database stats:", PrettyPrinter.ANSIColorCode.ANSI_RED);
        System.out.println();
        PrettyPrinter.withColor(String.format("\tSize: %d%n", allMovies.size()), PrettyPrinter.ANSIColorCode.ANSI_RED);
        PrettyPrinter.withColor(String.format("\tGenres: %d%n", allGenres.size()), PrettyPrinter.ANSIColorCode.ANSI_RED);
        allGenres.stream().forEach(g -> System.out.printf("%s, ", g.name));
        System.out.println();
        PrettyPrinter.withColor(String.format("\tKeywords: %d%n", allKeywords.size()), PrettyPrinter.ANSIColorCode.ANSI_RED);
        //System.out.println(allKeywords.toString());
        PrettyPrinter.withColor(String.format(" >>> Printing contents:"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        System.out.println();

        PrettyPrinter.withColor(String.format(" >>> All horror movies in database:"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        System.out.println();
        for (OurDBMovie m : allHorrorMovies) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies directed by 'Thomas Vinterberg' in database:"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        System.out.println();
        for (OurDBMovie m : mWDir) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        PrettyPrinter.withColor(String.format(" >>> All movies with 'Martin Brygmann' in database:"), PrettyPrinter.ANSIColorCode.ANSI_RED);
        System.out.println();
        for (OurDBMovie m : mWActor) {
            System.out.println(PrettyPrinter.OurDBMovie_Print(m));
        }

        EMF.close();

    }

}























