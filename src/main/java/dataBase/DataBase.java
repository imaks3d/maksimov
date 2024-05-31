package dataBase;

import data.*;

import java.util.*;
import java.util.stream.Collectors;

public class DataBase {
    private LinkedHashSet<StudyGroup> studyGroups;
    private final DataBaseManager dataBaseManager;

    public DataBase(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
        this.studyGroups = new LinkedHashSet<>();
        this.loadFromFile();
    }

    private void loadFromFile() {
        try {
            this.studyGroups = this.dataBaseManager.readFromDataBase();
            this.updateIds();
        } catch (Exception var2) {
            System.out.println("Error loading from file: " + var2.getMessage());
        }

    }

    public void save(String user_name) {
        this.dataBaseManager.saveToDataBase(this.studyGroups, user_name);
    }

    private void updateIds() {
        int id = 1;
        IdManager.ListID.clear();

        for (Iterator var3 = this.studyGroups.iterator(); var3.hasNext(); ++id) {
            StudyGroup studyGroup = (StudyGroup) var3.next();
            studyGroup.setId(id);
            IdManager.AddId(id);
        }

    }


    public void add(StudyGroup studyGroup) {
        studyGroup.setId(IdManager.GetNewId());
        IdManager.AddId(studyGroup.getId());
        this.studyGroups.add(studyGroup);
    }

//    public void update(int id, StudyGroup updatedStudyGroup, String username) {
//        Iterator iterator = this.studyGroups.iterator();
//
//        while (iterator.hasNext()) {
//            StudyGroup studyGroup = (StudyGroup) iterator.next();
//            if (studyGroup.getId() == id) {
//                if (studyGroup.getUser_name().equals(username)) {
//                    iterator.remove();
//                    updatedStudyGroup.setId(id);
//                    this.studyGroups.add(updatedStudyGroup);
//                    return;
//                }
//
//                System.out.println("You don't have access to this ID");
//            }
//        }
//
//        System.out.println("Study group with id " + id + " not found.");
//    }
public void update(int id, StudyGroup updatedStudyGroup, String username) {
    LinkedHashSet<StudyGroup> tempSet = new LinkedHashSet<>();
    boolean found = false;

    for (StudyGroup studyGroup : this.studyGroups) {
        if (studyGroup.getId() == id) {
            if (studyGroup.getUser_name().equals(username)) {
                updatedStudyGroup.setId(id);
                tempSet.add(updatedStudyGroup);
                found = true;
            } else {
                System.out.println("You don't have access to this ID");
                return;
            }
        } else {
            tempSet.add(studyGroup);
        }
    }

    if (!found) {
        System.out.println("Study group with id " + id + " not found.");
    } else {
        this.studyGroups.clear();
        this.studyGroups.addAll(tempSet);
        System.out.println("Study group updated successfully.");
    }
}


    //    public void update(int id, StudyGroup updatedStudyGroup, String username) {
//        lock.lock();
//        try {
//            Iterator<StudyGroup> iterator = this.studyGroups.iterator();
//
//            while (iterator.hasNext()) {
//                StudyGroup studyGroup = iterator.next();
//                if (studyGroup.getId() == id) {
//                    if (studyGroup.getUser_name().equals(username)) {
//                        iterator.remove();
//                        updatedStudyGroup.setId(id);
//                        this.studyGroups.add(updatedStudyGroup);
//                        return;
//                    } else {
//                        System.out.println("You don't have access to this ID");
//                        return;
//                    }
//                }
//            }
//
//            System.out.println("StudyGroup with id " + id + " not found.");
//        } finally {
//            lock.unlock();
//        }
//    }
    public ArrayList<String> getUsers() {
        return this.dataBaseManager.getUsers();
    }

    public void removeById(int id, String username) {
        Iterator<StudyGroup> iterator = this.studyGroups.iterator();
        boolean removeCheck = false;
        while (iterator.hasNext()) {
            StudyGroup studyGroup = (StudyGroup) iterator.next();
            if (studyGroup.getId() == id) {
                if (studyGroup.getUser_name().equals(username)) {
                    IdManager.RemoveId(studyGroup.getId());
                    iterator.remove();
                    this.updateIds();
                } else {
                    System.out.println("You don't have access to this ID");
                }

                removeCheck = true;
                break;
            }
        }

        if (!removeCheck) {
            System.out.println("No study group with ID = " + id);
        }
    }

