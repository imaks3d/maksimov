package data;

import java.util.Comparator;

public class StudyGroupComparatorStudentsCount implements Comparator<StudyGroup> {
    @Override
    public int compare(StudyGroup lhs, StudyGroup rhs) {
        // Сравниваем количество студентов в двух группах
        // и возвращаем результат сравнения
        return Integer.compare(lhs.getStudentsCount(), rhs.getStudentsCount());
    }
}
