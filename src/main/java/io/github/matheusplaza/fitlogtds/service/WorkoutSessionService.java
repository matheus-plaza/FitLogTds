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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutSessionService {

    private final WorkoutSessionRepository repository;
    private final WorkoutSessionMapper mapper;
    private final UserService userService;
    private final ExerciseService exerciseService;

    //metodo auxiliar
    public WorkoutSession findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Sessao nao encontrada"));
    }

    @Transactional
    public WorkoutSessionDTO saveSession(WorkoutSessionCreateDTO dto) {

        WorkoutSession sessionEntity = buildWorkoutSession(dto);

        List<LoggedExercise> loggedExercises = dto.loggedExercises().stream()
                .map(exerciseDTO -> buildLoggedExercise(exerciseDTO, sessionEntity))
                .collect(Collectors.toList());

        sessionEntity.setLoggedExercises(loggedExercises);

        WorkoutSession savedSession = repository.save(sessionEntity);

        return mapper.toDTO(savedSession);
    }

    //Single Responsibility Principle(Criar um WorkoutSession)
    private WorkoutSession buildWorkoutSession(WorkoutSessionCreateDTO dto) {
        User user = userService.findById(dto.userId());

        WorkoutSession sessionEntity = new WorkoutSession();
        sessionEntity.setSessionDate(dto.sessionDate());
        sessionEntity.setDurationInMinutes(dto.durationInMinutes());
        sessionEntity.setNotes(dto.notes());
        sessionEntity.setUser(user);
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


    //TODO: PROTEGER (DATA TENANCY). Este método deve ser filtrado para retornar dados apenas para o usuário que esta autenticado. Será implementado no checkpoint de segurança
    @Transactional(readOnly = true)
    public List<WorkoutSessionDTO> getListSessions() {
        List<WorkoutSession> sessions = repository.findAllWithExercises();
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
        WorkoutSession session = repository.findByIdWithExercises(id)
                .orElseThrow(() -> new NotFoundException("Sessao nao encontrada"));

        List<LoggedExercise> exercisesWithSets = repository.findLoggedExercisesWithSetsBySessionId(id);

        session.setLoggedExercises(exercisesWithSets);

        return mapper.toDTO(session);
    }

    @Transactional
    public WorkoutSessionDTO updateSession(Long id, WorkoutSessionCreateDTO dto) {

        WorkoutSession sessionEntity = findById(id);

        sessionEntity.setSessionDate(dto.sessionDate());
        sessionEntity.setDurationInMinutes(dto.durationInMinutes());
        sessionEntity.setNotes(dto.notes());

        sessionEntity.getLoggedExercises().clear();

        List<LoggedExercise> loggedExercises = dto.loggedExercises().stream()
                .map(exerciseDTO -> buildLoggedExercise(exerciseDTO, sessionEntity))
                .toList();

        sessionEntity.getLoggedExercises().addAll(loggedExercises);

        //como eu configurei o relacionamento como cascade, ao salvar a sessao, os "filhos" da WorkouSession que são uma lista de LoggedExercise que nela contem uma lista de LoggedSet, ambos sao salvos automaticamente junto com a sessao
        return mapper.toDTO(sessionEntity);
        //transacional fecha a transacao e faz o save automatico
    }

    @Transactional
    public void deleteSession(Long id) {

        WorkoutSession ws = findById(id);
        repository.delete(ws);
    }
}
