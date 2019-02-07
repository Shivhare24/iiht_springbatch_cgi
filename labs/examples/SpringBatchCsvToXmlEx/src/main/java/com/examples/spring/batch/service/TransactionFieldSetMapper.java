package com.examples.spring.batch.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.examples.spring.batch.model.Transaction;

public class TransactionFieldSetMapper implements FieldSetMapper<Transaction> {
	 
    public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Transaction transaction = new Transaction();
 
        System.out.println(fieldSet.getProperties());
        transaction.setUsername(fieldSet.readString("username"));
        transaction.setUserId(fieldSet.readInt(1));
        transaction.setAmount(fieldSet.readDouble(3));
        String dateString = fieldSet.readString(2);
        try {
            transaction.setTransactionDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return transaction;
    }
}
