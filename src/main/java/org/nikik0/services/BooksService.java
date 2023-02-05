package org.nikik0.services;

import org.nikik0.models.Book;
import org.nikik0.models.Customer;
import org.nikik0.repositories.BooksRepository;
import org.nikik0.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final CustomersRepository customersRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, CustomersRepository customersRepository) {
        this.booksRepository = booksRepository;
        this.customersRepository = customersRepository;
    }

    public List<Book> index(String page, String booksPerPage, String sort) {
        try {
            if (sort != null && sort.equals("true")) {
                return booksRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(booksPerPage), Sort.by("name"))).getContent();
            } else {
                return booksRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(booksPerPage))).getContent();
            }
        } catch (NumberFormatException e) {
            return index(sort);
        }
    }

    public List<Book> index(String sort) {
        if (sort != null && sort.equals("true"))
            return booksRepository.findAll(Sort.by("name"));
        else
            return booksRepository.findAll();
    }

    public Book show(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> searchByName(String name){
        return booksRepository.findByNameStartingWith(name);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setBookid(id);
        booksRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Customer takenBy(int bookId) {
        return customersRepository.findById(booksRepository.findById(bookId).orElse(null).getTaken()).orElse(null);
    }

    public List<Book> indexTaken(int customerId) {
        return booksRepository.findAllByOwner(customersRepository.findById(customerId).orElse(null));
    }

    @Transactional
    public void setTaken(int customerId, int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setTaken(customerId);
        book.setTakenAt(new Date());
    }

    @Transactional
    public void releaseTaken(int bookId) {
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setTaken(0);
        book.setTakenAt(null);
    }
}
