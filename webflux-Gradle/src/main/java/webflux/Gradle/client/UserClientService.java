package webflux.Gradle.client;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.Gradle.model.User;

@Service
@RequiredArgsConstructor
public class UserClientService {

    private final WebClient webClient;

    // Fetch all users
    public Flux<User> getAllUsers() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class);
    }

    // Fetch a specific user by ID
    public Mono<User> getUserById(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class);
    }

    // Create a new user
    public Mono<User> createUser(User user) {
        return webClient.post()
                .uri("/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    // Update an existing user
    public Mono<User> updateUser(Long id, User user) {
        return webClient.put()
                .uri("/users/{id}", id)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(User.class);
    }

    // Delete a user by ID
    public Mono<Void> deleteUser(Long id) {
        return webClient.delete()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
