package com.intain.loanverification.dto;

public class LoanSummaryResponse {

    private long totalLoans;
    private long pendingLoans;
    private long verifiedLoans;
    private long rejectedLoans;
    private long modifiedLoans;
    
    public LoanSummaryResponse() {
    }

    public LoanSummaryResponse(
            long totalLoans,
            long pendingLoans,
            long verifiedLoans,
            long rejectedLoans,
            long modifiedLoans) {

        this.totalLoans = totalLoans;
        this.pendingLoans = pendingLoans;
        this.verifiedLoans = verifiedLoans;
        this.rejectedLoans = rejectedLoans;
        this.modifiedLoans=modifiedLoans;
    }

    public long getTotalLoans() {
        return totalLoans;
    }

    public void setTotalLoans(long totalLoans) {
        this.totalLoans = totalLoans;
    }

    public long getPendingLoans() {
        return pendingLoans;
    }

    public void setPendingLoans(long pendingLoans) {
        this.pendingLoans = pendingLoans;
    }

    public long getVerifiedLoans() {
        return verifiedLoans;
    }

    public void setVerifiedLoans(long verifiedLoans) {
        this.verifiedLoans = verifiedLoans;
    }

    public long getRejectedLoans() {
        return rejectedLoans;
    }

    public void setRejectedLoans(long rejectedLoans) {
        this.rejectedLoans = rejectedLoans;
    }
    public long getModifiedLoans() {
    	return modifiedLoans;
    }
    public void setModifiedLoans(long modifiedLoans) {
    	this.modifiedLoans=modifiedLoans;
    }
}