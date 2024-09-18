package dk.obhnothing;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dto.CreditActorDTO;
import dk.obhnothing.persistence.dto.MBaseDTO;
import dk.obhnothing.persistence.dto.MDetailsDTO;
import dk.obhnothing.persistence.dto.PersonDTO;
import dk.obhnothing.persistence.entities.Company;
import dk.obhnothing.persistence.service.MService;
import jakarta.persistence.EntityManagerFactory;

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

        System.out.println("Working...");

        Locale loc = Locale.US;
        Locale[] locales = Locale.getAvailableLocales();
        System.out.println(locales.length);
        for (Locale locale : locales) {
            if (locale.getCountry().contains("DK"))
                loc = locale;
        }
        Faker nameGen = new Faker(loc);

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

        int pages = 1;
        MService.SearchCriteria sc = MService.SearchCriteria.builder().pageIndex(1).pageTotal(pages).build();
        List<MBaseDTO> res = MService.fetch(sc, apitoken);
        System.out.printf("Got %d in %d page(s):%n%n", res.size(), pages);
        for (MBaseDTO mBaseDTO : res) {
            System.out.println(mBaseDTO.original_title);
        }

        List<CreditActorDTO> creditDTOs = MService.fetchActorCreds(533535, apitoken);
        System.out.printf("Got %d creds:%n%n", creditDTOs.size());
        for (CreditActorDTO creditDTO : creditDTOs) {
            System.out.println(creditDTO.name);
        }

        MDetailsDTO detailsDTO = MService.fetchDets(533535, apitoken);
        System.out.printf("Details (%d credits):%n%n", detailsDTO.credits.cast.length + detailsDTO.credits.crew.length);
        System.out.println(detailsDTO.original_title);
        System.out.println(Arrays.toString(detailsDTO.keywords.keywords));

        int pId = 10859;
        PersonDTO personDTO = MService.fetchPerson(pId, apitoken);
        System.out.printf("Person details (%d):%n%n", pId);
        System.out.println(personDTO);

        Company com = new Company().withUId(3);
        System.out.println(com);

        EMF.close();

    }

}























