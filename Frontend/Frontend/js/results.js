document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("accessToken");
    const resultsTable = document.getElementById("resultsTable");
    const errorMessage = document.getElementById("errorMessage");
  
    // Check if the user is authenticated
    if (!token) {
      window.location.href = "login.html";
      return;
    }
  
    // Load user's test results from the backend
    try {
      const response = await fetch("http://localhost:8080/api/v1/results", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });
  
      if (!response.ok) {
        throw new Error("Failed to load results");
      }
  
      const results = await response.json();
      await renderResults(results);
    } catch (error) {
      errorMessage.textContent = error.message;
    }
  
    // Render the results in the table
    async function renderResults(results) {
      const tbody = resultsTable.querySelector("tbody");
      tbody.innerHTML = "";
  
      for (const result of results) {
        let testName = "Loading...";
        try {
          const testResponse = await fetch(`http://localhost:8080/api/v1/tests/${result.testId}`, {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token.trim()}`,
              "Content-Type": "application/json",
            },
          });
          if (testResponse.ok) {
            const test = await testResponse.json();
            testName = test.testName;
          } else {
            throw new Error("Failed to load test name");
          }
        } catch (error) {
          console.error("Error loading test name:", error);
        }
  
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${testName}</td>
          <td>${result.score}</td>
          <td>${new Date(result.attemptDate).toLocaleDateString()}</td>
          <td><button class="btn btn-primary" onclick="reviewResult(${result.resultId})">Review</button></td>
        `;
        tbody.appendChild(row);
      }
    }
  
    // Review a result (redirect to the review page)
    window.reviewResult = function(resultId) {
      localStorage.setItem("currentResultId", resultId);
      window.location.href = "review.html";
    };
  });
  