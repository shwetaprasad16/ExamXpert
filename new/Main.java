import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final PerformanceManager performanceManager = new PerformanceManager();
    private static Student currentStudent;

    public static void main(String[] args) {
        FileManager.initializeDataFiles();
        QuestionManager questionManager = new QuestionManager();

        System.out.println("==============================================");
        System.out.println("                 EXAMXPERT");
        System.out.println(" Competitive Examination Preparation System");
        System.out.println("==============================================");
        System.out.println("Questions loaded: " + questionManager.getQuestionCount());

        registerStudent();

        while (true) {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Start Mock Test");
            System.out.println("2. View Performance");
            System.out.println("3. View Weak Topics");
            System.out.println("4. Study Recommendations");
            System.out.println("5. View Student Profile");
            System.out.println("6. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1 -> startTest(questionManager);
                case 2 -> performanceManager.showPerformance();
                case 3 -> performanceManager.showWeakTopics();
                case 4 -> performanceManager.showStudyPlan();
                case 5 -> showProfile();
                case 6 -> {
                    System.out.println("Thank you for using EXAMXPERT.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerStudent() {
        System.out.println("\nSTUDENT REGISTRATION");

        String name;
        do {
            System.out.print("Enter name: ");
            name = sc.nextLine().trim();
        } while (name.isEmpty());

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = sc.nextLine().trim();
            if (email.contains("@") && email.contains(".")) break;
            System.out.println("Enter a valid email.");
        }

        currentStudent = new Student(name, email);
        FileManager.saveStudent(currentStudent);
        System.out.println("Profile saved successfully.");
    }

    private static void showProfile() {
        System.out.println("\nSTUDENT PROFILE");
        System.out.println("Name : " + currentStudent.getName());
        System.out.println("Email: " + currentStudent.getEmail());
    }

    private static void startTest(QuestionManager qm) {
        List<String> exams = qm.getExams();

        System.out.println("\nAVAILABLE EXAMS");
        for (int i = 0; i < exams.size(); i++)
            System.out.println((i + 1) + ". " + exams.get(i));

        int ec = readInt("Select exam: ");
        if (ec < 1 || ec > exams.size()) {
            System.out.println("Invalid exam.");
            return;
        }

        String exam = exams.get(ec - 1);
        List<String> subjects = qm.getSubjects(exam);

        System.out.println("\nSUBJECTS");
        for (int i = 0; i < subjects.size(); i++)
            System.out.println((i + 1) + ". " + subjects.get(i));

        int choice = readInt("Select subject: ");
        if (choice < 1 || choice > subjects.size()) {
            System.out.println("Invalid subject.");
            return;
        }

        String subject = subjects.get(choice - 1);
        List<Question> bank = qm.getQuestions(exam, subject);

        if (bank.isEmpty()) {
            System.out.println("No questions found.");
            return;
        }

        Collections.shuffle(bank);
        List<Question> selected = new ArrayList<>();
        for (int i = 0; i < 20; i++) selected.add(bank.get(i % bank.size()));

        Test test = new Test(selected);

        System.out.println("\nTEST READY");
        System.out.println("Exam: " + exam + " | Subject: " + subject);
        System.out.println("Questions: 20 | Time: 20 minutes");
        System.out.println("Commands: 1-4 answer | N next | P previous | S skip | SUBMIT");

        System.out.print("Press ENTER to start...");
        sc.nextLine();

        TestTimer timer = new TestTimer(20 * 60);
        timer.start();

        int current = 0;
        boolean submitted = false;

        while (!submitted && !timer.isTimeOver()) {
            Question q = selected.get(current);

            System.out.println("\n----------------------------------------------");
            System.out.println("Question " + (current + 1) + " / 20");
            System.out.println("Topic: " + q.getTopic());
            System.out.println("----------------------------------------------");
            System.out.println(q.getText());

            String[] options = q.getOptions();
            for (int i = 0; i < 4; i++)
                System.out.println((i + 1) + ". " + options[i]);

            if (test.getAnswer(current) != -1)
                System.out.println("Saved answer: " + test.getAnswer(current));

            System.out.print("Command: ");
            String input = sc.nextLine().trim();

            if (input.matches("[1-4]")) {
                test.setAnswer(current, Integer.parseInt(input));
                if (current < 19) current++;
            } else if (input.equalsIgnoreCase("N")) {
                if (current < 19) current++;
                else System.out.println("Already at the last question.");
            } else if (input.equalsIgnoreCase("P")) {
                if (current > 0) current--;
                else System.out.println("Already at the first question.");
            } else if (input.equalsIgnoreCase("S")) {
                if (current < 19) current++;
            } else if (input.equalsIgnoreCase("SUBMIT")) {
                submitted = true;
            } else {
                System.out.println("Invalid command.");
            }
        }

        timer.stopTimer();
        evaluate(test, exam, subject);
    }

    private static void evaluate(Test test, String exam, String subject) {
        int correct = 0, wrong = 0, skipped = 0;
        List<Question> questions = test.getQuestions();
        int[] answers = test.getAnswers();

        for (int i = 0; i < questions.size(); i++) {
            if (answers[i] == -1) skipped++;
            else if (answers[i] == questions.get(i).getCorrectAnswer()) correct++;
            else wrong++;
        }

        double accuracy = correct * 100.0 / questions.size();

        System.out.println("\n==============================================");
        System.out.println("                    RESULT");
        System.out.println("==============================================");
        System.out.println("Student  : " + currentStudent.getName());
        System.out.println("Exam     : " + exam);
        System.out.println("Subject  : " + subject);
        System.out.println("Total    : " + questions.size());
        System.out.println("Correct  : " + correct);
        System.out.println("Wrong    : " + wrong);
        System.out.println("Skipped  : " + skipped);
        System.out.printf("Accuracy : %.2f%%%n", accuracy);

        performanceManager.recordTest(exam, subject, questions, answers);
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
