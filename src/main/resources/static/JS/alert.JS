document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        var successMessage = document.getElementById("successMessage");
        var errorMessage = document.getElementById("errorMessage");
        if (successMessage) {
            successMessage.style.display = "none";
        }
        if (errorMessage) {
            errorMessage.style.display = "none";
        }
    }, 5000); // Hide after 5 seconds
});