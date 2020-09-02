package fr.paragoumba.parapet;

import java.util.ArrayList;
import java.util.List;

public class Tag extends AbstractAttributeTag {

    public Tag(String tag){

        this(tag, new Attribute[0]);

    }

    public Tag(String tag, Attribute... varargs){

        super(tag, varargs);

        children = new ArrayList<>();

    }

    private final List<AbstractTag> children;

    public void addChild(AbstractTag tag){

        children.add(tag);

    }

    public String toString(int indentation){

        StringBuilder builder = new StringBuilder();
        String indentationStr = "\t".repeat(Math.max(0, indentation));

        builder.append(indentationStr).append('<').append(tag).append(attributesToString()).append(">\n");

        for (AbstractTag child : children){

            builder.append(child.toString(indentation + 1));

        }

        builder.append(indentationStr);
        builder.append("</").append(tag).append(">\n");

        return builder.toString();

    }
}
