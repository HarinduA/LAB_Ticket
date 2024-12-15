package src.main;

import src.core.*;
import src.threads.Customer;
import src.threads.FastVendor;
import src.threads.SlowVendor;

public class Main {
    public static void main(String[] args) {
        TicketPool ticketPool = new TicketPool();

        // Add initial tickets to the pool
        ticketPool.addTickets("Ticket-1");
        ticketPool.addTickets("Ticket-2");
        ticketPool.addTickets("VIP-Ticket");
        ticketPool.addTickets("Ticket-3");

        // Create Vendors
        int baseReleaseRate = 5; // Base ticket release rate for vendors
        Thread fastVendor = new Thread(new FastVendor(ticketPool, baseReleaseRate)); // Double rate
        Thread slowVendor = new Thread(new SlowVendor(ticketPool, baseReleaseRate)); // Half rate

        // Create Customers
        // Customer 1: PriorityRetrieval (FIFO)
        TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval();
        Thread customer1 = new Thread(new Customer(ticketPool, priorityStrategy));

        // Customer 2: IDRetrieval for a specific ticket
        TicketRetrievalStrategy idStrategy = new IDRetrieval("VIP-Ticket");
        Thread customer2 = new Thread(new Customer(ticketPool, idStrategy));

        // Start Vendors
        fastVendor.start();
        slowVendor.start();

        // Start Customers
        customer1.start();
        customer2.start();

        try {
            // Wait for all threads to finish
            fastVendor.join();
            slowVendor.join();
            customer1.join();
            customer2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Print final ticket pool size
        System.out.println("Final ticket pool size: " + ticketPool.getTicketCount());
    }
}
