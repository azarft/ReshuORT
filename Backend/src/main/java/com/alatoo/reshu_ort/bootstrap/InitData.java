package com.alatoo.reshu_ort.bootstrap;

import com.alatoo.reshu_ort.entities.*;
import com.alatoo.reshu_ort.enums.Role;
import com.alatoo.reshu_ort.repositories.*;
import com.alatoo.reshu_ort.repositories.UserAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private UserAttemptsRepository userAttemptRepository;

    @Override
    public void run(String... args) throws Exception {
        // Add users
        User admin = User.builder()
                .lastName("Azanovaza")
                .firstName("Argena")
                .username("azarft1a")
                .email("admin1@gmailaa.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_ADMIN)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .lastName("Azanov")
                .firstName("Argen")
                .username("azarft")
                .email("user@gmailaa.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        Test test1 = Test.builder()
                .testName("Math Basics")
                .subject("Mathematics")
                .timeLimit(3600L)
                .createdBy(admin)
                .build();
        testRepository.save(test1);

        Test test2 = Test.builder()
                .testName("History Overview")
                .subject("History")
                .timeLimit(3600L)
                .createdBy(user)
                .build();
        testRepository.save(test2);

        addQuestionsAndAnswers(test1);
        addQuestionsAndAnswers(test2);

        Result result1 = Result.builder()
                .user(admin)
                .test(test1)
                .score(80)
                .attemptDate(LocalDateTime.now())
                .build();
        resultRepository.save(result1);

        Result result2 = Result.builder()
                .user(user)
                .test(test2)
                .score(70)
                .attemptDate(LocalDateTime.now())
                .build();
        resultRepository.save(result2);

        addUserAttempts(result1);
        addUserAttempts(result2);
    }

    private void addQuestionsAndAnswers(Test test) {
        for (int i = 1; i <= 10; i++) {
            Question question = Question.builder()
                    .test(test)
                    .questionText("Question " + i + " for " + test.getTestName())
                    .build();
            questionRepository.save(question);

            for (int j = 1; j <= 4; j++) {
                boolean isCorrect = j == 2;
                Answer answer = Answer.builder()
                        .question(question)
                        .answerText("Answer " + j + " for Question " + i)
                        .isCorrect(isCorrect)
                        .build();
                answerRepository.save(answer);
            }
        }
    }

    private void addUserAttempts(Result result) {
        List<Question> questions = questionRepository.getQuestionsByTestTestId(result.getTest().getTestId());
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            Answer selectedAnswer = answerRepository.findFirstByQuestion(question);

            if (selectedAnswer.getIsCorrect()) {
                correctAnswers++;
            }

            UserAttempt userAttempt = UserAttempt.builder()
                    .result(result)
                    .question(question)
                    .selectedAnswer(selectedAnswer)
                    .build();
            userAttemptRepository.save(userAttempt);
        }

        int scorePercentage = (int) ((correctAnswers / (double) totalQuestions) * 100);

        result.setScore(scorePercentage);
        resultRepository.save(result);
    }

}
