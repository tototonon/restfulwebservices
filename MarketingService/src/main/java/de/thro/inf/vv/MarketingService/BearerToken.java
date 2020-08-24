package de.thro.inf.vv.MarketingService;

import java.io.Serializable;

/**
 * @author Timon Tonon
 * Generate BearerToken.
 */
public class BearerToken implements Serializable {
    private final String username;
    private final String password;

    public BearerToken() {
        this.username = "sWIFtitono";
        this.password = "vvSS20";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
