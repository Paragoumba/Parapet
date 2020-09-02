package fr.paragoumba.parapet;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAttributeTag extends AbstractTag {

    public AbstractAttributeTag(String tag, Attribute... varargs){

        super(tag);

        args = new HashMap<>();

        for (Attribute attribute : varargs){

            args.put(attribute.key, attribute.value);

        }
    }

    protected Map<String, String> args;

    public void addAttribute(String arg, String value){

        args.put(arg, value);

    }

    public String attributesToString(){

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> arg : args.entrySet()){

            builder.append(' ').append(arg.getKey()).append("=\"").append(arg.getValue()).append('"');

        }

        return builder.toString();

    }
}
