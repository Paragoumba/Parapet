package fr.paragoumba.parapet;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class HttpServer extends com.sun.net.httpserver.HttpServer {

    @Override
    public void bind(InetSocketAddress inetSocketAddress, int i) throws IOException {

    }

    @Override
    public void start(){

    }

    @Override
    public void setExecutor(Executor executor){

    }

    @Override
    public Executor getExecutor(){

        return null;
    }

    @Override
    public void stop(int i){

    }

    @Override
    public HttpContext createContext(String s, HttpHandler httpHandler){

        return null;
    }

    @Override
    public HttpContext createContext(String s){

        return null;
    }

    @Override
    public void removeContext(String s) throws IllegalArgumentException{

    }

    @Override
    public void removeContext(HttpContext httpContext){

    }

    @Override
    public InetSocketAddress getAddress(){

        return null;
    }
}
