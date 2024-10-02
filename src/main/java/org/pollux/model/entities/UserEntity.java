package org.pollux.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
    @Column(nullable = false, name = "username")
    private String username;
    @Column(nullable = false, name = "registeredAt")
    private Date registeredAt;
    @Column(nullable = false, name = "quantityOfByes")
    private int quantityOfByes;
    @Column(nullable = false, name = "hasDiscount")
    private boolean hasDiscount;
}
