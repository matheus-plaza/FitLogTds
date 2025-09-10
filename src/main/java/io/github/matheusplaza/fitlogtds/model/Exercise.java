package io.github.matheusplaza.fitlogtds.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "t_exercise")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "muscle_group")
    private String muscleGroup;
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;
}
