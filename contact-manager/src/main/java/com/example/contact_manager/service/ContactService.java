package com.example.contact_manager.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.example.contact_manager.repository.ContactRepository;
import com.example.contact_manager.model.Contact;


@Service
public class ContactService {




    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
        }

        public Optional<Contact> getContactById(Long id) {
            return contactRepository.findById(id);
        }

        public Contact saveContact(Contact contact) {
            return contactRepository.save(contact);
        }

        public void deleteContact(Long id) {
            contactRepository.deleteById(id);
        }

    }

