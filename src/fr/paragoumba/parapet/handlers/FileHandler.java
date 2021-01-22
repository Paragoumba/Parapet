package fr.paragoumba.parapet.handlers;

import com.sun.net.httpserver.HttpExchange;
import fr.paragoumba.parapet.Parapet;
import fr.paragoumba.parapet.Settings;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.zip.ZipFile;

public class FileHandler extends Handler {

    public FileHandler(Settings settings){

        super(settings);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestedPath = httpExchange.getRequestURI().getPath();
        String exePath = FileHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        if (exePath.endsWith(".jar") || exePath.endsWith(".zip")){

            ZipFile zipFile = new ZipFile(exePath);

            if (zipFile.getEntry(requestedPath).isDirectory()){

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
                return;

            }

        } else {

            File exeFile = new File(exePath);

            if (exeFile.isFile() || new File(exeFile, requestedPath).isDirectory()){

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
                return;

            }
        }

        InputStream is = Parapet.class.getResourceAsStream(requestedPath);

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
