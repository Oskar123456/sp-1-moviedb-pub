package dk.obhnothing;

import java.util.Locale;

import com.github.javafaker.Faker;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * ......obhnothing.dk
 * .........2024-09-10
 * -------------------
 */

public class App
{

    public static void main(String[] args) throws Exception
    {
        /* INIT */
        //HibernateConfig.Init(HibernateConfig.Mode.DEV, "cphdat-jdd2", "oskar", "2104");
        //EntityManagerFactory EMF = HibernateConfig.getEntityManagerFactory();

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

        String apikey = System.getenv("API_KEY");
        String apitoken = System.getenv("API_TOKEN");

        System.out.println(apikey);
        System.out.println(apitoken);

        //EMF.close();

    }

}























