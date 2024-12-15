package src.threads;

import src.core.TicketPool;

public class SlowVendor extends Vendor {
    public SlowVendor(TicketPool ticketPool, int baseReleaseRate) {
        super(ticketPool, baseReleaseRate, "SlowVendor");
    }

    @Override
    protected int getAdjustedReleaseRate() {
        return Math.max(1, super.getAdjustedReleaseRate() / 2); // Half the release rate, minimum 1
    }
}
