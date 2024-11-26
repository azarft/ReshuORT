// Utility function to display error messages
function displayError(message) {
    const errorMessageElement = document.getElementById("errorMessage");
    if (errorMessageElement) {
      errorMessageElement.textContent = message;
    }
  }
  
  // Function to handle login
  async function loginUser(event) {
    event.preventDefault();
  
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
  
    try {
      const response = await fetch("http://localhost:8080/api/v1/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Login failed");
      }
  
      const data = await response.json();
      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
  
      // Redirect to the dashboard after successful login
      window.location.href = "index.html";
    } catch (error) {
      displayError(error.message);
    }
  }
  
  async function registerUser(event) {
    event.preventDefault();

    // Get all input values
    const username = document.getElementById("username").value.trim();
    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    // Validate inputs
    if (!username || !firstName || !lastName || !email || !password) {
        displayError("All fields are required.");
        return;
    }

    try {
        // Send registration request to the backend
        const response = await fetch("http://localhost:8080/api/v1/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ username, firstName, lastName, email, password }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Registration failed");
        }

        // Redirect to login after successful registration
        alert("Registration successful! Redirecting to login...");
        window.location.href = "login.html";
    } catch (error) {
        displayError(error.message);
    }
}

  
  // Attach event listeners to forms
  document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");
  
    if (loginForm) {
      loginForm.addEventListener("submit", loginUser);
    }
  
    if (registerForm) {
      registerForm.addEventListener("submit", registerUser);
    }
  });
  