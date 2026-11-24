package utb.fai.soapservice;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import utb.fai.soapservice.Model.AuthorPersistent;
import utb.fai.soapservice.Model.BookPersistent;
import com.example.book_web_service.*;

@Endpoint
public class LibraryEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/book-web-service";
    private final LibraryService libraryService;

    public LibraryEndpoint(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBook(@RequestPayload GetBookRequest request) {
        GetBookResponse response = new GetBookResponse();
        response.setBook(toBook(libraryService.getBook(request.getBookId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        Book input = request.getBook();
        CreateBookResponse response = new CreateBookResponse();
        response.setBook(toBook(libraryService.createBook(input.getTitle(), input.getAuthorID())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBookRequest")
    @ResponsePayload
    public UpdateBookResponse updateBook(@RequestPayload UpdateBookRequest request) {
        Book input = request.getBook();
        UpdateBookResponse response = new UpdateBookResponse();
        response.setBook(toBook(libraryService.updateBook(request.getBookId(), input.getTitle(), input.getAuthorID())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteBookRequest")
    @ResponsePayload
    public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request) {
        libraryService.deleteBook(request.getBookId());
        DeleteBookResponse response = new DeleteBookResponse();
        response.setMessage("Book deleted successfully");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthorRequest")
    @ResponsePayload
    public GetAuthorResponse getAuthor(@RequestPayload GetAuthorRequest request) {
        GetAuthorResponse response = new GetAuthorResponse();
        response.setAuthor(toAuthor(libraryService.getAuthor(request.getAuthorId())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAuthorRequest")
    @ResponsePayload
    public CreateAuthorResponse createAuthor(@RequestPayload CreateAuthorRequest request) {
        Author input = request.getAuthor();
        CreateAuthorResponse response = new CreateAuthorResponse();
        response.setAuthor(toAuthor(libraryService.createAuthor(input.getName(), input.getSurname())));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAuthorRequest")
    @ResponsePayload
    public DeleteAuthorResponse deleteAuthor(@RequestPayload DeleteAuthorRequest request) {
        libraryService.deleteAuthor(request.getAuthorId());
        DeleteAuthorResponse response = new DeleteAuthorResponse();
        response.setMessage("Author and their books deleted successfully");
        return response;
    }

    private static Book toBook(BookPersistent entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthorID(entity.getAuthor().getId());
        return book;
    }

    private static Author toAuthor(AuthorPersistent entity) {
        Author author = new Author();
        author.setId(entity.getId());
        author.setName(entity.getName());
        author.setSurname(entity.getSurname());
        return author;
    }
}
