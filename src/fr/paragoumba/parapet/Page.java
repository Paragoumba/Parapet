package fr.paragoumba.parapet;

public class Page {

    public Page(){

        head = new Tag("head");
        body = new Tag("body");

    }

    private final Tag head;
    private final Tag body;

    public Tag getHead(){

        return head;

    }

    public Tag getBody(){

        return body;

    }

    @Override
    public String toString(){

        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                head.toString(1) +
                body.toString(1) +
                "</html>";

    }
}
