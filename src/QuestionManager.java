import java.util.*;

public class QuestionManager {
    private final List<Question> questions;

    public QuestionManager() {
        questions = FileManager.loadQuestions();
    }

    public List<String> getExams() {
        Set<String> set = new LinkedHashSet<>();
        for (Question q : questions) set.add(q.getExam());
        return new ArrayList<>(set);
    }

    public List<String> getSubjects(String exam) {
        Set<String> set = new LinkedHashSet<>();
        for (Question q : questions)
            if (q.getExam().equalsIgnoreCase(exam)) set.add(q.getSubject());
        return new ArrayList<>(set);
    }

    public List<Question> getQuestions(String exam, String subject) {
        List<Question> result = new ArrayList<>();
        for (Question q : questions)
            if (q.getExam().equalsIgnoreCase(exam)
                    && q.getSubject().equalsIgnoreCase(subject))
                result.add(q);
        return result;
    }

    public int getQuestionCount() {
        return questions.size();
    }
}
