package fr.paragoumba.parapet;

public class Client {

    public Client(String hostname, String ip, String mac){

        this.hostname = hostname;
        this.ip = ip;
        this.mac = mac;

    }

    public final String hostname;
    public final String ip;
    public final String mac;

    public String toString(int indentation){

        String indentationStr = "\t".repeat(Math.max(0, indentation));

        return indentationStr + "{\n" +
                indentationStr + indentationStr + "\"hostname\": \"" + hostname + "\",\n" +
                indentationStr + indentationStr + "\"ip\": \"" + ip + "\",\n" +
                indentationStr + indentationStr + "\"mac\": \"" + mac + "\"\n" +
                indentationStr + '}';
    }
}
