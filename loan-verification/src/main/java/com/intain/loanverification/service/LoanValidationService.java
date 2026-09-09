package com.intain.loanverification.service;

import com.intain.loanverification.entity.Loan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanValidationService {

    public List<String> validateLoan(Loan loan) {

        List<String> errors = new ArrayList<>();

        // Loan ID validation
        if (loan.getLoanId() == null ||
                loan.getLoanId().trim().isEmpty()) {

            errors.add("Loan ID is required");
        }

        // Borrower name validation
        if (loan.getBorrowerName() == null ||
                loan.getBorrowerName().trim().isEmpty()) {

            errors.add("Borrower name is required");
        }

        // Loan amount validation
        if (loan.getLoanAmount() == null ||
                loan.getLoanAmount() <= 0) {

            errors.add("Loan amount must be greater than 0");
        }

        // Interest rate validation
        if (loan.getInterestRate() == null ||
                loan.getInterestRate() < 0) {

            errors.add("Interest rate cannot be negative");
        }

        // Loan date validation
        if (loan.getLoanDate() == null) {

            errors.add("Loan date is required");
        }

        // Status validation
        if (loan.getStatus() == null ||
                (!loan.getStatus().equals("ACTIVE") &&
                 !loan.getStatus().equals("CLOSED") &&
                 !loan.getStatus().equals("DEFAULT") &&
                 !loan.getStatus().equals("PENDING"))) {

            errors.add("Invalid loan status");
        }

        return errors;
    }
}