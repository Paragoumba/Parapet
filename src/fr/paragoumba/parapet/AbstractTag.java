package fr.paragoumba.parapet;

public abstract class AbstractTag {

    public AbstractTag(String tag){

        this.tag = tag;

    }

    protected String tag;

    public abstract String toString(int indentation);

}
