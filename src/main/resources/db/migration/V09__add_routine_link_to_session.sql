ALTER TABLE t_workout_session
ADD COLUMN workout_routine_id BIGINT,
ADD CONSTRAINT fk_session_routine FOREIGN KEY (workout_routine_id) REFERENCES t_workout_routine(id);