package dk.obhnothing;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.javafaker.Faker;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.service.NetScrape;
import dk.obhnothing.persistence.service.NetScrape.SearchCriteria;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.time.format.DateTimeFormatter;

import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBGenre;
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
        List<OurDBMovie> allHorrorMovies = OurDB.ourDBMovie_FindByGenre(new OurDBGenre("horror", null));
        List<OurDBMovie> mWDir = OurDB.ourDBMovie_FindByDirector(4453);
        List<OurDBMovie> mWActor = OurDB.ourDBMovie_FindByActor(4467);

        for (OurDBMovie m : allHorrorMovies) {
        }

        for (OurDBMovie m : mWDir) {
        }

        for (OurDBMovie m : mWActor) {
        }

        EMF.close();

    }

}























