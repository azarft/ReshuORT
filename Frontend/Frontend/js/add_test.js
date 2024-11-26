document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("accessToken");
    const addTestForm = document.getElementById("addTestForm");
    const addQuestionButton = document.getElementById("addQuestionButton");
    const questionsContainer = document.getElementById("questionsContainer");
    const errorMessage = document.getElementById("errorMessage");
  
    if (!token) {
      window.location.href = "login.html";
      return;
    }
  
    addQuestionButton.addEventListener("click", () => {
      const questionNumber = questionsContainer.childElementCount + 1;
      const questionDiv = document.createElement("div");
      questionDiv.classList.add("question");
  
      questionDiv.innerHTML = `
        <label>Question ${questionNumber}:</label>
        <input type="text" class="questionText" style="width: 100%; height: 50px;" required>
        <div class="answersContainer">
        </div>
      `;
  
      // Automatically add 4 answer fields to each question
      const answersContainer = questionDiv.querySelector('.answersContainer');
      for (let i = 0; i < 4; i++) {
        const answerDiv = document.createElement('div');
        answerDiv.classList.add('answer');
        answerDiv.innerHTML = `
          <input type="radio" name="correctAnswer${questionNumber}" class="isCorrect" required>
          <label>Answer ${i + 1}:</label>
          <input type="text" class="answerText answer-large" style="width: 100%; height: 40px;" required>
        `;
        answersContainer.appendChild(answerDiv);
      }
      questionsContainer.appendChild(questionDiv);
    });
  
    addTestForm.addEventListener("submit", async (e) => {
      e.preventDefault();
      try {
        const testNameElement = document.getElementById('testName');
        const subjectElement = document.getElementById('subject');
        const timeLimitElement = document.getElementById('timeLimit');
  
        if (!testNameElement || !subjectElement || !timeLimitElement || !testNameElement.value.trim() || !subjectElement.value.trim() || !timeLimitElement.value.trim()) {
          throw new Error("Please fill out all the required fields.");
        }
  
        const testName = testNameElement.value.trim();
        const subject = subjectElement.value.trim();
        const timeLimit = parseInt(timeLimitElement.value);
        const questions = document.querySelectorAll(".question");
  
        // Validate the test before sending to the backend
        if (questions.length < 10) {
          alert("The test must have at least 10 questions.");
          return;
        }
  
        for (let questionElement of questions) {
          const questionText = questionElement.querySelector(".questionText").value.trim();
          if (!questionText) {
            alert("All questions must have text.");
            return;
          }
  
          const answers = questionElement.querySelectorAll(".answer");
          if (answers.length !== 4) {
            alert("Each question must have exactly 4 answers.");
            return;
          }
  
          for (let answerElement of answers) {
            const answerText = answerElement.querySelector(".answerText").value.trim();
            if (!answerText) {
              alert("All answers must have text.");
              return;
            }
          }
  
          const correctAnswers = questionElement.querySelectorAll(".isCorrect:checked");
          if (correctAnswers.length !== 1) {
            alert("Each question must have exactly one correct answer.");
            return;
          }
        }
  
        // Create the test
        const testResponse = await fetch("http://localhost:8080/api/v1/tests", {
          method: "POST",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ testName, subject, timeLimit }),
        });
  
        if (!testResponse.ok) {
          throw new Error("Failed to create test");
        }
  
        const test = await testResponse.json();
        const testId = test.testId;
  
        // Add questions and answers
        for (let questionElement of questions) {
          const questionText = questionElement.querySelector(".questionText").value.trim();
  
          // Create question
          const questionResponse = await fetch("http://localhost:8080/api/v1/questions", {
            method: "POST",
            headers: {
              "Authorization": `Bearer ${token}`,
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ testId, questionText }),
          });
  
          if (!questionResponse.ok) {
            throw new Error("Failed to add question");
          }
  
          const question = await questionResponse.json();
          const questionId = question.questionId;
  
          // Add answers
          const answers = questionElement.querySelectorAll(".answer");
          for (let answerElement of answers) {
            const answerText = answerElement.querySelector(".answerText").value.trim();
            const isCorrect = answerElement.querySelector(".isCorrect").checked;
  
            const answerResponse = await fetch("http://localhost:8080/api/v1/answers", {
              method: "POST",
              headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
              },
              body: JSON.stringify({ questionId, answerText, isCorrect }),
            });
  
            if (!answerResponse.ok) {
              throw new Error("Failed to add answer");
            }
          }
        }
  
        alert("Test successfully added");
        window.location.href = "index.html";
      } catch (error) {
        console.error(error);
        errorMessage.textContent = error.message;
      }
    });
  });
  