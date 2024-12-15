package src.threads;

import src.core.TicketPool;
import src.logging.Logger;

public class Vendor implements Runnable {
    protected final TicketPool ticketPool;
    private final int baseReleaseRate;
    private final String vendorType; // Add a field to specify the vendor type

    public Vendor(TicketPool ticketPool, int baseReleaseRate, String vendorType) {
        this.ticketPool = ticketPool;
        this.baseReleaseRate = baseReleaseRate;
        this.vendorType = vendorType;
    }

    // Method to get the adjusted release rate; overridden by subclasses
    protected int getAdjustedReleaseRate() {
        return baseReleaseRate;
    }

    @Override
    public void run() {
        int adjustedRate = getAdjustedReleaseRate();
        for (int i = 0; i < adjustedRate; i++) {
            String ticket = vendorType + "-Ticket-" + System.nanoTime();
            ticketPool.addTickets(ticket);
            Logger.log(vendorType + " added: " + ticket);

            try {
                Thread.sleep(500); // Delay between adding tickets
            } catch (InterruptedException e) {
                Logger.log(vendorType + " interrupted.");
                break;
            }
        }
    }
}
