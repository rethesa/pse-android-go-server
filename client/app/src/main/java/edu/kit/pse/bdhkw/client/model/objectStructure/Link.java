package edu.kit.pse.bdhkw.client.model.objectStructure;

/**
 * Created by Theresa on 13.01.2017.
 */

public class Link {

    private String url;
    private String groupName;
    private String secret;

    public Link(String url, String groupName, String secret) {
        this.url = url;
        this.groupName = groupName;
        this.secret = secret;
    }

    public String toString() {
        return this.url + "/"+ this.groupName + "/" + this.secret;
    }
}
