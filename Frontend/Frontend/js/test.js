document.addEventListener("DOMContentLoaded", async () => {
    const testId = localStorage.getItem("currentTestId");
    const token = localStorage.getItem("accessToken");
    const questionsContainer = document.getElementById("questionsContainer");
    const testForm = document.getElementById("testForm");
    const resultContainer = document.getElementById("resultContainer");
    const scoreSpan = document.getElementById("score");

    if (!testId || !token) {
        window.location.href = "dashboard.html"; // Redirect if no test is selected
        return;
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
    testForm.addEventListener("submit", async (e) => {
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

            if (resultResponse.status === 401) {
                console.warn("Token expired. Refreshing token...");
                const newToken = await refreshToken();
                resultResponse = await fetch("http://localhost:8080/api/v1/results", {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${newToken.trim()}`,
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({ testId, score }),
                });
            }

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
                    "Authorization": `Bearer ${localStorage.getItem("accessToken").trim()}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(userAttempts),
            });

            if (attemptResponse.status === 401) {
                console.warn("Token expired. Refreshing token...");
                const newToken = await refreshToken();
                attemptResponse = await fetch("http://localhost:8080/api/v1/userattempts", {
                    method: "POST",
                    headers: {
                        "Authorization": `Bearer ${newToken.trim()}`,
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(userAttempts),
                });
            }

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
    });

    async function refreshToken() {
        const storedRefreshToken = localStorage.getItem("refreshToken");
        try {
            const response = await fetch("http://localhost:8080/api/v1/auth/refreshToken", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ token: storedRefreshToken }),
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem("accessToken", data.accessToken);
                return data.accessToken; // Return the new token
            } else {
                console.error("Failed to refresh token. Redirecting to login...");
                window.location.href = "login.html"; // Redirect to login if refresh fails
            }
        } catch (error) {
            console.error("Error refreshing token:", error);
            window.location.href = "login.html"; // Redirect to login
        }
    }
});
