<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>onlineexam</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
  <name>onlineexam</name>

  <properties>
    <java.version>17</java.version>
    <spring.boot.version>3.2.0</spring.boot.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
      <version>${spring.boot.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <version>${spring.boot.version}</version>
    </dependency>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
    <!-- Optional: Jackson datatype -->
    <dependency>
      <groupId>com.fasterxml.jackson.datatype</groupId>
      <artifactId>jackson-datatype-jsr310</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>${spring.boot.version}</version>
      </plugin>
    </plugins>
  </build>
</project>

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
logging.level.org.springframework=INFO

package com.example.onlineexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineExamApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnlineExamApplication.class, args);
    }
}

                   
package com.example.onlineexam.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    public User() {}
    public User(String name, String role) { this.name = name; this.role = role; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}


package com.example.onlineexam.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer durationMinutes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="exam_id")
    private List<Question> questions = new ArrayList<>();

    public Exam() {}
    public Exam(String title, Integer durationMinutes) {
        this.title = title; this.durationMinutes = durationMinutes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}


package com.example.onlineexam.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Double marks = 1.0;
    private Integer correctOptionIndex;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "question_id")
    private List<Option> options = new ArrayList<>();

    public Question() {}
    public Question(String text, Integer correctOptionIndex) {
        this.text = text; this.correctOptionIndex = correctOptionIndex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Integer getCorrectOptionIndex() { return correctOptionIndex; }
    public void setCorrectOptionIndex(Integer correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }
    public List<Option> getOptions() { return options; }
    public void setOptions(List<Option> options) { this.options = options; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }
}


package com.example.onlineexam.model;
import jakarta.persistence.*;

@Entity
public class Option {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    public Option() {}
    public Option(String text) { this.text = text; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
                  


package com.example.onlineexam.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Attempt {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Exam exam;

    private Double score;
    private LocalDateTime attemptedAt;

    public Attempt() {}
    public Attempt(User student, Exam exam, Double score) {
        this.student = student; this.exam = exam; this.score = score;
        this.attemptedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }
    public Exam getExam() { return exam; }
    public void setExam(Exam exam) { this.exam = exam; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public LocalDateTime getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(LocalDateTime attemptedAt) { this.attemptedAt = attemptedAt; }
}



package com.example.onlineexam.repository;
import com.example.onlineexam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

                   
package com.example.onlineexam.repository;
import com.example.onlineexam.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}


package com.example.onlineexam.repository;
import com.example.onlineexam.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}


package com.example.onlineexam.dto;
public class CreateExamRequest {
    private String title;
    private Integer durationMinutes;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
}
                   

package com.example.onlineexam.dto;
import java.util.List;

public class AddQuestionRequest {
    private String text;
    private List<String> options;
    private Integer correctOptionIndex;
    private Double marks = 1.0;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
    public Integer getCorrectOptionIndex() { return correctOptionIndex; }
    public void setCorrectOptionIndex(Integer correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }
    public Double getMarks() { return marks; }
    public void setMarks(Double marks) { this.marks = marks; }
}


package com.example.onlineexam.dto;
import java.util.Map;

public class SubmitAttemptRequest {
    // map questionId -> selectedOptionIndex (0-based)
    private Map<Long, Integer> answers;

    public Map<Long, Integer> getAnswers() { return answers; }
    public void setAnswers(Map<Long, Integer> answers) { this.answers = answers; }
}


package com.example.onlineexam.dto;
public class AttemptResultResponse {
    private Long attemptId;
    private Double score;
    private Double maxScore;

    public AttemptResultResponse(Long attemptId, Double score, Double maxScore) {
        this.attemptId = attemptId; this.score = score; this.maxScore = maxScore;
    }

    public Long getAttemptId() { return attemptId; }
    public Double getScore() { return score; }
    public Double getMaxScore() { return maxScore; }
}


package com.example.onlineexam.service;
import com.example.onlineexam.dto.AddQuestionRequest;
import com.example.onlineexam.dto.CreateExamRequest;
import com.example.onlineexam.model.Exam;
import com.example.onlineexam.model.Option;
import com.example.onlineexam.model.Question;
import com.example.onlineexam.repository.ExamRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ExamService {
    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam createExam(CreateExamRequest req) {
        Exam exam = new Exam(req.getTitle(), req.getDurationMinutes());
        return examRepository.save(exam);
    }

    public Optional<Exam> findById(Long id) {
        return examRepository.findById(id);
    }

    public Exam addQuestion(Long examId, AddQuestionRequest req) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
        Question q = new Question(req.getText(), req.getCorrectOptionIndex());
        q.setMarks(req.getMarks());
        for (String o : req.getOptions()) {
            q.getOptions().add(new Option(o));
        }
        exam.getQuestions().add(q);
        return examRepository.save(exam);
    }
}


