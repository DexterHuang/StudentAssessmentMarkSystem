package studentmanagement2;

public class AssessmentTask extends SearchableClass {

    String id;

    float weight;

    int fullMarks;

    String description;

    public AssessmentTask(String id, float weight, int fullMarks, String description) {
        this.id = id;
        this.weight = weight;
        this.fullMarks = fullMarks;
        this.description = description;
    }

    @Override
    public String toString() {
        return id + " " + description;
    }
}
