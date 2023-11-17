package base;

public class Feedback {

    private String userId;
    private String feedbackText;

    public Feedback() {
        // Default constructor required for Firebase
    }

    public Feedback(String userId, String feedbackText) {
        this.userId = userId;
        this.feedbackText = feedbackText;
    }

    // Add getters and setters for each field
    // ...
}
