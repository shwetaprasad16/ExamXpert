import java.util.*;

public class PerformanceManager {
    private final Map<String, Performance> performanceMap = new LinkedHashMap<>();

    public void recordTest(String exam, String subject,
                            List<Question> questions, int[] answers) {
        Map<String, Performance> current = new LinkedHashMap<>();

        for (int i = 0; i < questions.size(); i++) {
            if (answers[i] == -1) continue;

            Question q = questions.get(i);
            String key = exam + "|" + subject + "|" + q.getTopic();

            Performance p = current.computeIfAbsent(
                key, k -> new Performance(exam, subject, q.getTopic())
            );
            p.addResult(answers[i] == q.getCorrectAnswer());
        }

        for (Performance p : current.values()) {
            performanceMap.put(
                p.getExam() + "|" + p.getSubject() + "|" + p.getTopic(), p
            );
            FileManager.savePerformance(p);
        }
    }

    public void showPerformance() {
        if (performanceMap.isEmpty()) {
            System.out.println("No test performance available yet.");
            return;
        }

        for (Performance p : performanceMap.values()) {
            System.out.printf("%s | %s | %s -> %.2f%% (%d/%d)%n",
                p.getExam(), p.getSubject(), p.getTopic(),
                p.getAccuracy(), p.getCorrect(), p.getAttempted());
        }
    }

    public void showWeakTopics() {
        boolean found = false;
        for (Performance p : performanceMap.values()) {
            if (p.getAccuracy() < 60) {
                System.out.printf("Weak Topic: %s (%s) -> %.2f%%%n",
                    p.getTopic(), p.getSubject(), p.getAccuracy());
                found = true;
            }
        }
        if (!found) System.out.println("No weak topics detected yet.");
    }

    public void showStudyPlan() {
        if (performanceMap.isEmpty()) {
            System.out.println("Attempt a test first to generate a study plan.");
            return;
        }

        for (Performance p : performanceMap.values()) {
            double a = p.getAccuracy();
            if (a < 50) {
                System.out.printf("HIGH PRIORITY: %s -> %.2f%%%n", p.getTopic(), a);
                System.out.println("  Revise concepts and solve additional questions.");
            } else if (a < 70) {
                System.out.printf("MEDIUM PRIORITY: %s -> %.2f%%%n", p.getTopic(), a);
                System.out.println("  Practice more questions and revise mistakes.");
            } else {
                System.out.printf("MAINTAIN: %s -> %.2f%%%n", p.getTopic(), a);
                System.out.println("  Continue regular revision.");
            }
        }
    }
}
