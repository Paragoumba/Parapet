package fr.paragoumba.parapet.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.paragoumba.parapet.Settings;

public abstract class Handler implements HttpHandler {

    public Handler(Settings settings){

        this.settings = settings;

    }

    protected final Settings settings;

    public void log(HttpExchange httpExchange, String s){

        System.out.println(httpExchange.getRemoteAddress().getHostName() + ": " + httpExchange.getRequestURI().getPath() + " " + s);

    }
}
