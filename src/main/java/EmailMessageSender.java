public class EmailMessageSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Email is unavailable.");
            return false;
        }

        System.out.println("=== Email Message ===");
        System.out.println("To: " + recipient);
        System.out.println("Subject: " + title);
        System.out.println();
        System.out.println(body);
        System.out.println();
        System.out.println("Best regards,");
        System.out.println("Notification System");
        System.out.println("Email sent successfully.");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }
}
