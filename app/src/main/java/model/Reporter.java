package model;

public class Reporter {

    private double trustFactor;
    private String mail;

    public Reporter(String mail){
        this.trustFactor = 0;
        this.mail = mail;
    }

    public void increaseTrustFactor(double amount){
        this.trustFactor += amount;
    }

    public String getMail() {
        return  this.mail;
    }

}
