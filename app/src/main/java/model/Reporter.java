package model;

/**
 * Class representing the user that reports the incident.
 *
 * @see AbstractReport
 *
 * @author: Lucas Karlsson.
 */

public class Reporter {

    private int trustFactor;
    private String mail;

    /**
     * Constructor for Reporter-object and passes it.
     *
     * @param mail String
     */
    public Reporter(String mail){
        this.trustFactor = 0;
        this.mail = mail;
    }

    /**
     * Increases the reporters trust factor through up votes.
     *
     * @param amount double
     */
    public void increaseTrustFactor(double amount){
        this.trustFactor += amount;
    }

    public int getTrustFactor() {
        return this.trustFactor;
    }

    public String getMail() {
        return  this.mail;
    }

}
