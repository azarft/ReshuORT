package com.alatoo.reshu_ort.bootstrap;

import com.alatoo.reshu_ort.entities.*;
import com.alatoo.reshu_ort.enums.Role;
import com.alatoo.reshu_ort.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    @Override
    public void run(String... args) throws Exception {
        // Add users
        User admin = User.builder()
                .lastName("Azanov")
                .firstName("Argen")
                .username("admin")
                .email("admin1@gmailaa.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_ADMIN)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(admin);

        User user = User.builder()
                .lastName("User")
                .firstName("User")
                .username("user")
                .email("user@gmailaa.com")
                .password(passwordEncoder.encode("password"))
                .role(Role.ROLE_USER)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        // Add the Kyrgyz Language Test
        Test kyrgyzTest = Test.builder()
                .testName("Аналогиялар")
                .subject("Кыргыз тили")
                .timeLimit(30L)
                .createdBy(admin)
                .build();
        testRepository.save(kyrgyzTest);

        addKyrgyzLanguageTestQuestions(kyrgyzTest);

        Test analogyTest = Test.builder()
                .testName("Аналогиялар 2")
                .subject("Кыргыз тили")
                .timeLimit(60L)
                .createdBy(admin)
                .build();
        testRepository.save(analogyTest);

        addAnalogyTestQuestions(analogyTest);
    }

    private void addKyrgyzLanguageTestQuestions(Test test) {
        addQuestionWithAnswers(test,
                "Кайсы катардагы кыскартылган сөздөрдүн мүчөлөрү туура уланып жазылган?",
                new String[]{"КРнын, БУУна, ТИМде", "ИИМнин, БУУда, КРдин", "**УАКтын, ЖОЖда, МАИде**", "ФАПта, УКтун, ОМБдин"},
                2);

        addQuestionWithAnswers(test,
                "Төмөндөгү сүйлөмдөрдүн кайсынысында тыныш белгилери туура коюлган?",
                new String[]{"**“Мамлекеттик экономиканын күнгөй-тескейин кылдат иликтөөнүн натыйжасында гана чечим кабыл алууга болот”, – деди эксперт.**", "Эксперт, “Мамлекеттик экономиканын күнгөй-тескейин кылдат иликтөөнүн натыйжасында гана чечим кабыл алууга болот”, – деп эскертти.", "Эксперт, “Мамлекеттик экономиканын күнгөй-тескейин кылдат иликтөөнүн натыйжасында гана чечим кабыл алууга боло тургандыгын” эскертти.", "“Мамлекеттик экономиканын күнгөй-тескейин кылдат иликтөөнүн натыйжасында гана чечим кабыл алууга болот” – деп, эскертти эксперт."},
                0);

        addQuestionWithAnswers(test,
                "Төмөнкү сүйлөмдөрдүн кайсынысында \"менен\" сөзү сыпаттык маанини билдирет?",
                new String[]{"Ал поезд менен кеткен.", "Аким чоң атасы менен жашайт.", "Байкем келери менен жөнөдүк.", "**Аларды урматтоо менен тосуп алдык.**"},
                3);

        addQuestionWithAnswers(test,
                "Сүйлөмдөгү асты сызылган сөздөрдүн кайсынысы айтылып жаткан ойду эске түшүрүү маанисинде колдонулду?",
                new String[]{"Менимче, бул иш көп эмгекти талап кылат.", "Ал ишеничтүү жигит, тилекке каршы, көпчүлүк аны байкабайт.", "**Айтмакчы, баягы мен айткан маселе боюнча ойлондуңбу?**", "Кыскасы, келгендердин баары ыраазы болуп кайтышты."},
                2);

        addQuestionWithAnswers(test,
                "Кайсы сүйлөмдө туура эмес жазылган сөз бар?",
                new String[]{"Менде эки китептер бар.", "**Бизде көп дос бар.**", "Ал мектептерге барды.", "Булар жакын адамдар."},
                1);

        addQuestionWithAnswers(test,
                "Кайсы сүйлөмдө экинчи адамдын жашы эске алынган?",
                new String[]{"Апаңдын жашы канча?", "Анын жашы 30да.", "**Атаңдын жашын сурадым.**", "Бул жерде көп адамдар бар."},
                2);

        addQuestionWithAnswers(test,
                "Кайсы сүйлөмдө биринчи адамдын курагына байланыштуу суроо берилген?",
                new String[]{"**Мен канча жаштамын?**", "Сен канча жаштасың?", "Ал канча жашта?", "Биз канча жаштабыз?"},
                0);

        addQuestionWithAnswers(test,
                "Кайсы сүйлөмдө туура эмес айтылган сөз бар?",
                new String[]{"**Бул жакшы иш эмес.**", "Бул анын үйү.", "Ал жакшы бала.", "Менин машинам бар."},
                0);
    }

    private void addAnalogyTestQuestions(Test test) {
        addQuestionWithAnswers(test,
                "Бут кийим: майлоо",
                new String[]{"казан: коолоо", "**Сакал: алуу**", "Чан: суртуу", "Тиш щетка"},
                1);

        addQuestionWithAnswers(test,
                "Фонетика: тыбыш",
                new String[]{"фразеологизм: соз айкашы", "макал-лакап: соз", "текст: суйлом", "**пунктуация : тыныш белги**"},
                3);

        addQuestionWithAnswers(test,
                "Дальтонизм: коз",
                new String[]{"**Астма: коз**", "Псориаз: бут", "кардиолог: журок", "Лор: мурун"},
                0);

        addQuestionWithAnswers(test,
                "Кыш: лыжа",
                new String[]{"Жаз: велосипед", "Куз: чуркоо", "Жай: скутер", "**Кыш- муз аянтчасы**"},
                3);

        addQuestionWithAnswers(test,
                "Ит: үрүү",
                new String[]{"мышык: миялоо", "**эшек: акырык**", "уй: мөөрөө", "тоок: какылдоо"},
                1);

        addQuestionWithAnswers(test,
                "Китеп: билим",
                new String[]{"тамак: курсак", "жазуу: калем", "**мектеп: тарбия**", "оюн: көңүл ачуу"},
                2);

        addQuestionWithAnswers(test,
                "Жаз: жамгыр",
                new String[]{"жай: ысык", "күз: жалбырак", "**кыш: кар**", "түн: жылдыз"},
                2);

        addQuestionWithAnswers(test,
                "Суу: муз",
                new String[]{"от: күл", "жел: шамал", "**мөмө: мөмөлүү дарак**", "жер: топурак"},
                2);

        addQuestionWithAnswers(test,
                "Кара: ак",
                new String[]{"чоң: кичине", "туура: туура эмес", "жакшы: жаман", "**эски: жаңы**"},
                3);
    }

    private void addQuestionWithAnswers(Test test, String questionText, String[] answers, int correctIndex) {
        Question question = Question.builder()
                .test(test)
                .questionText(questionText)
                .build();
        questionRepository.save(question);

        for (int i = 0; i < answers.length; i++) {
            boolean isCorrect = i == correctIndex;
            String answerText = answers[i].replace("**", ""); // Remove ** for formatting
            Answer answer = Answer.builder()
                    .question(question)
                    .answerText(answerText)
                    .isCorrect(isCorrect)
                    .build();
            answerRepository.save(answer);
        }
    }
}
