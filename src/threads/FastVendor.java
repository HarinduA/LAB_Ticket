package src.threads;

import src.core.TicketPool;

public class FastVendor extends Vendor {
    public FastVendor(TicketPool ticketPool, int baseReleaseRate) {
        super(ticketPool, baseReleaseRate, "FastVendor");
    }

    @Override
    protected int getAdjustedReleaseRate() {
        return super.getAdjustedReleaseRate() * 2; // Double the release rate
    }
}