package com.example.onlineexam.service;
import com.example.onlineexam.dto.SubmitAttemptRequest;
import com.example.onlineexam.model.*;
import com.example.onlineexam.repository.AttemptRepository;
import com.example.onlineexam.repository.ExamRepository;
import com.example.onlineexam.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;

    public AttemptService(AttemptRepository attemptRepository, ExamRepository examRepository, UserRepository userRepository) {
        this.attemptRepository = attemptRepository;
        this.examRepository = examRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Attempt submitAttempt(Long studentId, Long examId, SubmitAttemptRequest req) {
        User student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new RuntimeException("Exam not found"));

        double totalScore = 0.0;
        double maxScore = 0.0;
        Map<Long, Integer> answers = req.getAnswers();

        for (Question q : exam.getQuestions()) {
            maxScore += q.getMarks();
            Integer selectedIndex = answers == null ? null : answers.get(q.getId());
            if (selectedIndex != null && q.getCorrectOptionIndex() != null) {
                if (selectedIndex.equals(q.getCorrectOptionIndex())) {
                    totalScore += q.getMarks();
                }
            }
        }

        Attempt attempt = new Attempt(student, exam, totalScore);
        attempt = attemptRepository.save(attempt);
        return attempt;
    }
}



package com.example.onlineexam.controller;
import com.example.onlineexam.dto.AddQuestionRequest;
import com.example.onlineexam.dto.CreateExamRequest;
import com.example.onlineexam.model.Exam;
import com.example.onlineexam.service.ExamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ExamService examService;

    public AdminController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/exams")
    public ResponseEntity<Exam> createExam(@RequestBody CreateExamRequest req) {
        Exam created = examService.createExam(req);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<Exam> addQuestion(@PathVariable Long examId, @RequestBody AddQuestionRequest req) {
        Exam updated = examService.addQuestion(examId, req);
        return ResponseEntity.ok(updated);
    }
}


package com.example.onlineexam.controller;
import com.example.onlineexam.model.Exam;
import com.example.onlineexam.repository.ExamRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {
    private final ExamRepository examRepository;

    public PublicController(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @GetMapping("/exams")
    public List<Exam> getExams() {
        return examRepository.findAll();
    }

    @GetMapping("/exams/{id}")
    public Exam getExam(@PathVariable Long id) {
        return examRepository.findById(id).orElseThrow(() -> new RuntimeException("Exam not found"));
    }
}



package com.example.onlineexam.controller;
import com.example.onlineexam.dto.SubmitAttemptRequest;
import com.example.onlineexam.dto.AttemptResultResponse;
import com.example.onlineexam.model.Attempt;
import com.example.onlineexam.service.AttemptService;
import com.example.onlineexam.repository.ExamRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final AttemptService attemptService;
    private final ExamRepository examRepository;

    public StudentController(AttemptService attemptService, ExamRepository examRepository) {
        this.attemptService = attemptService;
        this.examRepository = examRepository;
    }

    @PostMapping("/{studentId}/attempts")
    public AttemptResultResponse submitAttempt(@PathVariable Long studentId,
                                               @RequestParam Long examId,
                                               @RequestBody SubmitAttemptRequest req) {
        Attempt attempt = attemptService.submitAttempt(studentId, examId, req);

        // compute max score
        double maxScore = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"))
                .getQuestions()
                .stream().mapToDouble(q -> q.getMarks()).sum();

        return new AttemptResultResponse(attempt.getId(), attempt.getScore(), maxScore);
    }
}


package com.example.onlineexam;
import com.example.onlineexam.model.Exam;
import com.example.onlineexam.model.Option;
import com.example.onlineexam.model.Question;
import com.example.onlineexam.model.User;
import com.example.onlineexam.repository.ExamRepository;
import com.example.onlineexam.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {
    private final UserRepository userRepository;
    private final ExamRepository examRepository;

    public DataLoader(UserRepository userRepository, ExamRepository examRepository) {
        this.userRepository = userRepository;
        this.examRepository = examRepository;
    }

    @PostConstruct
    public void load() {
        // create sample users
        User admin = new User("Alice Admin", "ADMIN");
        User student = new User("Bob Student", "STUDENT");
        userRepository.save(admin);
        userRepository.save(student);

        // sample exam
        Exam exam = new Exam("Java Basics", 30);
        Question q1 = new Question("What is JVM?", 1); // assume option 1 is correct
        q1.getOptions().add(new Option("Java Variable Machine"));
        q1.getOptions().add(new Option("Java Virtual Machine"));
        q1.getOptions().add(new Option("Just Very Much"));

        Question q2 = new Question("Which keyword is used for inheritance in Java?", 0);
        q2.getOptions().add(new Option("extends"));
        q2.getOptions().add(new Option("implements"));
        q2.getOptions().add(new Option("inherits"));

        exam.getQuestions().add(q1);
        exam.getQuestions().add(q2);
        examRepository.save(exam);
    }
}
                 
