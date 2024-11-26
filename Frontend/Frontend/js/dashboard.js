document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("accessToken");
  const addTestButton = document.getElementById("addTestButton");
  const viewResultsButton = document.getElementById("viewResultsButton");
  const testsTable = document.getElementById("testsTable");
  const errorMessage = document.getElementById("errorMessage");

  // Check if the user is authenticated
  if (!token) {
    window.location.href = "login.html";
    return;
  }

  // Redirect to the "Add Test" page
  if (addTestButton) {
    addTestButton.addEventListener("click", () => {
      window.location.href = "add_test.html";
    });
  }

  // Redirect to the "Results" page
  if (viewResultsButton) {
    viewResultsButton.addEventListener("click", () => {
      window.location.href = "results.html";
    });
  }

  // Load available tests from the backend
  async function loadTests() {
    try {
      const response = await fetch("http://localhost:8080/api/v1/tests", {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${token.trim()}`,
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) {
        throw new Error("Failed to load tests");
      }

      const tests = await response.json();
      renderTests(tests);
    } catch (error) {
      errorMessage.textContent = error.message;
    }
  }

  // Render the tests in the table
  function renderTests(tests) {
    const tbody = testsTable.querySelector("tbody");
    tbody.innerHTML = "";

    tests.forEach(test => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${test.testName}</td>
        <td>${test.subject}</td>
        <td>${test.timeLimit} minutes</td>
        <td><button class="btn btn-primary" onclick="startTest(${test.testId})">Start</button></td>
      `;
      tbody.appendChild(row);
    });
  }

  // Start a test (redirect to the test page)
  window.startTest = function(testId) {
    localStorage.setItem("currentTestId", testId);
    window.location.href = "test.html";
  };

  // Load the tests when the page loads
  loadTests();
});
