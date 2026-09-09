
package com.intain.loanverification.service;

import com.intain.loanverification.dto.LoanSummaryResponse;
import com.intain.loanverification.entity.Loan;
import com.intain.loanverification.repository.LoanRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final LoanValidationService loanValidationService;

    private final ExceptionService exceptionService;

    private final HashService hashService;

    public LoanService(
            LoanRepository loanRepository,
            LoanValidationService loanValidationService,
            ExceptionService exceptionService,
            HashService hashService) {

        this.loanRepository = loanRepository;
        this.loanValidationService = loanValidationService;
        this.exceptionService = exceptionService;
        this.hashService = hashService;
    }

    // CREATE LOAN
    public Loan createLoan(Loan loan) {

        List<String> errors =
                loanValidationService.validateLoan(loan);

        if (!errors.isEmpty()) {

            for (String error : errors) {

                exceptionService.createException(
                        loan.getLoanId(),
                        "Loan Data",
                        "VALIDATION_ERROR",
                        error,
                        "HIGH"
                );
            }

            throw new RuntimeException(
                    "Loan validation failed: " + errors
            );
        }

        if (loanRepository.existsByLoanId(loan.getLoanId())) {

            exceptionService.createException(
                    loan.getLoanId(),
                    "loanId",
                    "DUPLICATE",
                    "Loan ID already exists",
                    "HIGH"
            );

            throw new RuntimeException(
                    "Loan ID already exists"
            );
        }

        loan.setVerificationStatus("PENDING");

        return loanRepository.save(loan);
    }

    // GET ALL LOANS
    public List<Loan> getAllLoans() {

        return loanRepository.findAll();
    }

    // GET LOAN BY ID
    public Loan getLoanById(Long id) {

        return loanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));
    }

    // UPDATE LOAN
    public Loan updateLoan(Long id, Loan updatedLoan) {

        Loan existingLoan = getLoanById(id);

        existingLoan.setBorrowerName(
                updatedLoan.getBorrowerName());

        existingLoan.setLoanAmount(
                updatedLoan.getLoanAmount());

        existingLoan.setInterestRate(
                updatedLoan.getInterestRate());

        existingLoan.setLoanDate(
                updatedLoan.getLoanDate());

        existingLoan.setStatus(
                updatedLoan.getStatus());

        // Validate updated loan
        List<String> errors =
                loanValidationService.validateLoan(existingLoan);

        if (!errors.isEmpty()) {

            for (String error : errors) {

                exceptionService.createException(
                        existingLoan.getLoanId(),
                        "Loan Data",
                        "UPDATE_VALIDATION_ERROR",
                        error,
                        "HIGH"
                );
            }

            throw new RuntimeException(
                    "Loan update validation failed: " + errors
            );
        }

        // Data changed, so previous verification is no longer valid
        existingLoan.setVerificationStatus("PENDING");



        return loanRepository.save(existingLoan);
    }

    // VERIFY LOAN
    public Loan verifyLoan(Long id) {

        Loan loan = getLoanById(id);

        List<String> errors =
                loanValidationService.validateLoan(loan);

        if (!errors.isEmpty()) {

            for (String error : errors) {

                exceptionService.createException(
                        loan.getLoanId(),
                        "Loan Data",
                        "VERIFICATION_ERROR",
                        error,
                        "HIGH"
                );
            }

            loan.setVerificationStatus("REJECTED");

            return loanRepository.save(loan);
        }

        String hash = hashService.generateHash(loan);

        loan.setRecordHash(hash);

        loan.setVerificationStatus("VERIFIED");

        return loanRepository.save(loan);
    }

    // VERIFY LOAN HASH
    public boolean verifyLoanHash(Long id) {

        Loan loan = getLoanById(id);

        boolean valid = hashService.verifyHash(loan);

        if (!valid) {

            loan.setVerificationStatus("MODIFIED");

            exceptionService.createException(
                    loan.getLoanId(),
                    "Loan Data",
                    "DATA_MODIFIED",
                    "Loan record has been modified after verification",
                    "HIGH"
            );

            loanRepository.save(loan);
        }

        return valid;
    }
    
 // GET LOAN BY LOAN ID
    public Loan getLoanByLoanId(String loanId) {

        return loanRepository.findByLoanId(loanId)
                .orElseThrow(() ->
                        new RuntimeException("Loan not found"));
    }
    
 // GET LOANS BY VERIFICATION STATUS
    public List<Loan> getLoansByVerificationStatus(
            String verificationStatus) {

        return loanRepository.findByVerificationStatus(
                verificationStatus.toUpperCase()
        );
    }
    
 // GET LOANS BY STATUS
    public List<Loan> getLoansByStatus(String status) {

        return loanRepository.findByStatus(
                status.toUpperCase()
        );
    }
    
 // GET LOAN SUMMARY
    public LoanSummaryResponse getLoanSummary() {

        long totalLoans = loanRepository.count();

        long pendingLoans =
                loanRepository.countByVerificationStatus("PENDING");

        long verifiedLoans =
                loanRepository.countByVerificationStatus("VERIFIED");

        long rejectedLoans =
                loanRepository.countByVerificationStatus("REJECTED");
        long modifiedLoans = 
        		loanRepository.countByVerificationStatus("MODIFIED");

        return new LoanSummaryResponse(
                totalLoans,
                pendingLoans,
                verifiedLoans,
                rejectedLoans,
                modifiedLoans
                
        );
    }

    // DELETE LOAN
    public void deleteLoan(Long id) {

        loanRepository.deleteById(id);
    }
}

