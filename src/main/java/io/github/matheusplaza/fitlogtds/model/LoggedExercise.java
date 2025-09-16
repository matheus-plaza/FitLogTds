package io.github.matheusplaza.fitlogtds.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_logged_exercise")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoggedExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private WorkoutSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "loggedExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoggedSet> sets = new ArrayList<>();
}