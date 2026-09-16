import java.util.ArrayList;
import java.util.List;

public class Exam {
    private final String name;
    private final List<String> subjects = new ArrayList<>();

    public Exam(String name) {
        this.name = name;
    }

    public void addSubject(String subject) {
        if (!subjects.contains(subject)) subjects.add(subject);
    }

    public String getName() { return name; }
    public List<String> getSubjects() { return new ArrayList<>(subjects); }
}
