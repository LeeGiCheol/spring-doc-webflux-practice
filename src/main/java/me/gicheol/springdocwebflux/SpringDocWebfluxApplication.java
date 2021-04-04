package me.gicheol.springdocwebflux;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class SpringDocWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDocWebfluxApplication.class, args);
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .pathMatchers("/my/hello.html").permitAll()
                .anyExchange()
                .authenticated().and()
                .formLogin();

        return http.build();
    }

    @Bean
    public RouterFunction<ServerResponse> router() {
        return route(GET("/book").and(accept(MediaType.APPLICATION_JSON)),
                req -> {
                    System.out.println(req.headers());
                    Book book1 = new Book();
                    book1.setIsbn("1234");
                    book1.setTitle("Book Spring Boot");

                    Book book2 = new Book();
                    book2.setIsbn("2345");
                    book2.setTitle("Book Spring Framework 5");

                    Flux<Book> books = Flux.just(book1, book2);

                    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(books, Book.class);
                });
    }

}