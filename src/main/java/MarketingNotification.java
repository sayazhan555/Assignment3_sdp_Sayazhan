public class MarketingNotification extends Notification {

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        if (!sender.isAvailable()) {
            System.out.println("Sender is unavailable.");
            return;
        }

        if (message == null || message.isBlank()) {
            System.out.println("Marketing message cannot be empty.");
            return;
        }

        String marketingMessage = "Special Offer! " + message;

        boolean success = sender.sendMessage(
                recipient,
                "Marketing Notification",
                marketingMessage
        );

        if (!success) {
            System.out.println("Failed to send marketing notification.");
        }
    }
}
