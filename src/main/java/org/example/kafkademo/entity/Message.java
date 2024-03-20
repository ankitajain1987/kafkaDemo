package org.example.kafkademo.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    int messageId;
    String userName;
    String email;
    String phoneNumber;
}
