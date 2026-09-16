public class Question {
    private final int id;
    private final String exam, subject, topic, text;
    private final String[] options;
    private final int correctAnswer;

    public Question(int id, String exam, String subject, String topic,
                    String text, String[] options, int correctAnswer) {
        this.id = id;
        this.exam = exam;
        this.subject = subject;
        this.topic = topic;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public int getId() { return id; }
    public String getExam() { return exam; }
    public String getSubject() { return subject; }
    public String getTopic() { return topic; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public int getCorrectAnswer() { return correctAnswer; }
}
