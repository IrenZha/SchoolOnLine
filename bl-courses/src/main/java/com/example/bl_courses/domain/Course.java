package com.example.bl_courses.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private UUID avatarId;
    private UUID authorId;
    private String title;

    @Column(columnDefinition = "text")
    private String description;
    private Double price;
    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
