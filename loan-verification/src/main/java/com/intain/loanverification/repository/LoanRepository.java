package com.intain.loanverification.repository;

import com.intain.loanverification.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLoanId(String loanId);

    Optional<Loan> findByLoanId(String loanId);

    List<Loan> findByVerificationStatus(String verificationStatus);

    List<Loan> findByStatus(String status);

    long countByVerificationStatus(String verificationStatus);
}