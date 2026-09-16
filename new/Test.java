import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Test {
    private final List<Question> questions;
    private final int[] answers;

    public Test(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
        this.answers = new int[questions.size()];
        Arrays.fill(this.answers, -1);
    }

    public List<Question> getQuestions() { return questions; }
    public int[] getAnswers() { return answers; }

    public void setAnswer(int index, int answer) {
        if (index >= 0 && index < answers.length && answer >= 1 && answer <= 4)
            answers[index] = answer;
    }

    public int getAnswer(int index) { return answers[index]; }
}
