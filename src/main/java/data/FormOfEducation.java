package data;
/**
 * FormOfEducation of StudyGroup represented by Distance_Education, Full_Time_Education, Evening_Classes
 */
    public enum FormOfEducation {
        DISTANCE_EDUCATION("distance education"),
        FULL_TIME_EDUCATION("full time education"),
        EVENING_CLASSES("evening classes");

        private final String description;

        FormOfEducation(String description) {
            this.description = description;
        }

    /**
     * get a description of the form of training.
     * @return description of the training form
     */
        public String getDescription() {
            return description;
        }
    }

