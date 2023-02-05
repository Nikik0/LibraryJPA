package org.nikik0.repositories;

import org.nikik0.models.Book;
import org.nikik0.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByOwner(Customer customer);
    List<Book> findByNameStartingWith(String name);
}
