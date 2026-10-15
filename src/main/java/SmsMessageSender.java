public class SmsMessageSender implements MessageSender {

    private boolean available = true;
    private static final int MAX_LENGTH = 160;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("SMS is unavailable.");
            return false;
        }

        String message = body;

        if (message == null) {
            System.out.println("SMS message cannot be null.");
            return false;
        }

        if (message.length() > MAX_LENGTH) {
            System.out.println("SMS is too long. Maximum 160 characters.");
            return false;
        }

        System.out.println("=== SMS Message ===");
        System.out.println("To: " + recipient);
        System.out.println("Message: " + message);
        System.out.println("SMS sent successfully.");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}
