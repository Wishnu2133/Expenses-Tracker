package authservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "Token")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    private String token;
    private Instant tokenExpire;

    // One to One with User Table
    @OneToOne
    @JoinColumn(name = "id",referencedColumnName = "user_id")
    // Store into User table //
    private User user;
}
