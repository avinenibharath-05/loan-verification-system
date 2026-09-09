package com.intain.loanverification.dto;

public class ExceptionSummaryResponse {

    private long totalExceptions;
    private long openExceptions;
    private long resolvedExceptions;

    public ExceptionSummaryResponse() {
    }

    public ExceptionSummaryResponse(
            long totalExceptions,
            long openExceptions,
            long resolvedExceptions) {

        this.totalExceptions = totalExceptions;
        this.openExceptions = openExceptions;
        this.resolvedExceptions = resolvedExceptions;
    }

    public long getTotalExceptions() {
        return totalExceptions;
    }

    public void setTotalExceptions(long totalExceptions) {
        this.totalExceptions = totalExceptions;
    }

    public long getOpenExceptions() {
        return openExceptions;
    }

    public void setOpenExceptions(long openExceptions) {
        this.openExceptions = openExceptions;
    }

    public long getResolvedExceptions() {
        return resolvedExceptions;
    }

    public void setResolvedExceptions(long resolvedExceptions) {
        this.resolvedExceptions = resolvedExceptions;
    }
}