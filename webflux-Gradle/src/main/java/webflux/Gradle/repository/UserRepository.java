package webflux.Gradle.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import webflux.Gradle.model.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
