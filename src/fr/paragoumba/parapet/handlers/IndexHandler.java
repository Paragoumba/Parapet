package fr.paragoumba.parapet.handlers;

import com.sun.net.httpserver.HttpExchange;
import fr.paragoumba.parapet.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class IndexHandler extends Handler {

    public IndexHandler(Settings settings){

        super(settings);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Page page = new Page();

        Tag head = page.getHead();
        Tag body = page.getBody();

        head.addChild(new AutoClosingTag("meta", new Attribute("charset", "utf8")));

        Tag title = new Tag("title");

        title.addChild(new TextTag("This is a title."));

        head.addChild(title);
        head.addChild(new AutoClosingTag("link", new Attribute("rel", "stylesheet"), new Attribute("href", "/css/bootstrap.min.css")));
        head.addChild(new AutoClosingTag("link", new Attribute("rel", "stylesheet"), new Attribute("href", "/css/style.css")));

        Tag h1 = new Tag("h1");

        h1.addChild(new TextTag("This is a header."));

        body.addChild(h1);
        body.addChild(new AutoClosingTag("img", new Attribute("src", "/imgs/parapet.svg"), new Attribute("width", "192"), new Attribute("height", "192")));

        Tag ul = new Tag("ul", new Attribute("id", "clients"));

        body.addChild(ul);
        body.addChild(new Tag("script", new Attribute("src", "/js/script.js")));

        String html = page.toString();

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, html.length());

        try (OutputStream out = httpExchange.getResponseBody()) {

            out.write(html.getBytes());
            log(httpExchange, "Sent page.");

        }
    }
}
