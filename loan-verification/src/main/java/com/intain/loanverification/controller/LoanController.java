package com.intain.loanverification.controller;


import com.intain.loanverification.dto.HashVerificationResponse;
import com.intain.loanverification.dto.LoanSummaryResponse;
import com.intain.loanverification.dto.VerificationResponse;
import com.intain.loanverification.entity.Loan;
import com.intain.loanverification.service.LoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {

        return ResponseEntity.ok(
                loanService.createLoan(loan)
        );
    }
    

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {

        return ResponseEntity.ok(
                loanService.getAllLoans()
        );
    }

    @GetMapping("/loan-id/{loanId}")
    public ResponseEntity<Loan> getLoanByLoanId(
            @PathVariable String loanId) {

        return ResponseEntity.ok(
                loanService.getLoanByLoanId(loanId)
        );
    }
    
    @GetMapping("/verification/{verificationStatus}")
    public ResponseEntity<List<Loan>> getLoansByVerificationStatus(
            @PathVariable String verificationStatus) {

        return ResponseEntity.ok(
                loanService.getLoansByVerificationStatus(
                        verificationStatus
                )
        );
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                loanService.getLoansByStatus(status)
        );
    }
    
    
    @GetMapping("/summary")
    public ResponseEntity<LoanSummaryResponse> getLoanSummary() {

        return ResponseEntity.ok(
                loanService.getLoanSummary()
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoan(@PathVariable Long id) {

        return ResponseEntity.ok(
                loanService.getLoanById(id)
        );
    }
    
    

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(
            @PathVariable Long id,
            @RequestBody Loan loan) {

        return ResponseEntity.ok(
                loanService.updateLoan(id, loan)
        );
    }
    
    @PostMapping("/{id}/verify")
    public ResponseEntity<VerificationResponse> verifyLoan(
            @PathVariable Long id) {
    	
    	Loan loan = loanService.verifyLoan(id);

        VerificationResponse response =
                new VerificationResponse(
                        loan.getLoanId(),
                        loan.getVerificationStatus(),
                        loan.getVerificationStatus().equals("VERIFIED")
                                ? "Loan verified successfully"
                                : "Loan verification failed"

    	);
        return ResponseEntity.ok(response);
        
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(
            @PathVariable Long id) {

        loanService.deleteLoan(id);

        return ResponseEntity.ok("Loan deleted successfully");
    }
    @GetMapping("/{id}/verify-hash")
    public ResponseEntity<HashVerificationResponse> verifyLoanHash(
            @PathVariable Long id) {

        Loan loan = loanService.getLoanById(id);

        boolean valid = loanService.verifyLoanHash(id);

        HashVerificationResponse response;

        if (valid) {

            response = new HashVerificationResponse(
                    loan.getLoanId(),
                    true,
                    "Loan record is valid and unchanged"
            );

        } else {

            response = new HashVerificationResponse(
                    loan.getLoanId(),
                    false,
                    "Loan record has been modified"
            );
        }

        return ResponseEntity.ok(response);
    }
}