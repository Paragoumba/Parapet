package fr.paragoumba.parapet.handlers.api;

import com.sun.net.httpserver.HttpExchange;
import fr.paragoumba.parapet.Client;
import fr.paragoumba.parapet.Settings;
import fr.paragoumba.parapet.handlers.Handler;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientsHandler extends Handler {

    public ClientsHandler(Settings settings){

        super(settings);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        httpExchange.getResponseHeaders().add("Content-Type", "application/json");

        Process whichHostapd = runtime.exec("which hostapd");

        try {

            int exitValue = whichHostapd.waitFor();

            if (exitValue == 0 && settings.apInterface != null){

                Process listClients = runtime.exec("iw dev " + settings.apInterface + " station dump");

                try (OutputStream out = httpExchange.getResponseBody()) {

                    List<String> clientsMacs = new ArrayList<>();

                    try (Scanner in = new Scanner(listClients.getInputStream())) {

                        while (in.hasNext()){

                            String line = in.nextLine();

                            // Station 34:e1:2d:cf:cc:3b (on wlan1)

                            if (!line.startsWith("\t")){

                                String macAddress = line.split(" ")[1];

                                clientsMacs.add(macAddress);

                            }
                        }
                    }

                    List<Client> clients = new ArrayList<>();

                    try (Scanner in = new Scanner(new FileInputStream("/var/lib/misc/dnsmasq.leases"))) {

                        while (in.hasNext()){

                            String[] line = in.nextLine().split(" ");

                            if (clientsMacs.contains(line[1])){

                                StringBuilder hostnameBuilder = new StringBuilder();

                                for (int i = 3; i < line.length - 1; ++i){

                                    hostnameBuilder.append(line[i]);

                                    if (i != line.length - 2){

                                        hostnameBuilder.append(' ');

                                    }
                                }

                                clients.add(new Client(hostnameBuilder.toString(), line[2], line[1]));

                            }
                        }
                    }

                    StringBuilder builder = new StringBuilder();

                    builder.append("{\"clients\": [\n");

                    for (Client client : clients){

                        builder.append(client.toString(1)).append(", ");

                    }

                    builder.delete(builder.length() - 2, builder.length());
                    builder.append("]}");

                    httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, builder.length());

                    log(httpExchange, builder.toString());
                    out.write(builder.toString().getBytes());
                    out.flush();

                } catch (Exception e){

                    e.printStackTrace();

                }

            } else {

                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAVAILABLE, 0);

                try(OutputStream out = httpExchange.getResponseBody()) {

                    out.write("{\"error\": \"This service is unavailable.\"}".getBytes());
                    log(httpExchange, "This service is unavailable (no hostapd).");

                }
            }

            whichHostapd.destroy();
            httpExchange.close();

        } catch (Exception e){

            e.printStackTrace();

        }
    }
}
