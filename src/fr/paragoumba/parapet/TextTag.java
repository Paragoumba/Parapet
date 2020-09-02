package fr.paragoumba.parapet;

public class TextTag extends AbstractTag {

    public TextTag(String text){

        super(text);

    }

    @Override
    public String toString(int indentation){

        return "\t".repeat(Math.max(0, indentation)) + tag;

    }
}
