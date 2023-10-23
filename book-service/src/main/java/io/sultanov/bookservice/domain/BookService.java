package io.sultanov.bookservice.domain;

import io.sultanov.bookservice.schedule.BookJob;
import io.sultanov.feignclients.authorservice.AuthorBookRequest;
import io.sultanov.feignclients.authorservice.AuthorClient;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.quartz.DateBuilder;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final AuthorClient authorClient;
    private final Scheduler scheduler;

    public ResponseEntity<?> createBook(BookDto bookDto) {
        if (!authorClient.checkAuthorExisting(bookDto.getAuthor()))
            return ResponseEntity.badRequest().body("Author does not exist");
        Book book = bookMapper.insertBook(bookDto);
        authorClient.addBookToAuthor(new AuthorBookRequest(book.getAuthor(), book.getId()));
        return ResponseEntity.ok(book);
    }

    public Book getBookById(Integer id) {
        Book book = bookMapper.getBookById(id);
        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return book;
    }

    public List<Book> getBooks() throws SchedulerException {
        List<Book> books = bookMapper.getAllBooks();
        JobDetail job = newJob(BookJob.class)
                .withIdentity("all-books-job")
                .storeDurably()
                .build();
        Trigger trigger = newTrigger()
                .withIdentity("trigger-books-job")
                .startAt(futureDate(1, DateBuilder.IntervalUnit.MILLISECOND))
                .build();
        scheduler.scheduleJob(job, trigger);
        return books;
    }
}
