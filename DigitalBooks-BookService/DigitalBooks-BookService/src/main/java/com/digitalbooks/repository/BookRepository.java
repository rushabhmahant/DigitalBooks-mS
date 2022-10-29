package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Book findByBookId(Long bookId);

	@Query("SELECT b FROM Book b where (:category is null or b.bookCategory=:category) and "
			+ "(:title is null or b.bookTitle=:title) and "
			+ "(:author is null or b.bookAuthor=:author) and "
			+ "(:price is null or b.bookPrice=:price)")
	public List<Book> searchBook(String category, String title, String author, Double price);
	
}
