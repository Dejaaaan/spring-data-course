package com.dejan.sdata;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {

	
	//05_03-String Operators
	public Book findByTitle(String title);
	
	public List<Book> findByTitleLike(String title);
	
	public List<Book> findByTitleContaining(String title);

	public List<Book> findByTitleStartingWith(String title);

	public List<Book> findByTitleEndingWith(String title);

	public List<Book> findByTitleIgnoreCase(String title);
	
	
	//05_04-Relational Operators
	public List<Book> findByPageCountEquals(int pageCount);

	public List<Book> findByPageCountGreaterThan(int pageCount);

	public List<Book> findByPageCountLessThan(int pageCount);

	public List<Book> findByPageCountGreaterThanEqual(int pageCount);

	public List<Book> findByPageCountLessThanEqual(int pageCount);

	public List<Book> findByPageCountBetween(int min, int max);
	
	
	//05_05-Logical Operators
	
	public List<Book> findByTitleContainingOrTitleContaining(String title, String title2);

	public List<Book> findByTitleContainingAndPageCountGreaterThan(String title, int pageCount);
	
	public List<Book> findByTitleNot(String title);
	
	
	//05_06-Date Comparisons
	public List<Book> findByPublishDateAfter(Date date);
	
	public List<Book> findByPublishDateBefore(Date date);
	
	public List<Book> findByPublishDateBetween(Date date, Date date2);
	
	
	//05_07-Ordering Results
	public List<Book> findByTitleContainingOrderByTitleAsc(String title);
	
	public List<Book> findByTitleContainingOrderByTitleDesc(String title);
	
	
	//05_08-Limiting Query Results
	public List<Book> findTopByOrderByPageCountDesc(); //find max. value (page count)
	
	public List<Book> findFirstByOrderByPageCountAsc(); //find min. value (page count) (First same as Top)

	public List<Book> findTop5ByOrderByPriceDesc(); //top 5
	
	public List<Book> findTop5ByTitleOrderByPriceAsc(String title); //with WERE
	
	
	// -----------------------------------------------------------------------------------------
	
	//06_02-@Query
	
	@Query("select b from Book b")
	public List<Book> queryOne();
	
	@Query("select b from Book b where b.pageCount > ?1")
	public List<Book> queryTwo(int pageCount);
	
	@Query("select b from Book b where b.title = :title")
	public List<Book> queryThree(@Param("title") String title);
	
	
	//06_04-Paging Results
	public List<Book> findByPageCountGreaterThan(int pageCount, Pageable pageable);
	
	//06_05-Sorting Results
	public List<Book> findByPageCountGreaterThan(int pageCount, Sort sort);
	
	//06_06-Query Method Return Types
	//List	Collection	Iterable	Page	Slice
	public Page<Book> findByPageCount(Pageable pageable);
	
	public Slice<Book> findByTitle(Pageable pageable);
	
}
