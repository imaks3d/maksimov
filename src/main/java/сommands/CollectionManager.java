package сommands;

import data.StudyGroup;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that manage collection
 */
public class CollectionManager {
    private final Set<StudyGroup> studyGroups;
    private final LocalDate creationDate;

    public CollectionManager(List<StudyGroup> studyGroups) {
        this.studyGroups = new HashSet<>(studyGroups);
        creationDate = LocalDate.now();
    }
    /**
     * @return current collection
     */
    public Set<StudyGroup> getCollection() {
        return new HashSet<>(studyGroups);
   }
    /**
     * Создает ArrayList с копиями элементов из HashSet, отсортированными по ID.
     *
     * @return отсортированный ArrayList
     */
    public List<StudyGroup> getSortedCollection() {
        List<StudyGroup> sortedList = studyGroups.stream()
                .sorted(Comparator.comparingLong(StudyGroup::getId))
                .collect(Collectors.toList());
        return new ArrayList<>(sortedList);
    }
    /**
     * @return collection creation date
     */
    public LocalDate getCreationDate() {
        return creationDate;
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
    public boolean add(StudyGroup studyGroup) {
       return studyGroups.add(studyGroup);
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
        if (studyGroups.isEmpty() || studyGroup.getName().compareTo(studyGroups.stream().max(Comparator.comparing(StudyGroup::getName)).orElse(studyGroup).getName()) > 0) {
            return studyGroups.add(studyGroup);
        }
        return false;
    }
    /**
     * remove all studyGroups in collection which distance greater than distance of given studyGroup
     */
    public void removeGreater(StudyGroup studyGroup) {
        studyGroups.removeIf(setStudyGroup -> setStudyGroup.getName().compareTo(studyGroup.getName()) > 0);
    }
    /**
     * remove all studyGroups in collection which distance lower than distance of given studyGroup
     */
    public void removeLower(StudyGroup studyGroup) {
        studyGroups.removeIf(setStudyGroup -> (setStudyGroup.getName().compareTo(studyGroup.getName()) < 0));
    }
    /**
     * @return studyGroup from collection with minimum coordinates
     */
    public StudyGroup minByCoordinates() {
        Optional<StudyGroup> min = getCollection().stream().min(Comparator.comparingDouble(group -> group.getCoordinates().getX() + group.getCoordinates().getY()));
        return min.orElse(null); // Возвращаем минимальный элемент или null, если коллекция пуста
    }
}

