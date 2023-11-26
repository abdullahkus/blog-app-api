package com.ak.blogapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

@Table(
        name = "POSTS", uniqueConstraints = {@UniqueConstraint(columnNames = {"TITLE"})}
)
public class Post implements Serializable {
    @Id
    @UuidGenerator
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Fetch(FetchMode.JOIN)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();
}
