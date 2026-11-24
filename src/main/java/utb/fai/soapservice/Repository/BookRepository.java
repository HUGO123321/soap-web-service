package utb.fai.soapservice.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import utb.fai.soapservice.Model.BookPersistent;

public interface BookRepository extends JpaRepository<BookPersistent, Long> {
    List<BookPersistent> findByAuthor_Id(Long authorId);
}
