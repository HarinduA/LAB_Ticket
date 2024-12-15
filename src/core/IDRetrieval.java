package src.core;

public class IDRetrieval implements TicketRetrievalStrategy {
    private final String targetTicketID;

    public IDRetrieval(String targetTicketID) {
        this.targetTicketID = targetTicketID;
    }

    @Override
    public String retrieveTicket(TicketPool ticketPool) {
        synchronized (ticketPool) {
            for (String ticket : ticketPool.getTickets()) {
                if (ticket.equals(targetTicketID)) {
                    ticketPool.getTickets().remove(ticket);
                    return ticket;
                }
            }
        }
        return null; // Ticket not found
    }
}
