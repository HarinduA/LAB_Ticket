package src.threads;

import src.core.AbstractTicketHandler;
import src.core.TicketPool;
import src.core.TicketRetrievalStrategy;
import src.logging.Logger;

/**
 * Represents a customer that retrieves tickets based on a strategy.
 */
public class Customer extends AbstractTicketHandler implements Runnable {
    private final TicketRetrievalStrategy strategy;
    private boolean running = true;

    public Customer(TicketPool ticketPool, TicketRetrievalStrategy strategy) {
        super(ticketPool);
        this.strategy = strategy;
    }

    @Override
    public void run() {
        while (running) {
            String ticket = strategy.retrieveTicket(ticketPool);
            if (ticket != null) {
                Logger.log("Customer retrieved: " + ticket);
            } else {
                Logger.log("No tickets available for retrieval.");
                break;
            }
            try {
                // Simulate time between ticket retrievals
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // If the thread is interrupted, log the message and exit the loop
                Logger.log("Customer interrupted.");
                Thread.currentThread().interrupt(); // Properly propagate the interrupt
                break;
            }
        }
    }

    @Override
    public void handleTickets() {
        run(); // Delegate the ticket handling to the run method (thread's entry point)
    }

    // Method to stop the customer gracefully
    public void stopCustomer() {
        running = false;
        Thread.interrupted(); // Clear the interrupt status
    }
}
