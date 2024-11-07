package webflux.Gradle.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@RequiredArgsConstructor
@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
}
