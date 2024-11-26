document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("accessToken");
    const resultId = localStorage.getItem("currentResultId");
  
    if (!token || !resultId) {
      window.location.href = "results.html";
      return;
    }
  
    try {
      // Fetch user attempts with the given resultId
      const userAttemptsResponse = await fetch(`http://localhost:8080/api/v1/userattempts/${resultId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!userAttemptsResponse.ok) {
        throw new Error("Failed to load user attempts");
      }
  
      const userAttempts = await userAttemptsResponse.json();
  
      if (userAttempts.length === 0) {
        throw new Error("No user attempts found for this result");
      }
  
      // Fetch result details using resultId
      const resultResponse = await fetch(`http://localhost:8080/api/v1/results/${resultId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!resultResponse.ok) {
        throw new Error("Failed to load result details");
      }
  
      const result = await resultResponse.json();
      const testId = result.testId;
      const score = result.score;
  
      if (!testId) {
        throw new Error("Test ID is missing from result");
      }
  
      // Fetch test details using testId
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
      document.getElementById("testName").textContent = testDetails.testName || "N/A";
      document.getElementById("testSubject").textContent = testDetails.subject || "N/A";
      document.getElementById("testScore").textContent = `Score: ${score || 0}`;
  
      // Render the questions and answers for review
      renderReview(userAttempts, token);
    } catch (error) {
      console.error("Error loading review:", error);
    }
  });
  
  async function renderReview(userAttempts, token) {
    const questionsContainer = document.getElementById("questionsContainer");
    questionsContainer.innerHTML = "";
  
    for (const userAttempt of userAttempts) {
      const questionId = userAttempt.questionId;
  
      // Fetch question details using the newly added endpoint
      const questionResponse = await fetch(`http://localhost:8080/api/v1/questions/question/${questionId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!questionResponse.ok) {
        console.error(`Failed to load question details for question ID: ${questionId}`);
        continue; // Skip this question if it fails to load
      }
  
      const question = await questionResponse.json();
  
      // Fetch answers for the question
      const answerResponse = await fetch(`http://localhost:8080/api/v1/answers/${questionId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!answerResponse.ok) {
        console.error(`Failed to load answers for question ID: ${questionId}`);
        continue; // Skip if the answers can't be loaded
      }
  
      const answers = await answerResponse.json();
  
      // Render question and answers
      const questionDiv = document.createElement("div");
      questionDiv.classList.add("question");
  
      const selectedAnswerId = userAttempt.selectedAnswerId;
  
      questionDiv.innerHTML = `
        <p><strong>Question:</strong> ${question.questionText || "No question text available"}</p>
        <div class="answers-container">
          ${answers.map(answer => {
            let answerClass = '';
            if (selectedAnswerId === answer.answerId) {
              answerClass = answer.isCorrect ? 'correct' : 'incorrect';
            }
            return `
              <p class="${answerClass}">
                ${selectedAnswerId === answer.answerId ? "<strong>Your Answer:</strong> " : ""}
                ${answer.answerText}
              </p>
            `;
          }).join('')}
        </div>
        <hr>
      `;
  
      questionsContainer.appendChild(questionDiv);
    }
  }
  