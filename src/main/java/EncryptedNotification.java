import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class EncryptedNotification extends Notification {

    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        if (!sender.isAvailable()) {
            System.out.println("Sender is unavailable.");
            return;
        }

        if (message == null) {
            System.out.println("Message cannot be null.");
            return;
        }

        String encodedMessage = Base64.getEncoder()
                .encodeToString(message.getBytes(StandardCharsets.UTF_8));

        boolean success = sender.sendMessage(
                recipient,
                "Encrypted Notification",
                encodedMessage
        );

        if (!success) {
            System.out.println("Failed to send encrypted notification.");
        }
    }
}
