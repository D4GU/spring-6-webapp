package guru.springframework.spring6webapp.bootstrap;

import ch.qos.logback.core.CoreConstants;
import guru.springframework.spring6webapp.domain.Author;
import guru.springframework.spring6webapp.domain.Book;
import guru.springframework.spring6webapp.domain.Publisher;
import guru.springframework.spring6webapp.repository.AuthorRepository;
import guru.springframework.spring6webapp.repository.BookRepository;
import guru.springframework.spring6webapp.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("123456");

        Author john = new Author();
        john.setFirstName("John");
        john.setLastName("Evans");

        Book test = new Book();
        test.setTitle("Testing");
        test.setIsbn("123457");

        Author ericSaved = authorRepository.save(eric);
        Book dddSaved = bookRepository.save(ddd);

        Author johnSaved = authorRepository.save(john);
        Book testSaved = bookRepository.save(test);

        ericSaved.getBooks().add(dddSaved);
        johnSaved.getBooks().add(testSaved);
        dddSaved.getAuthors().add(ericSaved);
        testSaved.getAuthors().add(johnSaved);


        Publisher monopoly = new Publisher();
        monopoly.setPublisherName("Monopoly");
        monopoly.setAddress("Riederbachweg 46");
        monopoly.setCity("Busswil");
        monopoly.setZip(3292);
        monopoly.setState("Bern");

        Publisher monopolySaved = publisherRepository.save(monopoly);
        dddSaved.setPublisher(monopolySaved);
        testSaved.setPublisher(monopolySaved);

        authorRepository.save(ericSaved);
        authorRepository.save(johnSaved);
        bookRepository.save(dddSaved);
        bookRepository.save(testSaved);


        System.out.println("In Bootstrap");
        System.out.println("Author Count:" + authorRepository.count());
        System.out.println("Book Count:" + bookRepository.count());
        System.out.println("Publisher Count:" + publisherRepository.count());

    }
}
