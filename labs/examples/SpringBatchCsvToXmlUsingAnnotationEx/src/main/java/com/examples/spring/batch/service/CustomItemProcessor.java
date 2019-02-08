package com.examples.spring.batch.service;

import org.springframework.batch.item.ItemProcessor;

import com.examples.spring.batch.model.Transaction;

public class CustomItemProcessor implements ItemProcessor<Transaction, Transaction> {

	public Transaction process(Transaction item) {
		item.setUsername(item.getUsername().toUpperCase());
		//System.out.println("Processing logic goes here...");
		return item;
	}
}
