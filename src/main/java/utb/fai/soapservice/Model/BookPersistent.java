package utb.fai.soapservice.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookPersistent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorPersistent author;

    public BookPersistent() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public AuthorPersistent getAuthor() { return author; }
    public void setAuthor(AuthorPersistent author) { this.author = author; }
}
