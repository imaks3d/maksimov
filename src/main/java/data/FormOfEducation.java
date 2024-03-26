package data;
/**
 * FormOfEducation of StudyGroup represented by Distance_Education, Full_Time_Education
 */
    public enum FormOfEducation {
        DISTANCE_EDUCATION("distance education"),
        FULL_TIME_EDUCATION("full time education"),
        EVENING_CLASSES("evening classes");

        private final String description;

        FormOfEducation(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

