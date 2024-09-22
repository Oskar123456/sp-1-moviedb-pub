package dk.obhnothing.persistence;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCmp;
import dk.obhnothing.persistence.entities.OurDBColl;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.entities.OurDBPers;
import jakarta.persistence.EntityManagerFactory;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class HibernateConfig {
    private static EntityManagerFactory emf;
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null)
            throw new RuntimeException("No EntityManagerFactory Instance");
        return emf;
    }

    private static void getAnnotationConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(OurDBPers.class);
        configuration.addAnnotatedClass(OurDBCast.class);
        configuration.addAnnotatedClass(OurDBCrew.class);
        configuration.addAnnotatedClass(OurDBColl.class);
        configuration.addAnnotatedClass(OurDBCmp.class);
        configuration.addAnnotatedClass(OurDBGenre.class);
        configuration.addAnnotatedClass(OurDBKeyword.class);
        configuration.addAnnotatedClass(OurDBMovie.class);
    }

    public static void Init(Mode mode, String dbName, String uname, String pwd) {
        try {
            Configuration configuration = new Configuration();
            Properties props = new Properties();

            setBaseProperties(props, dbName, uname, pwd);

            configuration.setProperties(props);
            getAnnotationConfiguration(configuration);

            ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
            SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
            emf = sf.unwrap(EntityManagerFactory.class);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Properties setBaseProperties(Properties props, String dbName, String uname, String pwd){
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.current_session_context_class", "thread");
        props.put("hibernate.show_sql", "false");
        props.put("hibernate.format_sql", "true");
        props.put("hibernate.use_sql_comments", "true");
        props.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/" + dbName);
        props.put("hibernate.connection.username", uname);
        props.put("hibernate.connection.password", pwd);
        return props;
    }

    public enum Mode {
        DEV, TEST, DEPLOY
    }

}

