package com.examples.spring.batch.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.examples.spring.batch.model.Transaction;

public class TransactionRowMapper implements RowMapper<Transaction> {

	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {

		Transaction transaction = new Transaction();

		transaction.setUsername(rs.getString("username"));
		transaction.setUserId(rs.getInt("userId"));
		transaction.setTransactionDate(rs.getDate("transactionDate"));
		transaction.setAmount(rs.getDouble("amount"));

		return transaction;
	}
}