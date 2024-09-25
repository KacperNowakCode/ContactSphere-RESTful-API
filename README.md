## ContactSphere Application

## Overwiev:
The Contact Manager Application is a robust, full-stack web application.

The application allows users to create, edit, and delete contacts, and send group emails.
With a clean and intuitive user interface, it simplifies contact management tasks,
making it an useful everyday tool.

## Technologies Used:

**-Java 17**: Leveraged the features of Java for building a robust backend.  
**-Spring Boot**: Utilized Spring Boot to rapidly develop the backend, enabling dependency injection and data persistence.  
**-Spring Data JPA (Hibernate)**: Facilitated easy integration with the MySQL database for managing contact data and groups.  
**-Thymeleaf**: Implemented Thymeleaf as the templating engine to render dynamic HTML pages and enhance the user experience.  
**-Bootstrap**: Provided a responsive and attractive frontend.  
**-MySQL**: Employed MySQL as the relational database to store contact information and other essential data.  
**-Jakarta Mail (JavaMail)**: Used for sending group emails directly from the application.  

## App Features:

**-Contact Management**: Add, update, delete, and search contacts with ease, ensuring your contact list is always up-to-date.  
**-Email Functionality**: Send group emails directly from the application using SMTP, making communication effortless and efficient.  
**-User-Friendly Interface**: Designed with a clean and responsive UI using Thymeleaf, Bootstrap, and custom CSS, ensuring a smooth user experience on any device.  
**-Data Persistence**: Integrated with MySQL for reliable data storage, ensuring your contacts are safe.  
**-RESTful APIs**: Utilizes RESTful services for managing contacts, making the application modular and easily extendable.  

## Installation and Setup:

-git clone https://github.com/KacperNowakCode/ContactSphere-RESTful-API  
-Configure your MySQL database in application.properties.  
-cd contactsphere-restful-api  
-cd contact-manager  
-sudo mvn clean install  
-sudo mvn spring-boot:run  
-Open your browser and go to http://localhost:8080/contacts  
