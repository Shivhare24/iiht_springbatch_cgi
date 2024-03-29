package com.examples.spring.batch.config;

import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.examples.spring.batch.model.Transaction;
import com.examples.spring.batch.service.CustomItemProcessor;
import com.examples.spring.batch.service.TransactionFieldSetMapper;

/**
 * Batch job configuration
 */
@Configuration
public class JobConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("input/transactions.csv")
	private Resource inputCsv;

	@Value("file:target/output/transactions.xml")
	private Resource outputXml;

	/**
	 * Reader configuration to read data from flat file (csv)
	 */
	@Bean
	public ItemReader<Transaction> itemReader() throws UnexpectedInputException, ParseException {
		FlatFileItemReader<Transaction> reader = new FlatFileItemReader<Transaction>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		String[] tokens = { "username", "userid", "transactiondate", "amount" };
		tokenizer.setNames(tokens);
		reader.setResource(inputCsv);
		DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<Transaction>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new TransactionFieldSetMapper());
		reader.setLineMapper(lineMapper);
		return reader;
	}

	/**
	 * Processor configuration to process input item
	 */
	@Bean
	public ItemProcessor<Transaction, Transaction> itemProcessor() {
		return new CustomItemProcessor();
	}

	/**
	 * Writer configuration to write data into XML document
	 */
	@Bean
	public ItemWriter<Transaction> itemWriter(Marshaller marshaller) throws MalformedURLException {
		StaxEventItemWriter<Transaction> itemWriter = new StaxEventItemWriter<Transaction>();
		itemWriter.setMarshaller(marshaller);
		itemWriter.setRootTagName("transactions");
		itemWriter.setResource(outputXml);
		return itemWriter;
	}

	/**
	 * Marshaller configuration to bind Transaction object properties to XML
	 */
	@Bean
	public Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { Transaction.class });
		return marshaller;
	}

	/**
	 * Step configuration
	 */
	@Bean
	protected Step step1(ItemReader<Transaction> reader, ItemProcessor<Transaction, Transaction> processor,
			ItemWriter<Transaction> writer) {
		return steps
				.get("step1")
				.<Transaction, Transaction>chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	/**
	 * Batch job configuration
	 */
	@Bean(name = "csvToXmlBatchJob")
	public Job job(@Qualifier("step1") Step step1) {
		return jobs
				.get("firstBatchJob")
				.start(step1)
				.build();
	}
}
