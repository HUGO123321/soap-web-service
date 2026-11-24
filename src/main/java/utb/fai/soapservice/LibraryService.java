package utb.fai.soapservice;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utb.fai.soapservice.Model.AuthorPersistent;
import utb.fai.soapservice.Model.BookPersistent;
import utb.fai.soapservice.Repository.AuthorRepository;
import utb.fai.soapservice.Repository.BookRepository;

@Service
@Transactional
public class LibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public LibraryService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public AuthorPersistent getAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + authorId));
    }

    public BookPersistent getBook(long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
    }

    public BookPersistent createBook(String title, long authorId) {
        validateRequired(title, "title");
        BookPersistent book = new BookPersistent();
        book.setTitle(title.trim());
        book.setAuthor(getAuthor(authorId));
        return bookRepository.save(book);
    }

    public BookPersistent updateBook(long bookId, String title, long authorId) {
        validateRequired(title, "title");
        BookPersistent book = getBook(bookId);
        book.setTitle(title.trim());
        book.setAuthor(getAuthor(authorId));
        return bookRepository.save(book);
    }

    public void deleteBook(long bookId) {
        BookPersistent book = getBook(bookId);
        bookRepository.delete(book);
    }

    public AuthorPersistent createAuthor(String name, String surname) {
        validateName(name, "name");
        validateName(surname, "surname");
        AuthorPersistent author = new AuthorPersistent();
        author.setName(name.trim());
        author.setSurname(surname.trim());
        return authorRepository.save(author);
    }

    public void deleteAuthor(long authorId) {
        AuthorPersistent author = getAuthor(authorId);
        bookRepository.deleteAll(bookRepository.findByAuthor_Id(authorId));
        authorRepository.delete(author);
    }

    private static void validateRequired(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " must not be empty");
        }
    }

    private static void validateName(String value, String field) {
        validateRequired(value, field);
        if (!Pattern.matches("[\\p{L}\\p{M}]+(?:[-'][\\p{L}\\p{M}]+)*", value.trim())) {
            throw new IllegalArgumentException(field + " must be a single word");
        }
    }
}
