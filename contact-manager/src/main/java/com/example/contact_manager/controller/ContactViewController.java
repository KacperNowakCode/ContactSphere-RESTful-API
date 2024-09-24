package com.example.contact_manager.controller;

import com.example.contact_manager.model.Contact;
import com.example.contact_manager.service.ContactService;
import com.example.contact_manager.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contacts")
public class ContactViewController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "index"; // Renders the index.html Thymeleaf template
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create"; // Renders the create.html Thymeleaf template
    }

    @PostMapping("/create")
    public String createContact(@ModelAttribute("contact") Contact contact, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        contactService.saveContact(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact ID: " + id));
        model.addAttribute("contact", contact);
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String updateContact(@PathVariable Long id,@ModelAttribute("contact") Contact contact, BindingResult result) {
        if (result.hasErrors()) {
            contact.setId(id);
            return "edit";
        }
        contactService.saveContact(contact);
        return "redirect:/contacts"; // Redirects to the contacts list page
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/contacts";
    }

    @GetMapping("/send-group-email")
    public String showGroupEmailPage(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "group-email";
    }

    @PostMapping("/send-group-email")
    public String sendGroupEmail(
            @RequestParam List<Long> contactIds,
            @RequestParam String subject,
            @RequestParam String messageBody) {

        List<String> emailAddresses = contactService.getAllContacts().stream()
                .filter(contact -> contactIds.contains(contact.getId()))
                .map(Contact::getEmail)
                .collect(Collectors.toList());
        emailService.sendGroupEmail(emailAddresses, subject, messageBody);

        return "redirect:/contacts/send-group-email?success";
    }

}