package dk.obhnothing.persistence.dao;

import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCmp;
import dk.obhnothing.persistence.entities.OurDBColl;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.entities.OurDBPers;
import dk.obhnothing.persistence.service.Mapping;
import dk.obhnothing.persistence.service.NetScrape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

/**
 * OurDB
 */
public class OurDB
{

    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) { EMF = e; }

    private static boolean enableCrew = false;
    public static void EnableCrew(boolean enable) { enableCrew = enable; }

    /**
     * Attempt to fetch lazy relations for OurDBMovie
     */
    @SuppressWarnings("unused")
	public static OurDBMovie ourDBMovie_Touch(OurDBMovie m)
    {
        if (m.id == null) {
            return null;
        }
        try (EntityManager em = EMF.createEntityManager()) {
            em.getTransaction().begin();
            m = em.find(OurDBMovie.class, m.id);
            m.production_companies.size();
            m.production_countries_iso_3166_1.size();
            m.origin_country_iso_3166_1.size();
            m.spoken_languages_iso_639_1.size();
            m.crew.size();
            m.cast.size();
            m.genres.size();
            m.keywords.size();
            OurDBColl done = m.collection;
            return m;
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static OurDBMovie ourDBMovie_FindById(Integer id)
    {
        try (EntityManager em = EMF.createEntityManager()) {
            em.getTransaction().begin();
            return em.createQuery("select m from OurDBMovie m where ext_id = ?1", OurDBMovie.class).setParameter(1, id).getSingleResult();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static OurDBMovie ourDBMovie_Create(OurDBMovie m)
    {
        /* check existence of relational elements */
        try (EntityManager em = EMF.createEntityManager())
        {
            em.getTransaction().begin();

            try {
                OurDBMovie res = em.createQuery("select m from OurDBMovie m where tmdb_id = ?1", OurDBMovie.class).
                    setParameter(1, m.tmdb_id).getSingleResult();
                if (res != null)
                {
                    System.out.printf("FAST PATH IN CREATE (%s)%n", m.title);
                    return m;
                }
            } catch (NoResultException ignore) {}

            if (m.genres != null) {
                for (OurDBGenre g : m.genres) {
                    if (em.find(OurDBGenre.class, g.name) == null)
                        em.persist(g);
                }
            }

            if (m.keywords != null) {
                for (OurDBKeyword k : m.keywords) {
                    if (em.find(OurDBKeyword.class, k.name) == null)
                        em.persist(k);
                }
            }

            System.out.println("prodcmps");
            if (m.production_companies != null) {
                for (OurDBCmp c : m.production_companies) {
                    if (em.find(OurDBCmp.class, c.ext_id) == null)
                        em.persist(c);
                    System.out.printf("%d, ", c.ext_id);
                }
            }

            if (m.cast != null) {
                for (OurDBCast c : m.cast) {
                    if (em.find(OurDBPers.class, c.person.ext_id) == null)
                        em.persist(Mapping.tMDBPers_OurDBPers(NetScrape.fetchPerson(c.person.ext_id)));
                    em.persist(c);
                }

            }

            if ( enableCrew && m.crew != null ) {
            }
            else
                m.crew = null;

            if (m.collection != null) {
                if (em.find(OurDBColl.class, m.collection.ext_id) == null)
                    em.persist(m.collection);
            }

            em.persist(m);

            em.getTransaction().commit();
            return m;
        }

        catch (Exception e) {
            System.err.println(e.getMessage());
            //e.printStackTrace();
            return null;

        }
    }
}















