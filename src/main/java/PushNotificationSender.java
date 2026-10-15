public class PushNotificationSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Push notification is unavailable.");
            return false;
        }

        System.out.println("=== Push Notification ===");
        System.out.println("Device: " + recipient);
        System.out.println("Title: " + title);
        System.out.println("Message: " + body);
        System.out.println("Push notification sent successfully.");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Push";
    }
}
