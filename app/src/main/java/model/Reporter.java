package model;

/**
 * Class representing the user that reports the incident.
 *
 * @author Lucas Karlsson.
 * @see AbstractReport
 */

public class Reporter {

    private double trustFactor;
    private String mail;

    /**
     * Constructor for Reporter-object and passes it.
     *
     * @param mail String
     */
    public Reporter(String mail) {
        this.trustFactor = 0;
        this.mail = mail;
    }

    /**
     * Increases the reporters trust factor through up votes.
     *
     * @param amount double
     */
    public void increaseTrustFactor(double amount) {
        this.trustFactor += amount;
    }

    public String getMail() {
        return this.mail;
    }

    public double getTrustFactor() {
        return this.trustFactor;
    }

}
