package fr.paragoumba.parapet;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import fr.paragoumba.parapet.handlers.FileHandler;
import fr.paragoumba.parapet.handlers.IndexHandler;
import fr.paragoumba.parapet.handlers.api.ClientsHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Parapet {

    public static void main(String[] args){

        String apInterface = null;

        try (Scanner scanner = new Scanner(new FileInputStream("/etc/hostapd/hostapd.conf"))){

            while (scanner.hasNext()){

                String line = scanner.nextLine();

                if (line.startsWith("interface=")){

                    String[] splitLine = line.split("=");

                    if (splitLine[1].length() > 0){

                        apInterface = splitLine[1];

                    }

                    break;

                }
            }

        } catch (FileNotFoundException ignored){}

        if (apInterface == null){

            System.err.println("Could not determine AP interface. Is hostapd installed?.");

        }

        Settings settings = new Settings(apInterface);

        try {

            HttpServer server = HttpServer.create(new InetSocketAddress((InetAddress) null, 8080), 0);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));

            //server.setHttpsConfigurator(new HttpsConfigurator(SSLContext.getInstance("TLS")));
            server.createContext("/", new IndexHandler(settings));
            server.createContext("/api/clients", new ClientsHandler(settings));

            HttpHandler readFileHandler = new FileHandler(settings);

            server.createContext("/css", readFileHandler);
            server.createContext("/js", httpExchange -> {

                httpExchange.getResponseHeaders().add("Content-Type", "application/javascript");

                readFileHandler.handle(httpExchange);

            });
            server.createContext("/imgs", readFileHandler);

            server.start();

        } catch (Exception e){

            e.printStackTrace();

        }
    }
}
