
public class Main {

    public static void main(String[] args) {

        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();
        SmsMessageSender sms = new SmsMessageSender();
        PushNotificationSender push = new PushNotificationSender();

        // 1. Urgent notification with Telegram
        System.out.println("\n--- Scenario 1: Urgent + Telegram ---");

        UrgentNotification urgent = new UrgentNotification(telegram, sms);
        urgent.send("@sayazhan", "Your account needs attention!");

        // 2. Encrypted notification with Email
        System.out.println("\n--- Scenario 2: Encrypted + Email ---");

        EncryptedNotification encrypted = new EncryptedNotification(email);
        encrypted.send("user@gmail.com", "My secret message");

        // 3. Batch notification with SMS
        System.out.println("\n--- Scenario 3: Batch + SMS ---");

        ScheduledBatchNotification batch = new ScheduledBatchNotification(sms);
        batch.addRecipient("87011111111");
        batch.addRecipient("87022222222");
        batch.addRecipient("87033333333");

        batch.send(null, "Your class starts at 10 AM.");

        // 4. Runtime provider switching
        System.out.println("\n--- Scenario 4: Runtime Switching ---");

        UrgentNotification switching = new UrgentNotification(telegram, sms);
        switching.send("@sayazhan", "First message");

        switching.setSender(email);
        switching.send("user@gmail.com", "Second message");

        // 5. Telegram unavailable, fallback to SMS
        System.out.println("\n--- Scenario 5: Fallback ---");


        telegram.setAvailable(false);


        UrgentNotification fallback = new UrgentNotification(telegram, sms);
        fallback.send("87011111111", "Telegram is down!");


        telegram.setAvailable(true);


        // 6. Marketing notification with Push
        System.out.println("\n--- Scenario 6: Marketing + Push ---");

        
        MarketingNotification marketing = new MarketingNotification(push);
        marketing.send("device123", "Get 20% off your next order!");
    }
}

