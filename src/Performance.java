public class Performance {
    private final String exam, subject, topic;
    private int attempted, correct;

    public Performance(String exam, String subject, String topic) {
        this.exam = exam;
        this.subject = subject;
        this.topic = topic;
    }

    public void addResult(boolean isCorrect) {
        attempted++;
        if (isCorrect) correct++;
    }

    public double getAccuracy() {
        return attempted == 0 ? 0.0 : correct * 100.0 / attempted;
    }

    public String getExam() { return exam; }
    public String getSubject() { return subject; }
    public String getTopic() { return topic; }
    public int getAttempted() { return attempted; }
    public int getCorrect() { return correct; }

    public String toFileString() {
        return exam + "|" + subject + "|" + topic + "|" + attempted + "|" + correct;
    }
}
