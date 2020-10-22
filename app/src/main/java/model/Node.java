package model;

public class Node {
    /**
     * Name of the Node as well as the state of the node.
     *
     * @see #name
     * @see #state
     */
    private String name;
    private boolean state;

    public Node(String name) {
        this.name = name;
        this.state = false;
    }

    @Override
        /*
            To make it easier to find our nodes.
        */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Get the name of the Node.
     *
     * @return Name of the Node.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the current state of the Node.
     *
     * @return current state of the Node.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Set the current state of the Node.
     *
     * @param desiredState state to change into.
     */
    public void setState(boolean desiredState) {
        state = desiredState;
    }

}
