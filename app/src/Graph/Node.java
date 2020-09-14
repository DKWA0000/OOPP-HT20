public class Node {

    String station;
    boolean alert;

    /*
        Station name and whether or not there has been an alert on this station.
        Alert is always set to false upon creation. 
     */
    Node(String station) {
        this.station = station;
        this.alert = false;
    }
}
