package com.intain.loanverification.service;

import com.intain.loanverification.entity.Loan;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;

@Service
public class CsvLoanService {

    private final LoanService loanService;

    public CsvLoanService(LoanService loanService) {
        this.loanService = loanService;
    }

    public int[] processCsv(MultipartFile file) throws Exception {

        int totalRecords = 0;
        int successfulRecords = 0;
        int failedRecords = 0;

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("CSV file is empty");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".csv")) {

            throw new RuntimeException(
                    "Only CSV files are allowed"
            );
        }

        Reader reader = new BufferedReader(
                new InputStreamReader(
                        file.getInputStream()
                )
        );

        CSVParser csvParser = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get()
                .parse(reader);

        String[] requiredColumns = {
                "Loan_ID",
                "Borrower_Name",
                "Loan_Amount",
                "Interest_Rate",
                "Loan_Date",
                "Status"
        };

        for (String column : requiredColumns) {

            if (!csvParser.getHeaderMap().containsKey(column)) {

                csvParser.close();
                reader.close();

                throw new RuntimeException(
                        "Missing required CSV column: " + column
                );
            }
        }

        for (CSVRecord record : csvParser) {

            totalRecords++;

            try {

                Loan loan = new Loan();

                loan.setLoanId(
                        record.get("Loan_ID").trim()
                );

                loan.setBorrowerName(
                        record.get("Borrower_Name").trim()
                );

                loan.setLoanAmount(
                        Double.parseDouble(
                                record.get("Loan_Amount").trim()
                        )
                );

                loan.setInterestRate(
                        Double.parseDouble(
                                record.get("Interest_Rate").trim()
                        )
                );

                loan.setLoanDate(
                        LocalDate.parse(
                                record.get("Loan_Date").trim()
                        )
                );

                loan.setStatus(
                        record.get("Status").trim().toUpperCase()
                );

                loanService.createLoan(loan);

                successfulRecords++;

            } catch (Exception e) {

                failedRecords++;

                System.out.println(
                        "Error processing CSV row "
                        + record.getRecordNumber()
                        + ": "
                        + e.getMessage()
                );
            }
        }

        csvParser.close();
        reader.close();

        return new int[]{
                totalRecords,
                successfulRecords,
                failedRecords
        };
    }
}