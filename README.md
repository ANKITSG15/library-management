##################### Public Library Management System (LMS) ##############

1.	*User Management*
  - LMS supports two types of users/roles
  - Librarian : admin like access (can add/update book details, list all books using filters)
  - Member : normal user (can borrow/return book, fetch the list of books using filters)
  

2. Authorisation and Authentication : 
- Issue JWT with email, userId, and role
- JWT claims : userId, email, role
- Signing Algorithm : HMAC using SHA-256
- Token expiry = 10 hours
- Spring security : Authorize/Protect the API endpoints/paths based on role

3. Database tables details :

   
<img width="1349" height="441" alt="Screenshot 2025-07-22 at 12 49 17 AM" src="https://github.com/user-attachments/assets/859b5c8c-4ab8-48f2-a59b-c48e51539dc1" />



4. Notification system on Due pass date using Spring Cron Scheduler.

5. ControllerAdvice to handle exceptions globally across the controllers.

6. Using @Transactional, while borrowing and returning the book.

7. Pagination, sorting and filters to fetch the list of books.