    public LinkedHashSet<StudyGroup> getStudyGroups() {
        return new LinkedHashSet<>(this.studyGroups);
    }

    public String toString() {
        StringBuilder show = new StringBuilder(String.format("There are %d StudyGroup in the collection.\n", this.studyGroups.size()));
        this.studyGroups.forEach((studyGroup) -> {
            show.append(studyGroup).append("\n------------\n");
        });
        return show.toString();
    }

//    public void clear(String userName) {
//        Iterator iterator = this.studyGroups.iterator();
//
//        while (iterator.hasNext()) {
//            StudyGroup studyGroup = (StudyGroup) iterator.next();
//            if (studyGroup.getUser_name().equals(userName)) {
//                IdManager.RemoveId(studyGroup.getId());
//                iterator.remove();
//            }
//        }
//
//        this.updateIds();
//    }
public void clear(String userName) {
    // Удаление элементов, принадлежащих пользователю
    Iterator<StudyGroup> iterator = this.studyGroups.iterator();
    while (iterator.hasNext()) {
        StudyGroup studyGroup = iterator.next();
        if (studyGroup.getUser_name().equals(userName)) {
            IdManager.RemoveId(studyGroup.getId());
            iterator.remove();
        }
    }

    // Переназначение идентификаторов оставшихся элементов
    int newId = 1;
    LinkedHashSet<StudyGroup> tempSet = new LinkedHashSet<>();
    for (StudyGroup studyGroup : this.studyGroups) {
        IdManager.RemoveId(studyGroup.getId());  // Удаление старого ID из менеджера
        studyGroup.setId(newId);
        IdManager.AddId(newId);  // Добавление нового ID в менеджер
        tempSet.add(studyGroup);
        newId++;
    }

    // Обновление коллекции с сохранением порядка элементов
    this.studyGroups.clear();
    this.studyGroups.addAll(tempSet);
}

    public List<StudyGroup> filterContainsName(String name) {
            return this.studyGroups.stream()
                    .filter(studyGroup -> studyGroup.getName().contains(name))
                    .collect(Collectors.toList());
    }

//    public List<StudyGroup> addIfMin(StudyGroup studyGroup) {
//            StudyGroup minGroup = Collections.min(this.studyGroups);
//
//            if (studyGroup.compareTo(minGroup) < 0) {
//                this.studyGroups.add(studyGroup);
//                return new ArrayList<>(this.studyGroups);
//            } else {
//                return new ArrayList<>();
//            }
//    }

//    public List<StudyGroup> addIfMax(StudyGroup studyGroup) {
//            StudyGroup maxGroup = Collections.max(this.studyGroups);
//
//            if (studyGroup.compareTo(maxGroup) > 0) {
//                this.studyGroups.add(studyGroup);
//                return new ArrayList<>(this.studyGroups);
//            } else {
//                return new ArrayList<>();
//            }
//    }
public void addIfMax(StudyGroup studyGroup) throws IllegalArgumentException {
    if (this.studyGroups.isEmpty()) {
        studyGroup.setId(IdManager.GetNewId());
        IdManager.AddId(studyGroup.getId());
        this.studyGroups.add(studyGroup);
    } else {
        if (studyGroup.getStudentsCount() > studyGroups.stream().mapToLong(StudyGroup::getStudentsCount).max().orElse(studyGroup.getStudentsCount())) {
            studyGroups.add(studyGroup);
            studyGroup.setId(IdManager.GetNewId());
            IdManager.AddId(studyGroup.getId());
            this.studyGroups.add(studyGroup);
        } else {
            throw new IllegalArgumentException("not max");
        }
    }
}
    public void addIfMin(StudyGroup studyGroup) throws IllegalArgumentException {
        if (this.studyGroups.isEmpty()) {
            studyGroup.setId(IdManager.GetNewId());
            IdManager.AddId(studyGroup.getId());
            this.studyGroups.add(studyGroup);
        } else {
            if (studyGroup.getStudentsCount() < studyGroups.stream().mapToLong(StudyGroup::getStudentsCount).min().orElse(studyGroup.getStudentsCount())) {
                studyGroups.add(studyGroup);
                studyGroup.setId(IdManager.GetNewId());
                IdManager.AddId(studyGroup.getId());
                this.studyGroups.add(studyGroup);
            } else {
                throw new IllegalArgumentException("not min");
            }
        }
    }


    public String info() {
        return String.format("Collection type: %s\nNumber of elements: %d", this.studyGroups.getClass().getName(), this.studyGroups.size());
    }
}