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

    // Display the list of contacts
    @GetMapping
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        return "index"; // Renders the index.html Thymeleaf template
    }

    // Show the form to create a new contact
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create"; // Renders the create.html Thymeleaf template
    }

    // Handle form submission for creating a new contact
    @PostMapping("/create")
    public String createContact(@ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create"; // Re-renders the create form with validation errors
        }
        contactService.saveContact(contact);
        return "redirect:/contacts"; // Redirects to the contacts list page
    }

    // Show the form to edit an existing contact
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.getContactById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact ID: " + id));
        model.addAttribute("contact", contact);
        return "edit"; // Renders the edit.html Thymeleaf template
    }

    // Handle form submission for updating an existing contact
    @PostMapping("/edit/{id}")
    public String updateContact(@PathVariable Long id,@ModelAttribute("contact") Contact contact, BindingResult result, Model model) {
        if (result.hasErrors()) {
            contact.setId(id); // Ensure the ID is set for re-rendering the form with errors
            return "edit"; // Re-renders the edit form with validation errors
        }
        contactService.saveContact(contact);
        return "redirect:/contacts"; // Redirects to the contacts list page
    }

    // Handle contact deletion
    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return "redirect:/contacts"; // Redirects to the contacts list page after deletion


    }
    // Show the form to send group email
    @GetMapping("/send-group-email")
    public String showGroupEmailPage(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "group-email"; // Renders the group-email.html Thymeleaf template
    }

    // Handle form submission for sending group email
    @PostMapping("/send-group-email")
    public String sendGroupEmail(
            @RequestParam List<Long> contactIds,  // IDs of selected contacts
            @RequestParam String subject,
            @RequestParam String messageBody) {

        // Fetch emails of selected contacts
        List<String> emailAddresses = contactService.getAllContacts().stream()
                .filter(contact -> contactIds.contains(contact.getId()))
                .map(Contact::getEmail)
                .collect(Collectors.toList());

        // Send the group email using the email service
        emailService.sendGroupEmail(emailAddresses, subject, messageBody);

        return "redirect:/contacts/send-group-email?success"; // Redirect back with success message
    }

}