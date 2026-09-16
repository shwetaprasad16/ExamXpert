import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileManager {
    public static final String DATA_DIR = "data";
    public static final String QUESTIONS_FILE = DATA_DIR + File.separator + "questions.txt";
    public static final String STUDENTS_FILE = DATA_DIR + File.separator + "students.txt";
    public static final String PERFORMANCE_FILE = DATA_DIR + File.separator + "performance.txt";

    public static void initializeDataFiles() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
            createIfMissing(QUESTIONS_FILE, defaultQuestions());
            createIfMissing(STUDENTS_FILE, "");
            createIfMissing(PERFORMANCE_FILE, "");
        } catch (IOException e) {
            System.out.println("Storage initialization error: " + e.getMessage());
        }
    }

    private static void createIfMissing(String file, String content) throws IOException {
        Path path = Paths.get(file);
        if (!Files.exists(path)) Files.writeString(path, content);
    }

    private static String defaultQuestions() {
        return String.join(System.lineSeparator(),
            "1|GATE|DSA|Complexity|What is the time complexity of binary search?|O(n)|O(log n)|O(n^2)|O(1)|2",
            "2|GATE|DSA|Data Structures|Which data structure follows FIFO?|Stack|Queue|Tree|Graph|2",
            "3|GATE|DSA|Sorting|Which sorting algorithm has average O(n log n) complexity?|Bubble Sort|Selection Sort|Merge Sort|Linear Search|3",
            "4|GATE|DSA|Trees|Which BST traversal gives sorted order?|Preorder|Postorder|Inorder|Level order|3",
            "5|GATE|Operating System|Scheduling|Which scheduling algorithm uses a time quantum?|FCFS|SJF|Round Robin|Priority|3",
            "6|GATE|Operating System|Memory|Which technique divides memory into fixed-size blocks?|Paging|Segmentation|Swapping|Compaction|1",
            "7|GATE|Operating System|Processes|Which is a valid process state?|Ready|Compiler|Editor|Loader|1",
            "8|GATE|Operating System|Deadlock|Which is a necessary deadlock condition?|Circular Wait|Compilation|Paging|Caching|1",
            "9|GATE|DBMS|SQL|Which SQL command retrieves data?|SELECT|DELETE|DROP|UPDATE|1",
            "10|GATE|DBMS|Keys|Which key uniquely identifies a record?|Foreign Key|Primary Key|Candidate Key|Composite Key|2",
            "11|GATE|DBMS|Normalization|Normalization mainly reduces which problem?|Redundancy|Security|Processing speed|Network traffic|1",
            "12|GATE|Java|OOP|Which concept lets a class acquire properties of another class?|Encapsulation|Inheritance|Abstraction|Polymorphism|2",
            "13|GATE|Java|Exception Handling|Which keyword explicitly throws an exception?|throws|try|throw|catch|3",
            "14|SSC|Quantitative Aptitude|Percentage|What is 20 percent of 200?|20|30|40|50|3",
            "15|SSC|Reasoning|Series|Find the next number: 2, 4, 8, 16, ?|20|24|32|36|3",
            "16|SSC|English|Grammar|Choose the correct sentence.|He go to school.|He goes to school.|He going school.|He gone school.|2",
            "17|BANKING|Quantitative Aptitude|Profit and Loss|If CP is 100 and SP is 120, what is the profit percentage?|10%|15%|20%|25%|3",
            "18|UPSC|Polity|Constitution|Who is the constitutional head of India?|Prime Minister|President|Chief Justice|Home Minister|2",
            "19|CAT|Quantitative Aptitude|Algebra|If x + 5 = 10, what is x?|3|4|5|6|3",
            "20|CAT|DILR|Logical Reasoning|If all A are B and all B are C, then:|All A are C|No A are C|Some C are not B|None|1"
        );
    }

    public static List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank() || line.startsWith("#")) continue;
                String[] p = line.split("\\|", -1);
                if (p.length != 10) continue;

                int id = Integer.parseInt(p[0].trim());
                int correct = Integer.parseInt(p[9].trim());
                if (correct < 1 || correct > 4) continue;

                String[] options = {p[5], p[6], p[7], p[8]};
                list.add(new Question(id, p[1], p[2], p[3], p[4], options, correct));
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load questions: " + e.getMessage());
        }
        return list;
    }

    public static void saveStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STUDENTS_FILE, true))) {
            bw.write(student.getName() + "|" + student.getEmail());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Could not save student: " + e.getMessage());
        }
    }

    public static void savePerformance(Performance p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PERFORMANCE_FILE, true))) {
            bw.write(p.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Could not save performance: " + e.getMessage());
        }
    }
}
