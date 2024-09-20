package dk.obhnothing.persistence.dao;

import jakarta.persistence.EntityManagerFactory;

/**
 * OurDB
 */
public class OurDB
{

    private static EntityManagerFactory EMF;
    public static void Init(EntityManagerFactory e) { EMF = e; }



}
