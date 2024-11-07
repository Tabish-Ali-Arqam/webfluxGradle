package webflux.Gradle.client;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.Gradle.model.User;

@RestController
@RequestMapping("/client/users")
@RequiredArgsConstructor
public class UserClientController {

    private final UserClientService userClientService;

    @GetMapping
    public Flux<User> getAllUsers() {
        return userClientService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userClientService.getUserById(id);
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userClientService.createUser(user);
    }

    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userClientService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable Long id) {
        return userClientService.deleteUser(id);
    }
}
