package dk.obhnothing.persistence.dao;

import java.util.HashMap;
import java.util.Map;

import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCmp;
import dk.obhnothing.persistence.entities.OurDBColl;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.entities.OurDBPers;
import dk.obhnothing.persistence.service.Mapping;
import dk.obhnothing.persistence.service.NetScrape;
import jakarta.persistence.EntityGraph;
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
    public static OurDBMovie ourDBMovie_touch(OurDBMovie m)
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

    public static OurDBMovie ourDBMovie_findById(Integer id)
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
                OurDBMovie res = em.createQuery("select m from OurDBMovie m where ext_id = ?1", OurDBMovie.class).
                    setParameter(1, m.ext_id).getSingleResult();
                if (res != null)
                {
                    System.out.println("FAST PATH IN CREATE");
                    return m;
                }
            } catch (NoResultException ignore) {}

            if (m.genres != null) {
                for (OurDBGenre genre : m.genres) {
                    try {
                        OurDBGenre res = em.createQuery("select g from OurDBGenre g where name ilike ?1", OurDBGenre.class).
                            setParameter(1, genre.name).getSingleResult();
                        genre.id = res.id;
                    } catch (NoResultException e) {
                        em.persist(genre);
                    }
                }
            }

            if (m.keywords != null) {
                for (OurDBKeyword keyword : m.keywords) {
                    try {
                        OurDBKeyword res = em.createQuery("select k from OurDBKeyword k where name ilike ?1", OurDBKeyword.class).
                            setParameter(1, keyword.name).getSingleResult();
                        keyword.id = res.id;
                    } catch (NoResultException e) {
                        em.persist(keyword);
                    }
                }
            }

            if (m.production_companies != null) {
                for (OurDBCmp cmp : m.production_companies) {
                    try {
                        OurDBCmp res = em.createQuery("select c from OurDBCmp c where ext_id = ?1", OurDBCmp.class).
                            setParameter(1, cmp.ext_id).getSingleResult();
                        cmp.id = res.id;
                    } catch (NoResultException e) {
                        em.persist(cmp);
                    }
                }
            }

            if (m.cast != null) {
                for (OurDBCast cast : m.cast) {
                    try {
                        OurDBPers res = em.createQuery("select p from OurDBPers p where ext_id = ?1", OurDBPers.class).
                            setParameter(1, cast.person.ext_id).getSingleResult();
                        cast.person = res;
                    } catch (NoResultException e) {
                        tMDBPers tMDBp = NetScrape.fetchPerson(cast.person.ext_id);
                        OurDBPers ourP = Mapping.tMDBPers_OurDBPers(tMDBp);
                        em.persist(ourP);
                        cast.person = ourP;
                        cast.movie = m;
                    }
                }
            }

            if ( enableCrew && m.crew != null ) {
                for (OurDBCrew crew : m.crew) {
                    try {
                        OurDBPers res = em.createQuery("select p from OurDBPers p where ext_id = ?1", OurDBPers.class).
                            setParameter(1, crew.person.ext_id).getSingleResult();
                        crew.person = res;
                    } catch (NoResultException e) {
                        tMDBPers tMDBp = NetScrape.fetchPerson(crew.person.ext_id);
                        OurDBPers ourP = Mapping.tMDBPers_OurDBPers(tMDBp);
                        em.persist(ourP);
                        crew.person = ourP;
                        crew.movie = m;
                    }
                }
            }

            else
                m.crew = null;

            if (m.collection != null) {
                try {
                    OurDBColl res = em.createQuery("select p from OurDBColl p where ext_id = ?1", OurDBColl.class).
                        setParameter(1, m.collection.ext_id).getSingleResult();
                    m.collection = res;
                } catch (NoResultException e) {
                    em.persist(m.collection);
                }
            }

            em.persist(m);

            em.getTransaction().commit();
            return m;
        }

        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;

        }
    }
}















