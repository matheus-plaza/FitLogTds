package io.github.matheusplaza.fitlogtds.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "t_logged_set")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoggedSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private Integer setNumber;

    private Integer repetitions;

    private BigDecimal weight;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "logged_exercise_id", nullable = false)
    private LoggedExercise loggedExercise;
}
