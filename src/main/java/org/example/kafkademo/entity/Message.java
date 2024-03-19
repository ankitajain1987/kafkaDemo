package org.example.kafkademo.entity;

import jakarta.persistence.*;


@Entity
public class Message {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int messageId;

    String msg;

    public void setMessage(String message) {
        this.msg = message;
    }
}
