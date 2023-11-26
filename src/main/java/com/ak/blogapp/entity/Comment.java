package com.ak.blogapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "COMMENTS")
public class Comment implements Serializable {
    @Id
    @UuidGenerator
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "BODY", nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    @ToString.Exclude
    private Post post;
}
