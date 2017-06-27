package com.dejan.sdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

public class Application {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// XML Configuration
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

		// Java Configuration
		// try(AnnotationConfigApplicationContext context
		// = new AnnotationConfigApplicationContext(DataConfiguration.class)) {
		BookRepository repository = context.getBean(BookRepository.class);

		// -----------------------------------------------------------------------------------------

		// 04_02-Retrieving Entities
		// Retrieve One Book By ID (WHERE ID = ?)
		Book book = repository.findOne(1L);
		System.out.println(book);

		// Retrieve All Books
		List<Book> books = repository.findAll();
		for (Book book1 : books) {
			System.out.println(book1);
		}

		// Retrieve Specific Books (IN (?,?,?))
		List<Book> books1 = repository.findAll(new ArrayList<Long>() {
			{
				add(1L);
				add(3L);
				add(7L);
			}
		});
		for (Book book2 : books1) {
			System.out.println(book2);
		}

		// -----------------------------------------------------------------------------------------

		// 04_03-Persisting New Entities (if there is no ID specified - SQL
		// INSERT)
		// Save One Book
		book = new Book();
		book.setTitle("Title");
		book.setPageCount(100);
		book.setPrice(new BigDecimal(100.00));
		book.setPublishDate(new Date());

		repository.save(book);
		System.out.println(repository.findOne(8L));

		// Save List Of Books
		repository.save(Arrays.asList(new Book(), new Book(), new Book()));
		books = repository.findAll();
		for (Book book1 : books) {
			System.out.println(book1);
		}

		// -----------------------------------------------------------------------------------------

		// 04_04-Modifying Entities (if the ID is specified - SQL UPDATE)
		book = repository.findOne(1L);
		System.out.println(book);
		book.setTitle("War and Peace");
		repository.save(book);
		book = repository.findOne(1L);
		System.out.println(book);

		// -----------------------------------------------------------------------------------------

		// 04_05-Removing Entities
		// By ID
		repository.delete(1L);

		// By Entity
		repository.delete(repository.findOne(2L));

		// By Collection Of Entities
		repository.delete(repository.findAll(Arrays.asList(8L, 9L, 10L)));

		// By Collection Of Entities In One Query (OR)
		repository.deleteInBatch(repository.findAll(Arrays.asList(3L, 4L, 5L)));

		books = repository.findAll();
		for (Book book1 : books) {
			System.out.println(book1);
		}

		// Delete All Entities
		repository.deleteAll();

		// Delete All Entities In One Query (OR)
		repository.deleteAllInBatch();

		// -----------------------------------------------------------------------------------------

		book = new Book();
		book.setTitle("Animal Farm");
		book.setPageCount(100);
		book.setPrice(new BigDecimal(100.00));
		book.setPublishDate(new Date());
		repository.save(book);

		
		//05_02-Derived Queries
		System.out.println("Derived Queries");

		/*
		 * select book0_.BOOK_ID as BOOK_ID1_0_, book0_.PAGE_COUNT as
		 * PAGE_COU2_0_, book0_.PRICE as PRICE3_0_, book0_.PUBLISH_DATE as
		 * PUBLISH_4_0_, book0_.TITLE as TITLE5_0_ from BOOK book0_ where
		 * book0_.TITLE=?
		 */

		System.out.println(repository.findByTitle("Animal Farm"));

		
		//05_03-String Operators
		System.out.println("String Operators");
		
		for(Book book1: repository.findByTitleLike("%al%")) { //with wildcard %
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByTitleContaining("al")) { //without wildcard 
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByTitleStartingWith("A")) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByTitleEndingWith("m")) { 
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByTitleIgnoreCase("aNimaL FaRM")) {
			System.out.println(book1);
		}
		
		
		//05_04-Relational Operators
		System.out.println("Relational Operators");

		for(Book book1: repository.findByPageCountGreaterThanEqual(100)) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByPageCountBetween(80, 10)) {
			System.out.println(book1);
		}
		
		
		//05_05-Logical Operators
		System.out.println("Logical Operators");
		
		for(Book book1: repository.findByTitleContainingAndPageCountGreaterThan("Farm", 90)) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.findByTitleNot("Animal Farm")) {
			System.out.println(book1);
		}
		
		
		//05_06-Date Comparisons
		System.out.println("Date Comparisons");
		
		Date date = new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2017");
		Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse("01/17/2017");
		for(Book book1: repository.findByPublishDateBetween(date, date2)) {
			System.out.println(book1);
		}
		
		
		//05_07-Ordering Results
		System.out.println("Ordering Results");
		
		for(Book book1: repository.findByTitleContainingOrderByTitleDesc("Farm")) {
			System.out.println(book1);
		}
		
		
		//05_08-Limiting Query Results
		System.out.println("Limiting Query Results");
		
		for(Book book1: repository.findTopByOrderByPageCountDesc()) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.findTop5ByOrderByPriceDesc()) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.findTop5ByTitleOrderByPriceAsc("Animal")) {
			System.out.println(book1);
		}
		
		
		// -----------------------------------------------------------------------------------------
		
		//06_02-@Query
		//06_03-Named Queries
		System.out.println("@Query");
		
		for(Book book1: repository.queryOne()) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.queryTwo(90)) {
			System.out.println(book1);
		}
		
		for(Book book1: repository.queryThree("Animal Farm")) {
			System.out.println(book1);
		}
		
		
		//06_04-Paging Results
		System.out.println("Paging Results");
		
		for (Book book1 : repository.findAll(new PageRequest(0, 3))) { //(page zero-based, number of results)
			System.out.println(book1);
		}
		
		for (Book book1 : repository.findByPageCountGreaterThan(90, new PageRequest(1,2))) { 
			System.out.println(book1);
		}
		
		
		//06_05-Sorting Results
		System.out.println("Sorting Results");
		
		for (Book book1 : repository.findAll(new Sort(Sort.Direction.ASC, "author.lastName", "pageCount"))) { //Default ASC
			System.out.println(book1);
		}
		
		for (Book book1 : repository.findAll(new Sort(Sort.Direction.ASC, "author.lastName").and(new Sort(Sort.Direction.DESC, "pageCount")))) { //Default ASC
			System.out.println(book1);
		}
		
		for (Book book1 : repository.findByPageCountGreaterThan(220, new Sort("author.firstName"))) {
			System.out.println(book1);
		}
		
		//06_06-Query Method Return Types
		System.out.println("Query Method Return Types");
		
		for (Book book1 : repository.findByPageCount(new PageRequest(0,3))) {
			System.out.println(book1);
		}
		
		//Return type can be: List, Collection, Iterable, Page, Slice
		Page page = repository.findByPageCount(new PageRequest(0,3));
		page.getTotalElements(); //number of elements on a page
		page.getTotalPages(); //total number of pages
		
		Slice slice = repository.findByTitle(new PageRequest(0,3));
		slice.hasNext(); //can determine if there is another slice,
		 				 //but does not have getToatalPages() method (for efficiency).						  *
		 		
		// -----------------------------------------------------------------------------------------

		//07_03-Global Repository Customization
		for(Book b:repository.findByIds(1L,2L)) {
			System.out.println(b);
		}
		
		// }
	}
}
