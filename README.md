# EXAMXPERT
## Competitive Examination Preparation & Performance Analysis System

A pure Java console application for competitive-exam practice, timed mock tests, automatic evaluation, topic-wise performance analysis, and rule-based study recommendations.

### Features
- Student registration
- GATE, SSC, BANKING, UPSC and CAT categories
- Subject selection
- 20-question randomized test
- Next / Previous / Skip
- 20-minute Java Thread timer
- Automatic submission and evaluation
- Score, accuracy, correct/wrong/skipped
- Topic-wise performance
- Weak-topic detection
- Study recommendations
- File-based storage
- Input validation and exception handling

### Java Concepts
OOP, encapsulation, constructors, ArrayList, HashMap/Map, file I/O, exception handling, multithreading, methods, loops and conditionals.

### Run
From the project root:

```bash
javac -d out src/*.java
java -cp out Main
```

### Test
```bash
javac -d out src/*.java tests/EXAMXPERTTest.java
java -cp out EXAMXPERTTest
```

### Project Structure
```text
EXAMXPERT/
├── src/
├── data/
├── tests/
├── screenshots/
├── README.md
├── statement.md
└── .gitignore
```

### Non-Functional Requirements
- Usability
- Performance
- Reliability
- Maintainability
- Scalability
- Resource efficiency
- Error handling

### Future Enhancements
JavaFX GUI, larger question bank, difficulty levels, negative marking, admin question management, detailed history charts and exportable reports.
