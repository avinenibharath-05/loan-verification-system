package com.intain.loanverification.service;

import com.intain.loanverification.entity.Loan;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class HashService {

    public String generateHash(Loan loan) {

        String data =
                loan.getLoanId()
                + "|" + loan.getBorrowerName()
                + "|" + loan.getLoanAmount()
                + "|" + loan.getInterestRate()
                + "|" + loan.getLoanDate()
                + "|" + loan.getStatus();

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            data.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash) {

                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating loan hash",
                    e
            );
        }
    }
    
    public boolean verifyHash(Loan loan) {

        if (loan.getRecordHash() == null) {
            return false;
        }

        String currentHash = this.generateHash(loan);

        return currentHash.equals(loan.getRecordHash());
    }
}