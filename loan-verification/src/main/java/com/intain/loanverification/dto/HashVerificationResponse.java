package com.intain.loanverification.dto;

public class HashVerificationResponse {

    private String loanId;
    private boolean valid;
    private String message;

    public HashVerificationResponse() {
    }

    public HashVerificationResponse(
            String loanId,
            boolean valid,
            String message) {

        this.loanId = loanId;
        this.valid = valid;
        this.message = message;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}