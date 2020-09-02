package fr.paragoumba.parapet.handlers;

import com.sun.net.httpserver.HttpExchange;
import fr.paragoumba.parapet.Parapet;
import fr.paragoumba.parapet.Settings;

import java.io.*;
import java.net.HttpURLConnection;

public class FileHandler extends Handler {

    public FileHandler(Settings settings){

        super(settings);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InputStream is = Parapet.class.getResourceAsStream(httpExchange.getRequestURI().getPath());

        if (is != null){

            try(BufferedInputStream bis = new BufferedInputStream(is)){

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                try(BufferedOutputStream out = new BufferedOutputStream(httpExchange.getResponseBody())){

                    out.write(bis.readAllBytes());
                    out.flush();

                    log(httpExchange, "Sent file.");

                } catch (Exception e){

                    e.printStackTrace();

                }
            }

            is.close();

        } else {

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);

        }
    }
}
