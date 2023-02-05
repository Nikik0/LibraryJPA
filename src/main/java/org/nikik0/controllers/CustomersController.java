package org.nikik0.controllers;

import org.nikik0.models.Customer;
import org.nikik0.services.BooksService;
import org.nikik0.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/customers")
public class CustomersController {
    private final BooksService booksService;
    private final CustomersService customersService;
    @Autowired
    public CustomersController(BooksService booksService, CustomersService customersService) {
        this.booksService = booksService;
        this.customersService = customersService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("customers",customersService.index());
        return "customers/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("customers", customersService.show(id));
        return "customers/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("customers") @Valid Customer customer, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()) return "customers/new";
        customersService.update(id, customer);
        return "redirect:/customers";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customers") Customer customer){
        return "customers/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("customers") @Valid Customer customer, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "customers/new";
        customersService.save(customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        customersService.delete(id);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        int daysToExpire = 10;
        model.addAttribute("customers", customersService.show(id));
        model.addAttribute("books", booksService.indexTaken(id));
        model.addAttribute("lastValidDate", new Date().getTime()-daysToExpire*24*60*60);
        return "customers/show";
    }



}
