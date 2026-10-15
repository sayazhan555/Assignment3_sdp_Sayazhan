public class UrgentNotification extends Notification {

    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender, MessageSender fallbackSender) {
        super(sender);
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentMessage = "[URGENT] " + message;

        if (sender.isAvailable()) {
            boolean success = sender.sendMessage(recipient, "Urgent Notification", urgentMessage);

            if (success) {
                return;
            }
        }

        System.out.println("Primary sender failed. Trying fallback...");

        if (fallbackSender != null && fallbackSender.isAvailable()) {
            boolean success = fallbackSender.sendMessage(recipient, "Urgent Notification", urgentMessage);

            if (success) {
                System.out.println("Fallback successful.");
                return;
            }
        }

        System.out.println("Failed to send urgent notification.");
    }
}
