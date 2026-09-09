package com.intain.loanverification.controller;

import com.intain.loanverification.dto.CsvUploadResponse;
import com.intain.loanverification.service.CsvLoanService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:3000")
public class CsvController {

    private final CsvLoanService csvLoanService;

    public CsvController(CsvLoanService csvLoanService) {
        this.csvLoanService = csvLoanService;
    }

    @PostMapping("/upload")
    public ResponseEntity<CsvUploadResponse> uploadCsv(
            @RequestParam("file") MultipartFile file) {

        try {

            if (file.isEmpty()) {

                CsvUploadResponse response =
                        new CsvUploadResponse(
                                0,
                                0,
                                0,
                                "Please select a CSV file"
                        );

                return ResponseEntity.badRequest().body(response);
            }

            int[] result = csvLoanService.processCsv(file);

            CsvUploadResponse response =
                    new CsvUploadResponse(
                            result[0],
                            result[1],
                            result[2],
                            "CSV processing completed"
                    );

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            CsvUploadResponse response =
                    new CsvUploadResponse(
                            0,
                            0,
                            0,
                            "Error processing CSV: " + e.getMessage()
                    );

            return ResponseEntity.badRequest().body(response);
        }
    }
}