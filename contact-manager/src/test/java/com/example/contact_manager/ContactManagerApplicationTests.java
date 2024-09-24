package com.example.contact_manager;

import com.example.contact_manager.model.Contact;
import com.example.contact_manager.repository.ContactRepository;
import com.example.contact_manager.service.ContactService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContactManagerApplicationTests {

	@Mock
	private ContactRepository contactRepository;

	@InjectMocks
	private ContactService contactService;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAllContacts() {
		Contact contact1 = new Contact();
		contact1.setId(1L);
		contact1.setName("Jan Kowalski");
		contact1.setEmail("jankowal@example.com");
		contact1.setPhoneNumber("123 456 789");

		Contact contact2 = new Contact();
		contact2.setId(2L);
		contact2.setName("Adam Brzozowski");
		contact2.setEmail("adam@example.com");
		contact2.setPhoneNumber("987654321");

		List<Contact> contacts = Arrays.asList(contact1, contact2);
		when(contactRepository.findAll()).thenReturn(contacts);

		List<Contact> result = contactService.getAllContacts();

		assertEquals(2, result.size());
		assertEquals("Jan Kowalski", result.get(0).getName());
		verify(contactRepository, times(1)).findAll();
	}

	@Test
	void getContactById() {
		Contact contact = new Contact();
		contact.setId(1L);
		contact.setName("Jan Kowalski");

		when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));

		Optional<Contact> result = contactService.getContactById(1L);

		assertTrue(result.isPresent());
		assertEquals("Jan Kowalski", result.get().getName());
		verify(contactRepository, times(1)).findById(1L);
	}

	@Test
	void saveContact() {
		Contact contact = new Contact();
		contact.setName("Jan Kowalski");

		when(contactRepository.save(contact)).thenReturn(contact);

		Contact result = contactService.saveContact(contact);

		assertEquals("Jan Kowalski", result.getName());
		verify(contactRepository, times(1)).save(contact);
	}

	@Test
	void deleteContact() {
		Long contactId = 1L;

		doNothing().when(contactRepository).deleteById(contactId);

		contactService.deleteContact(contactId);

		verify(contactRepository, times(1)).deleteById(contactId);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();

	}

	@Test
	void getContactByNonExistentId() {
		when(contactRepository.findById(99L)).thenReturn(Optional.empty());

		Optional<Contact> result = contactService.getContactById(99L);

		assertTrue(result.isEmpty(), "Expected contact not to be found");
		verify(contactRepository, times(1)).findById(99L);
	}

	@Test
	void saveContactWithMissingName() {
		Contact contact = new Contact();
		contact.setEmail("missingname@example.com");
		contact.setPhoneNumber("123456789");

		when(contactRepository.save(contact)).thenThrow(new IllegalArgumentException("Name is required"));

		Exception exception = assertThrows(IllegalArgumentException.class, () -> contactService.saveContact(contact));

		assertEquals("Name is required", exception.getMessage());
	}

	@Test
	void deleteNonExistentContact() {
		doThrow(new RuntimeException("Contact not found")).when(contactRepository).deleteById(99L);

		Exception exception = assertThrows(RuntimeException.class, () -> contactService.deleteContact(99L));

		assertEquals("Contact not found", exception.getMessage());
	}
}

