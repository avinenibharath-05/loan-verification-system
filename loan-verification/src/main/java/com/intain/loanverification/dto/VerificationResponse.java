package com.intain.loanverification.dto;

public class VerificationResponse {

    private String loanId;
    private String verificationStatus;
    private String message;

    public VerificationResponse() {
    }

    public VerificationResponse(
            String loanId,
            String verificationStatus,
            String message) {

        this.loanId = loanId;
        this.verificationStatus = verificationStatus;
        this.message = message;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}