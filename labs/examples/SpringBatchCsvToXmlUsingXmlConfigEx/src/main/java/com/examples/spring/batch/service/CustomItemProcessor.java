package com.examples.spring.batch.service;

import org.springframework.batch.item.ItemProcessor;

import com.examples.spring.batch.model.Transaction;

public class CustomItemProcessor implements ItemProcessor<Transaction, Transaction> {

	/**
	 * Batch application processing logic goes here (validation, transformation, filtering, etc..)
	 * This method will be invoked for each record being processed from input source.
	 * This method takes input record object (Transaction) as input and applies transformation and gives back transformed (processed) object.
	 * Both input and output object can be of same type or different type.   
	 */
	public Transaction process(Transaction item) {
		// performing simple transformation of making all input username to uppercase
		item.setUsername(item.getUsername().toUpperCase());
		return item;
	}
}
