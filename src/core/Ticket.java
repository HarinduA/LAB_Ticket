package src.core;



// Ticket.java
public class Ticket {
    private String id;
    private String priority;

    // Constructor
    public Ticket(String id, String priority) {
        this.id = id;
        this.priority = priority;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getPriority() {
        return priority;
    }
}

