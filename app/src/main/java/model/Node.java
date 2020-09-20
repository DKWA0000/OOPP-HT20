package model;

public class Node {
        String station;
        Node(String station) {
            this.station = station;
        }

        @Override
        /*
            To make it easier to find our nodes.
        */
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((station == null) ? 0 : station.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (station == null) {
                if (other.station != null)
                    return false;
            } else if (!station.equals(other.station))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return station;
        }

        //TODO:
        private Graph getOuterType() {
            return new Graph();
        }

    }