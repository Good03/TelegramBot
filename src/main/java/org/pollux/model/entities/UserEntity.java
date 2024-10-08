package org.pollux.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "username", unique = true)
    private String username;
    @Column(nullable = false, name = "registeredAt")
    private LocalDate registeredAt;
    @Column(nullable = false, name = "quantityOfByes")
    private int quantityOfByes;
    @Column(nullable = false, name = "hasDiscount")
    private boolean hasDiscount;
}
