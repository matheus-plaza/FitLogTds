package io.github.matheusplaza.fitlogtds.service;

import io.github.matheusplaza.fitlogtds.controller.dto.LoggedExerciseCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.LoggedSetCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutSessionCreateDTO;
import io.github.matheusplaza.fitlogtds.controller.dto.WorkoutSessionDTO;
import io.github.matheusplaza.fitlogtds.exceptions.NotFoundException;
import io.github.matheusplaza.fitlogtds.mapper.WorkoutSessionMapper;
import io.github.matheusplaza.fitlogtds.model.*;
import io.github.matheusplaza.fitlogtds.repository.WorkoutSessionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.matheusplaza.fitlogtds.service.UserContextService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository repository;
    private final WorkoutSessionMapper mapper;
    private final ExerciseService exerciseService;
    private final WorkoutRoutineService workoutRoutineService;
    private final UserContextService userContextService;

    //metodo auxiliar
    public WorkoutSession findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Sessao nao encontrada"));
    }

    @Transactional
    public WorkoutSessionDTO saveSession(WorkoutSessionCreateDTO dto) {
        User currentUser = userContextService.getCurrentUser();

        WorkoutSession sessionEntity = buildWorkoutSession(dto, currentUser); // Passar currentUser

        List<LoggedExercise> loggedExercises = dto.loggedExercises().stream()
                .map(exerciseDTO -> buildLoggedExercise(exerciseDTO, sessionEntity))
                .collect(Collectors.toList());

        sessionEntity.setWorkoutRoutine(linkSession(dto.workoutRoutineId()));
        sessionEntity.setLoggedExercises(loggedExercises);

        WorkoutSession savedSession = repository.save(sessionEntity);
        return mapper.toDTO(savedSession);
    }

    //Single Responsibility Principle(buscar a rotina)
    private WorkoutRoutine linkSession(Long id) {
        if (id == null) {
            return null;
        }
        return workoutRoutineService.findById(id);
    }

    //Single Responsibility Principle(Criar um WorkoutSession)
    private WorkoutSession buildWorkoutSession(WorkoutSessionCreateDTO dto, User currentUser) {
        WorkoutSession sessionEntity = new WorkoutSession();
        sessionEntity.setSessionDate(dto.sessionDate());
        sessionEntity.setDurationInMinutes(dto.durationInMinutes());
        sessionEntity.setNotes(dto.notes());
        sessionEntity.setUser(currentUser); // Setar o usuário logado
        return sessionEntity;
    }
    //Single Responsibility Principle(Criar um LoggedExercise)
    private LoggedExercise buildLoggedExercise(LoggedExerciseCreateDTO dto, WorkoutSession session) {
        Exercise exercise = exerciseService.findById(dto.exerciseId());

        LoggedExercise loggedExerciseEntity = new LoggedExercise();
        loggedExerciseEntity.setSession(session);
        loggedExerciseEntity.setExercise(exercise);

        List<LoggedSet> sets = dto.sets().stream()
                .map(setDTO -> buildLoggedSet(setDTO, loggedExerciseEntity))
                .collect(Collectors.toList());

        loggedExerciseEntity.setSets(sets);
        return loggedExerciseEntity;
    }


    //Single Responsibility Principle(Criar um LoggedSet)
    private LoggedSet buildLoggedSet(LoggedSetCreateDTO dto, LoggedExercise parentExercise) {
        LoggedSet setEntity = new LoggedSet();
        setEntity.setSetNumber(dto.setNumber());
        setEntity.setRepetitions(dto.repetitions());
        setEntity.setWeight(dto.weight());
        setEntity.setNotes(dto.notes());
        setEntity.setLoggedExercise(parentExercise);
        return setEntity;
    }

    @Transactional(readOnly = true)
    public List<WorkoutSessionDTO> getListSessions() {
        User currentUser = userContextService.getCurrentUser();
        List<WorkoutSession> sessions = repository.findAllByUserWithExercises(currentUser.getId());
        addSets(sessions);
        return mapper.toDTO(sessions);
    }

    //Single Responsibility Principle(adiciona a lista de sets para cada loggedExercise)
    private void addSets(List<WorkoutSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return;
        }

        List<LoggedExercise> allLoggedExercises = sessions.stream()
                .flatMap(session -> session.getLoggedExercises().stream())
                .toList();

        if (!allLoggedExercises.isEmpty()) {
            repository.findLoggedExercisesWithSets(allLoggedExercises);
        }
    }

    public WorkoutSessionDTO getSession(Long id) {
        User currentUser = userContextService.getCurrentUser();

        WorkoutSession session = repository.findByIdWithExercises(id)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));

        if (!session.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Esta sessão não pertence a qem esta logado.");
        }

        return mapper.toDTO(session);
    }


@Transactional
public WorkoutSessionDTO updateSession(Long id, WorkoutSessionCreateDTO dto) {
    User currentUser = userContextService.getCurrentUser();

    WorkoutSession sessionEntity = findById(id);

    if (!sessionEntity.getUser().getId().equals(currentUser.getId())) {
        throw new RuntimeException("Acesso negado: Você não pode alterar sessões de outro usuário.");
    }

    sessionEntity.setSessionDate(dto.sessionDate());
    sessionEntity.setDurationInMinutes(dto.durationInMinutes());
    sessionEntity.setNotes(dto.notes());

    sessionEntity.getLoggedExercises().clear();

    List<LoggedExercise> loggedExercises = dto.loggedExercises().stream()
            .map(exerciseDTO -> buildLoggedExercise(exerciseDTO, sessionEntity))
            .toList();

    sessionEntity.getLoggedExercises().addAll(loggedExercises);

    return mapper.toDTO(sessionEntity);
}
    @Transactional
    public void deleteSession(Long id) {
        User currentUser = userContextService.getCurrentUser();
        WorkoutSession ws = findById(id);

        if (!ws.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Acesso negado: Você não pode deletar sessões de outro usuário.");
        }

        repository.delete(ws);
    }
}
