public class KafkaEventSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Kafka is unavailable.");
            return false;
        }

        System.out.println("=== Kafka Event ===");
        System.out.println("Topic: notifications");
        System.out.println("Key: " + recipient);
        System.out.println("Title: " + title);
        System.out.println("Payload: " + body);
        System.out.println("Event published successfully.");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}
