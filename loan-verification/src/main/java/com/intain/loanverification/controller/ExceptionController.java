package com.intain.loanverification.controller;

import com.intain.loanverification.dto.ExceptionRequest;
import com.intain.loanverification.dto.ExceptionSummaryResponse;
import com.intain.loanverification.entity.ExceptionRecord;
import com.intain.loanverification.service.ExceptionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exceptions")
@CrossOrigin(origins = "http://localhost:3000")
public class ExceptionController {

    private final ExceptionService exceptionService;

    public ExceptionController(ExceptionService exceptionService) {
        this.exceptionService = exceptionService;
    }

    @PostMapping
    public ResponseEntity<ExceptionRecord> createException(
            @RequestBody ExceptionRequest request) {

        return ResponseEntity.ok(
                exceptionService.createException(
                        request.getLoanId(),
                        request.getFieldName(),
                        request.getIssueType(),
                        request.getDescription(),
                        request.getSeverity()
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<ExceptionRecord>> getAllExceptions() {

        return ResponseEntity.ok(
                exceptionService.getAllExceptions()
        );
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<ExceptionRecord>> getExceptionsByLoanId(
            @PathVariable String loanId) {

        return ResponseEntity.ok(
                exceptionService.getExceptionsByLoanId(loanId)
        );
    }

    @GetMapping("/open")
    public ResponseEntity<List<ExceptionRecord>> getOpenExceptions() {

        return ResponseEntity.ok(
                exceptionService.getOpenExceptions()
        );
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<ExceptionRecord> resolveException(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                exceptionService.resolveException(id)
        );
    }
    @GetMapping("/summary")
    public ResponseEntity<ExceptionSummaryResponse> getExceptionSummary() {

        return ResponseEntity.ok(
                exceptionService.getExceptionSummary()
        );
    }
}