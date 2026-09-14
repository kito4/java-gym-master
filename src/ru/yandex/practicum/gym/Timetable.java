package ru.yandex.practicum.gym;

import java.util.*;


public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.computeIfAbsent(trainingSession.getDayOfWeek(), k -> new HashMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), k -> new ArrayList<>())
                .add(trainingSession);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, Collections.emptyMap());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        return dayMap.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<Integer> getCountByCoaches() {
        Map<String, Integer> coaches = new HashMap<>();
        List<TrainingSession> temp = timetable.values().stream()
                .flatMap(e -> e.values().stream())
                .flatMap(List::stream)
                .toList();
        for (TrainingSession trainingSession : temp) {
            String name = trainingSession.getCoach().getName() + trainingSession.getCoach().getMiddleName() + trainingSession.getCoach().getSurname();
            coaches.put(name, coaches.computeIfAbsent(name, e -> 1) + 1);
        }
        return coaches.values().stream().sorted(Comparator.reverseOrder()).toList();
    }
}

