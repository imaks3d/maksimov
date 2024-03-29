package сommands;

import data.StudyGroup;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Class that manage collection
 */
public class CollectionManager {
    private final Set<StudyGroup> studyGroups;
    private final LocalDateTime creationDateTime;
    private List<StudyGroup> sortedStudyGroups;
    private static final AtomicInteger nextId = new AtomicInteger(0); // Объявление и инициализация переменной nextId


    public CollectionManager(List<StudyGroup> studyGroups) {
        this.studyGroups = new HashSet<>(studyGroups);
        assignIds();
        updateNextId();
        this.sortedStudyGroups = getSortedCollection();
        creationDateTime = LocalDateTime.now();
    }

    public Set<StudyGroup> getCollection() {
        return new HashSet<>(studyGroups);
    }

//    public List<StudyGroup> getSortedCollection() {
//        List<StudyGroup> sortedList = studyGroups.stream()
//                .sorted(Comparator.comparing(StudyGroup::getCreationDateTime))
//                .collect(Collectors.toList());
//        return new ArrayList<>(sortedList);
//    }
public List<StudyGroup> getSortedCollection() {
    List<StudyGroup> sortedList = studyGroups.stream()
            .sorted(Comparator.comparing(StudyGroup::getCreationDateTime))
            .collect(Collectors.toList());
    // Нумерация элементов в порядке их даты создания
    for (int i = 0; i < sortedList.size(); i++) {
        sortedList.get(i).setId(i + 1);
    }
    return new ArrayList<>(sortedList);
}
    private void assignIds() {
        List<StudyGroup> sortedList = getSortedCollection();
        for (int i = 0; i < sortedList.size(); i++) {
            sortedList.get(i).setId(i + 1);
        }
    }
    private void updateNextId() {
        int maxId = studyGroups.stream()
                .mapToInt(StudyGroup::getId)
                .max()
                .orElse(0); // Если коллекция пуста, устанавливаем значение по умолчанию 0
        nextId.set(maxId + 1); // Устанавливаем следующий id
    }
    /**
     * @return collection creation date
     */
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }
    /**
     * @return collection name
     */
    public String getCollectionName() {
        return studyGroups.getClass().toString();
    }
    /**
     * @return size of collection
     */
    public int getSize() {
        return studyGroups.size();
    }
    /**
     * @param studyGroup new StudyGroup to add to collection
     * @return true if element successfully added to collection, else return false
     */
//    public boolean add(StudyGroup studyGroup) {
//       return studyGroups.add(studyGroup);
//   }
    public boolean add(StudyGroup studyGroup) {
        boolean added = studyGroups.add(studyGroup);
        sortedStudyGroups = getSortedCollection(); // Обновление порядка нумерации
        return added;
    }

    /**
     * @return true if collection contains StudyGroup with id, else return false
     */
   public boolean existElementWithId(Integer id) {
        for (StudyGroup setStudyGroup : studyGroups) {
            if (setStudyGroup.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    /**
     * update StudyGroup with given id with the data from given studyGroup
     */
    public void updateById(int id, StudyGroup studyGroup) {
        for (StudyGroup setStudyGroup : studyGroups) {
            if (setStudyGroup.getId().equals(id)) {
                setStudyGroup.update(studyGroup);
            }
        }
    }
    /**
     * if collection contains element with given id, this element will be removed, else collection will not be changed
     */
    public void removeById(Integer id) {
        studyGroups.removeIf(setStudyGroup -> setStudyGroup.getId().equals(id));
    }
    /**
     * remove all elements from collection
     */
    public void clear() {
        studyGroups.clear();
    }
    /**
     * add a new item to the collection if the distance of the specified route is greater than the minimum distance of studyGroups in the collection
     * @param studyGroup a new studyGroup that will be added if the condition is met correctly
     * @return true if the element is added, return false otherwise
     */
    public boolean addIfMax(StudyGroup studyGroup) {
        if (studyGroups.isEmpty() || studyGroup.getStudentsCount() > studyGroups.stream().mapToInt(StudyGroup::getStudentsCount).max().orElse(studyGroup.getStudentsCount())) {
            return studyGroups.add(studyGroup);
        }
        return false;
    }
    /**
     * remove all studyGroups in collection which distance greater than distance of given studyGroup
     */
    public void removeGreater(StudyGroup studyGroup) {
        studyGroups.removeIf(setStudyGroup -> setStudyGroup.getStudentsCount() > studyGroup.getStudentsCount());
    }
    /**
     * remove all studyGroups in collection which distance lower than distance of given studyGroup
     */
    public void removeLower(StudyGroup studyGroup) {
        studyGroups.removeIf(setStudyGroup -> setStudyGroup.getStudentsCount() < studyGroup.getStudentsCount());
    }

    /**
     * @return studyGroup from collection with minimum coordinates
     */
    public StudyGroup minByCoordinates() {
        Optional<StudyGroup> min = getCollection().stream().min(Comparator.comparingDouble(group -> group.getCoordinates().getX() + group.getCoordinates().getY()));
        return min.orElse(null); // Возвращаем минимальный элемент или null, если коллекция пуста
    }
}

