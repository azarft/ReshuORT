document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("accessToken");
    const resultsTableBody = document.querySelector("#resultsTable tbody");
    const reviewModal = document.getElementById("reviewModal");
    const reviewContent = document.getElementById("reviewContent");
    const closeModalBtn = document.getElementById("closeModal");

    if (!token) {
        window.location.href = "login.html"; // Redirect to login if not authenticated
        return;
    }

    // Fetch and display results
    async function fetchResults() {
        try {
            const response = await fetch("http://localhost:8080/api/v1/results", {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token.trim()}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to fetch results");
            }

            const results = await response.json();
            renderResults(results);
        } catch (error) {
            console.error(error);
        }
    }

    // Render results table
    function renderResults(results) {
        resultsTableBody.innerHTML = "";

        results.forEach(result => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${result.testName}</td>
                <td>${result.score}%</td>
                <td>${new Date(result.attemptDate).toLocaleString()}</td>
                <td><button data-result-id="${result.resultId}" class="review-btn">Review</button></td>
            `;

            resultsTableBody.appendChild(row);
        });

        // Attach review buttons click events
        document.querySelectorAll(".review-btn").forEach(button => {
            button.addEventListener("click", async (e) => {
                const resultId = e.target.getAttribute("data-result-id");
                await fetchReview(resultId);
            });
        });
    }

    // Fetch and render review details
    async function fetchReview(resultId) {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/userattempts/${resultId}`, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${token.trim()}`,
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Failed to fetch review details");
            }

            const attempts = await response.json();
            renderReview(attempts);
        } catch (error) {
            console.error(error);
        }
    }

    // Render review modal
    function renderReview(attempts) {
        reviewContent.innerHTML = ""; // Clear previous content

        attempts.forEach(attempt => {
            const questionDiv = document.createElement("div");
            const isCorrect = attempt.selectedAnswer.isCorrect;

            questionDiv.innerHTML = `
                <p><strong>Question:</strong> ${attempt.question.questionText}</p>
                <p><strong>Your Answer:</strong> <span class="${isCorrect ? 'correct' : 'incorrect'}">${attempt.selectedAnswer.answerText}</span></p>
                ${!isCorrect ? `<p><strong>Correct Answer:</strong> ${attempt.correctAnswer.answerText}</p>` : ""}
                <hr>
            `;

            reviewContent.appendChild(questionDiv);
        });

        reviewModal.style.display = "flex"; // Show modal
    }

    // Close modal
    closeModalBtn.addEventListener("click", () => {
        reviewModal.style.display = "none";
    });

    // Fetch results on load
    fetchResults();
});
