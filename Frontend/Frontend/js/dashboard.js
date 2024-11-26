document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("accessToken");
    const testsTable = document.getElementById("testsTable").querySelector("tbody");
    const errorMessage = document.getElementById("errorMessage");
  
    if (!token) {
      window.location.href = "login.html"; // Redirect to login if not authenticated
      return;
    }
  
    try {
      const response = await fetch("http://localhost:8080/api/v1/tests", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
        
      });
  
      console.log(token); // Ensure no leading/trailing spaces
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Failed to fetch tests");
      }
  
      const tests = await response.json();
      tests.forEach(test => {
        const row = document.createElement("tr");
  
        row.innerHTML = `
          <td>${test.testName}</td>
          <td>${test.subject}</td>
          <td>${test.timeLimit} mins</td>
          <td><button onclick="startTest(${test.testId})">Start Test</button></td>
        `;
  
        testsTable.appendChild(row);
      });
    } catch (error) {
      errorMessage.textContent = error.message;
    }
  });
  
  function startTest(testId) {
    localStorage.setItem("currentTestId", testId);
    window.location.href = "test.html"; // Redirect to the test-taking page
  }
  