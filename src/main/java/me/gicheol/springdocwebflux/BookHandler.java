package me.gicheol.springdocwebflux;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class BookHandler {

    public Mono<ServerResponse> test() {
        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("Book Spring Boot");

        Book book2 = new Book();
        book2.setIsbn("2345");
        book2.setTitle("Book Spring Framework 5");

        Flux<Book> books = Flux.just(book1, book2);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(books, Book.class);
    }
}
