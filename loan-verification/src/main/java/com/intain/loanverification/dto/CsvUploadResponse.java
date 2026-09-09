package com.intain.loanverification.dto;

public class CsvUploadResponse {

    private int totalRecords;
    private int successfulRecords;
    private int failedRecords;
    private String message;

    public CsvUploadResponse() {
    }

    public CsvUploadResponse(
            int totalRecords,
            int successfulRecords,
            int failedRecords,
            String message) {

        this.totalRecords = totalRecords;
        this.successfulRecords = successfulRecords;
        this.failedRecords = failedRecords;
        this.message = message;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getSuccessfulRecords() {
        return successfulRecords;
    }

    public void setSuccessfulRecords(int successfulRecords) {
        this.successfulRecords = successfulRecords;
    }

    public int getFailedRecords() {
        return failedRecords;
    }

    public void setFailedRecords(int failedRecords) {
        this.failedRecords = failedRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}