document.addEventListener("DOMContentLoaded", function () {
    const forms = document.querySelectorAll("form");

    console.log("Validation.js loaded successfully.");

    forms.forEach((form) => {
        form.addEventListener("submit", function (event) {
            let isValid = true;

            console.log("Form submit intercepted by validation.js.");

            const requiredFields = form.querySelectorAll("[data-required]");
            requiredFields.forEach((field) => {
                if (!field.value.trim()) {
                    const message = field.dataset.error || "This field is required.";
                    showError(field, message);
                    isValid = false;
                } else {
                    clearError(field);
                }
            });

            const emailField = form.querySelector("[data-email]");
            if (emailField && emailField.value) {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(emailField.value)) {
                    const message = emailField.dataset.error || "Please enter a valid email address.";
                    showError(emailField, message);
                    isValid = false;
                } else {
                    clearError(emailField);
                }
            }

            const numberFields = form.querySelectorAll("[data-number]");
            numberFields.forEach((field) => {
                if (field.value && isNaN(field.value)) {
                    const message = field.dataset.error || "Please enter a valid number.";
                    showError(field, message);
                    isValid = false;
                } else {
                    clearError(field);
                }
            });

            if (!isValid) {
                event.preventDefault();
            }
        });
    });

    function showError(field, message) {
        clearError(field);

        const errorElement = document.createElement("span");
        errorElement.className = "error";
        errorElement.textContent = message;

        field.parentNode.insertBefore(errorElement, field.nextSibling);

        field.classList.add("error-border");
    }

    function clearError(field) {
        const errorElement = field.nextSibling;

        if (errorElement && errorElement.nodeType === Node.ELEMENT_NODE && errorElement.classList.contains("error")) {
            errorElement.remove();
        }

        field.classList.remove("error-border");
    }
});
