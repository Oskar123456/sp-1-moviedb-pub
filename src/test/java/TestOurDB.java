import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dk.obhnothing.persistence.HibernateConfig;
import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.service.Mapping;
import dk.obhnothing.persistence.service.NetScrape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class TestOurDB
{

    static EntityManagerFactory EMF;
    static EntityManager EM;

    static String apikey = System.getenv("API_KEY");
    static String apitoken = System.getenv("API_TOKEN");
    static String dbname = System.getenv("DB_NAME");
    static String dbuser = System.getenv("DB_USER");
    static String dbpw = System.getenv("DB_PW");

    static OurDBMovie currentM = null;

    @BeforeAll
    static void Init()
    {
        HibernateConfig.Init(HibernateConfig.Mode.DEV, dbname, dbuser, dbpw);
        EMF = HibernateConfig.getEntityManagerFactory();
        NetScrape.Init(apitoken);
        OurDB.Init(EMF);
    }

    @AfterAll
    static void DeInit()
    {
        EM = EMF.createEntityManager();
        EM.getTransaction().begin();
        EM.createNativeQuery(dropTablesSQL).executeUpdate();
        EM.getTransaction().commit();
        EM.close();
        EMF.close();
    }

    @BeforeEach
    void Setup()
    {
    }

    @AfterEach
    void TearDown()
    {
    }

    @Test @DisplayName("Test database movie creation")
    void TestCreate()
    {
        tMDBFullDesc tmdbM = NetScrape.fetchDets(833339);
        OurDBMovie ourdbM  = Mapping.tMDBFullDesc_OurDBMovie(tmdbM);
        OurDBMovie storedM = OurDB.ourDBMovie_Create(ourdbM);

        assertEquals(ourdbM.tmdb_id, tmdbM.id, "assert fetched and mapped movie are the same DB%n");
        assertEquals(ourdbM.tmdb_id, storedM.tmdb_id, "assert fetched and stored movie are the same DB%n");

        currentM = storedM;
    }

    @Test @DisplayName("Test update movie")
    void TestUpdate()
    {
        tMDBFullDesc tmdbM = NetScrape.fetchDets(980026);
        OurDBMovie ourdbM  = Mapping.tMDBFullDesc_OurDBMovie(tmdbM);
        OurDBMovie storedM = OurDB.ourDBMovie_Create(ourdbM);

        OurDBMovie retM = OurDB.ourDBMovie_FindById(storedM.id);

        assertNotEquals(retM.original_title, "update title");

        retM.original_title = "update title";
        OurDB.ourDBMovie_Update(retM);
        retM = OurDB.ourDBMovie_FindById(storedM.id);

        assertEquals(retM.original_title, "update title");
    }

    @Test @DisplayName("Test database movie deletion")
    void TestDelete()
    {
        OurDBMovie retM = OurDB.ourDBMovie_FindById(currentM.id);

        assertNotNull(retM, String.format("assert %d (%s) exists in DB%n", currentM.id, currentM.original_title));

        boolean success = OurDB.ourDBMovie_DeleteById(currentM.id);

        assertTrue(success, String.format("assert %d (%s) deleted in DB%n", currentM.id, currentM.original_title));

        boolean successAgain = OurDB.ourDBMovie_DeleteById(currentM.id);

        assertFalse(successAgain, String.format("assert %d (%s) not deleted twice in DB%n", currentM.id, currentM.original_title));
        retM = OurDB.ourDBMovie_FindById(currentM.id);

        assertNull(retM, String.format("assert %d (%s) does not exists in DB%n", currentM.id, currentM.original_title));
    }

    static String dropTablesSQL = """
        drop table \"ourdbgenre\" cascade;
        drop table \"ourdbcmp\" cascade;
        drop table \"ourdbpers\" cascade;
        drop table \"ourdbcoll\" cascade;
        drop table \"ourdbmovie\" cascade;
        drop table \"ourdbcast\" cascade;
        drop table \"ourdbcrew\" cascade;
        drop table \"ourdbmovie_origin_country_iso_3166_1\" cascade;
        drop table \"ourdbmovie_ourdbcmp\" cascade;
        drop table \"ourdbmovie_ourdbgenre\" cascade;
        drop table \"ourdbkeyword\" cascade;
        drop table \"ourdbmovie_ourdbkeyword\" cascade;
        drop table \"ourdbmovie_production_countries_iso_3166_1\" cascade;
        drop table \"ourdbmovie_spoken_languages_iso_639_1\" cascade;""";

}
