package org.nikik0.controllers;

import org.nikik0.models.Book;
import org.nikik0.models.Customer;
import org.nikik0.services.BooksService;
import org.nikik0.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final CustomersService customersService;
    @Autowired
    public BooksController(BooksService booksService, CustomersService customersService) {
        this.booksService = booksService;
        this.customersService = customersService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "sort", required = false) String sorting,
                        @RequestParam(value = "books_per_page", required = false) String booksPerPage,  Model model){
        if (page != null && booksPerPage!=null)
            model.addAttribute("books", booksService.index(page, booksPerPage, sorting));
        else
            model.addAttribute("books", booksService.index(sorting));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute("customer") Customer customer){
        model.addAttribute("books", booksService.show(id));
        model.addAttribute("customerTaken", booksService.takenBy(id));
        model.addAttribute("customers", customersService.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book){
        return "books/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "books/new";
        booksService.save(book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "books/new";
        booksService.update(id,book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("books", booksService.show(id));
        return "books/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/setowner")
    public String setOwner(@ModelAttribute("custom") @Valid Customer customer, BindingResult bindingResult, @PathVariable("id") int id){
        booksService.setTaken(customer.getCustomerid(), id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        booksService.releaseTaken(id);
        return "redirect:/books/"+id;
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(name = "bookName", required = false) String name, Model model){
        if (name!=null)
            model.addAttribute("searchResult", booksService.searchByName(name));
        return "books/search";
    }
}
