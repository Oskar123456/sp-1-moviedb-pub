import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dk.obhnothing.persistence.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * TestOurDB
 * 2024-09-22
 */
public class TestOurDB
{

    static EntityManagerFactory EMF;
    static EntityManager EM;

    static String dbname = "sp-1-test";
    static String dbuser = "oskar";
    static String dbpw   = "2104";

    @BeforeAll
    static void Init()
    {
        HibernateConfig.Init(HibernateConfig.Mode.DEV, dbname, dbuser, dbpw);
        EMF = HibernateConfig.getEntityManagerFactory();
    }

    @Test @DisplayName("Test database movie creation")
    void TestCreate()
    {
        assertEquals(0, 0);
    }

}























