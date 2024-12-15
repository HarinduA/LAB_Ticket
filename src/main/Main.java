package src.main;

import src.core.*;
import src.threads.Customer;
import src.threads.FastVendor;
import src.threads.SlowVendor;
import src.threads.StatisticsReporter;

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
        TicketRetrievalStrategy priorityStrategy = new PriorityRetrieval(); // FIFO strategy
        TicketRetrievalStrategy idStrategy = new IDRetrieval("VIP-Ticket"); // Retrieve VIP-Ticket

        Thread customer1 = new Thread(new Customer(ticketPool, priorityStrategy));
        Thread customer2 = new Thread(new Customer(ticketPool, idStrategy));

        // Create and start the StatisticsReporter
        StatisticsReporter statisticsReporter = new StatisticsReporter(ticketPool);
        Thread reporterThread = new Thread(statisticsReporter);
        reporterThread.start();

        // Start vendors and customers
        fastVendor.start();
        slowVendor.start();
        customer1.start();
        customer2.start();

        try {
            // Wait for vendors and customers to finish
            fastVendor.join();
            slowVendor.join();
            customer1.join();
            customer2.join();

            // Stop the statistics reporter after all operations are done
            statisticsReporter.stopReporter();
            reporterThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Print final ticket pool size
        System.out.println("Final ticket pool size: " + ticketPool.getTicketCount());
    }
}
