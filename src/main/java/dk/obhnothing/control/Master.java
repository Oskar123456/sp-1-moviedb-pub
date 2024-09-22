/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-02
 * -------------------
 */

package dk.obhnothing.control;

import com.sun.net.httpserver.HttpServer;

import dk.obhnothing.handling.Fileserver;

/**
 * master
 */
public class Master
{
    public static void Init(HttpServer server)
    {
        server.createContext("/", new Fileserver());
    }
}
