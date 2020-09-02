package fr.paragoumba.parapet;

public class AutoClosingTag extends AbstractAttributeTag {

    public AutoClosingTag(String tag){

        this(tag, new Attribute[0]);

    }

    public AutoClosingTag(String tag, Attribute... varargs){

        super(tag, varargs);

    }

    @Override
    public String toString(int indentation){

        return "\t".repeat(indentation) + '<' + tag + attributesToString() + "/>\n";

    }
}
