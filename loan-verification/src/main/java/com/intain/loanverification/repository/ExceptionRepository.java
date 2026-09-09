package com.intain.loanverification.repository;

import com.intain.loanverification.entity.ExceptionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExceptionRepository
        extends JpaRepository<ExceptionRecord, Long> {

    List<ExceptionRecord> findByLoanId(String loanId);

    List<ExceptionRecord> findByStatus(String status);

    long countByStatus(String status);
}