package src.threads;

import src.core.TicketPool;
import src.logging.Logger;

public class StatisticsReporter implements Runnable {
    private final TicketPool ticketPool;
    private boolean running = true;

    public StatisticsReporter(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (ticketPool) {
                // Fetch statistics
                int totalAdded = ticketPool.getTotalTicketsAdded();
                int totalRemoved = ticketPool.getTotalTicketsRemoved();
                int currentTickets = ticketPool.getTicketCount();

                // Log statistics
                Logger.log("Statistics Report: " +
                        "Total Added: " + totalAdded + ", " +
                        "Total Removed: " + totalRemoved + ", " +
                        "Current Tickets: " + currentTickets);
            }

            try {
                Thread.sleep(5000); // Sleep for 5 seconds
            } catch (InterruptedException e) {
                Logger.log("StatisticsReporter interrupted.");
                Thread.currentThread().interrupt(); // Reset interrupted status
                break;
            }
        }
    }

    // Stop the reporter gracefully
    public void stopReporter() {
        running = false;
    }
}
