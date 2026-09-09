package com.intain.loanverification.service;

import com.intain.loanverification.dto.ExceptionSummaryResponse;
import com.intain.loanverification.entity.ExceptionRecord;
import com.intain.loanverification.repository.ExceptionRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExceptionService {

    private final ExceptionRepository exceptionRepository;

    public ExceptionService(ExceptionRepository exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    public ExceptionRecord createException(
            String loanId,
            String fieldName,
            String issueType,
            String description,
            String severity) {

        ExceptionRecord exception = new ExceptionRecord();

        exception.setLoanId(loanId);
        exception.setFieldName(fieldName);
        exception.setIssueType(issueType);
        exception.setDescription(description);
        exception.setSeverity(severity);
        exception.setStatus("OPEN");
        exception.setCreatedAt(LocalDateTime.now());

        return exceptionRepository.save(exception);
    }

    public List<ExceptionRecord> getAllExceptions() {
        return exceptionRepository.findAll();
    }

    public List<ExceptionRecord> getExceptionsByLoanId(String loanId) {
        return exceptionRepository.findByLoanId(loanId);
    }

    public List<ExceptionRecord> getOpenExceptions() {
        return exceptionRepository.findByStatus("OPEN");
    }

    public ExceptionRecord resolveException(Long id) {

        ExceptionRecord exception = exceptionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Exception not found"));

        exception.setStatus("RESOLVED");
        exception.setResolvedAt(LocalDateTime.now());

        return exceptionRepository.save(exception);
    }
    
 // GET EXCEPTION SUMMARY
    public ExceptionSummaryResponse getExceptionSummary() {

        long totalExceptions = exceptionRepository.count();

        long openExceptions =
                exceptionRepository.countByStatus("OPEN");

        long resolvedExceptions =
                exceptionRepository.countByStatus("RESOLVED");

        return new ExceptionSummaryResponse(
                totalExceptions,
                openExceptions,
                resolvedExceptions
        );
    }
}