/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-02
 * -------------------
 */

package dk.obhnothing.handling;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map.Entry;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * fileserver
 */
public class Fileserver implements HttpHandler
{

    @Override
    public void handle(HttpExchange exchange)
    {

        try {

            URI uri = exchange.getRequestURI();
            String uriStr = uri.getPath();
            String uriExt = (uriStr.lastIndexOf(".") > 0) ? uriStr.substring(uriStr.lastIndexOf(".") + 1) : "";
            String contentFormat = "";
            Headers reqHeaders = exchange.getRequestHeaders();
            for (Entry<String, List<String>> e : reqHeaders.entrySet()) {
                System.out.println(e.getKey());
            }

            ClassLoader cl = this.getClass().getClassLoader();
            InputStream fileStream = null;
            switch (uriExt.toLowerCase()) {
                case "html":
                    fileStream = cl.getResourceAsStream("html" + uriStr);
                    contentFormat = "text/html; charset=utf-8";
                    break;
                case "ico":
                    fileStream = cl.getResourceAsStream("images" + uriStr);
                    contentFormat = "png";
                    break;
                case "png":
                    fileStream = cl.getResourceAsStream("images" + uriStr);
                    break;
                case "jpg":
                    fileStream = cl.getResourceAsStream("images" + uriStr);
                    break;
                case "jpeg":
                    fileStream = cl.getResourceAsStream("images" + uriStr);
                    break;
                default:
                    break;
            }

            if (fileStream == null) {
                System.err.printf("(thread %d) couldnt find %s%n", Thread.currentThread().getId(), uri.getPath());
                return;
            }

            byte responseContent[] = fileStream.readAllBytes();
            Headers rHeaders = exchange.getResponseHeaders();
            rHeaders.add("Content-Type", contentFormat);
            exchange.sendResponseHeaders(200, responseContent.length);
            exchange.getResponseBody().write(responseContent);

            System.err.printf("(thread %d) served %s to %s%n",
                    Thread.currentThread().getId(), uri.getPath(), exchange.getRemoteAddress().toString());

            exchange.close();

        }

        catch (IOException e) {

            e.printStackTrace();

        }

    }

}

















