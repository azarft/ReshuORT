document.addEventListener("DOMContentLoaded", async () => {
    const testId = localStorage.getItem("currentTestId");
    const token = localStorage.getItem("accessToken");
    const questionsContainer = document.getElementById("questionsContainer");
    const testForm = document.getElementById("testForm");
    const resultContainer = document.getElementById("resultContainer");
    const scoreSpan = document.getElementById("score");
    const timerElement = document.getElementById("timer");

    if (!testId || !token) {
        window.location.href = "dashboard.html"; // Redirect if no test is selected
        return;
    }

    let timeLimit = 0;

    try {
        // Fetch test details to get the time limit
        const testResponse = await fetch(`http://localhost:8080/api/v1/tests/${testId}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token.trim()}`,
                "Content-Type": "application/json",
            },
        });

        if (!testResponse.ok) {
            throw new Error("Failed to load test details");
        }

        const testDetails = await testResponse.json();
        timeLimit = testDetails.timeLimit; // Get the time limit from the test details (in minutes)

        // Start the countdown timer
        startCountdown(timeLimit);
    } catch (error) {
        console.error("Error loading test details:", error);
    }

    async function fetchAnswersForQuestion(questionId) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/answers/${questionId}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token.trim()}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to load answers for question ID ${questionId}`);
            }

            return await response.json();
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    // Fetch Questions
    let allQuestions = [];
    try {
        const response = await fetch(`http://localhost:8080/api/v1/questions/${testId}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token.trim()}`,
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            throw new Error("Failed to load questions");
        }

        const questions = await response.json();
        allQuestions = questions; // Store questions to calculate the score later

        for (const question of questions) {
            const questionDiv = document.createElement("div");
            questionDiv.classList.add("question");

            const answers = await fetchAnswersForQuestion(question.questionId);

            questionDiv.innerHTML = `
                <p>${question.questionText}</p>
                <div class="answers" data-question-id="${question.questionId}">
                    ${answers
                        .map(
                            (answer) => `
                            <label>
                                <input type="radio" name="question-${question.questionId}" value="${answer.answerId}" data-correct="${answer.isCorrect}">
                                ${answer.answerText}
                            </label>
                        `
                        )
                        .join("")}
                </div>
            `;

            questionsContainer.appendChild(questionDiv);
        }
    } catch (error) {
        console.error(error);
        alert("Error loading questions. Please try again.");
    }

    // Submit Test
    testForm.addEventListener("submit", handleTestSubmission);

    function handleTestSubmission(e) {
        e.preventDefault();

        const selectedAnswers = [];
        const answersContainers = document.querySelectorAll(".answers");
        let correctAnswersCount = 0;

        answersContainers.forEach((container) => {
            const questionId = container.getAttribute("data-question-id");
            const selectedAnswer = container.querySelector("input:checked");

            if (selectedAnswer) {
                const isCorrect = selectedAnswer.dataset.correct === "true";
                if (isCorrect) correctAnswersCount++; // Count correct answers

                selectedAnswers.push({
                    questionId: parseInt(questionId),
                    selectedAnswerId: parseInt(selectedAnswer.value),
                });
            }
        });

        // Calculate score percentage
        const totalQuestions = allQuestions.length;
        const score = Math.round((correctAnswersCount / totalQuestions) * 100);

        submitTestResults(score, selectedAnswers);
    }

    async function submitTestResults(score, selectedAnswers) {
        try {
            // Step 1: Create a result with score
            let resultResponse = await fetch("http://localhost:8080/api/v1/results", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token.trim()}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ testId, score }), // Send score with result creation
            });

            if (!resultResponse.ok) {
                throw new Error("Failed to create result");
            }

            const resultData = await resultResponse.json();
            const resultId = resultData.resultId;

            // Step 2: Submit user attempts
            const userAttempts = selectedAnswers.map((answer) => ({
                resultId,
                questionId: answer.questionId,
                selectedAnswerId: answer.selectedAnswerId,
            }));

            let attemptResponse = await fetch("http://localhost:8080/api/v1/userattempts", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token.trim()}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(userAttempts),
            });

            if (!attemptResponse.ok) {
                throw new Error("Failed to submit attempts");
            }

            // Step 3: Display the score
            resultContainer.classList.remove("hidden");
            scoreSpan.textContent = `${score}%`; // Display score in percentage

            // Optionally disable the form after submission
            testForm.classList.add("hidden");
        } catch (error) {
            console.error("Error submitting test:", error);
        }
    }

    function startCountdown(minutes) {
        let timeRemaining = minutes * 60;

        const timerInterval = setInterval(() => {
            const minutes = Math.floor(timeRemaining / 60);
            const seconds = timeRemaining % 60;

            timerElement.textContent = `Time Remaining: ${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;

            if (timeRemaining <= 0) {
                clearInterval(timerInterval);
                alert("Time's up! The test will be automatically submitted.");
                handleTestSubmission(new Event("submit")); // Automatically submit the test
            }

            timeRemaining--;
        }, 1000);
    }
});
