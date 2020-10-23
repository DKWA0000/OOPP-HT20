package com.example.planka.model;

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

    /**
     * Method to get mail of a Reporter.
     * @return Mail of the reporter.
     */
    public String getMail() {
        return this.mail;
    }

    /**
     * Method to get the TrustFactor of a Reporter.
     *
     * @return TrustFactor of a Reporter.
     */
    public double getTrustFactor() {
        return this.trustFactor;
    }
}
