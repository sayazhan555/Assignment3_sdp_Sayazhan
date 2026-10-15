import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class NotificationTest {

    private String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setOut(original);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void telegramSuccess() {
        TelegramMessageSender telegram = new TelegramMessageSender();

        assertTrue(telegram.sendMessage("@user", "Hello", "Test message"));
    }

    @Test
    void telegramUnavailable() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        telegram.setAvailable(false);

        assertFalse(telegram.isAvailable());
        assertFalse(telegram.sendMessage("@user", "Hello", "Test"));
    }

    @Test
    void urgentAddsPrefix() {
        UrgentNotification urgent = new UrgentNotification(
                new TelegramMessageSender(), new SmsMessageSender());

        String output = captureOutput(() ->
                urgent.send("@user", "Important message"));

        assertTrue(output.contains("[URGENT] Important message"));
    }

    @Test
    void fallbackToSms() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        telegram.setAvailable(false);

        UrgentNotification urgent = new UrgentNotification(
                telegram, new SmsMessageSender());

        String output = captureOutput(() ->
                urgent.send("87011111111", "Help!"));

        assertTrue(output.contains("Fallback successful."));
        assertTrue(output.contains("SMS sent successfully."));
    }

    @Test
    void smsAccepts160Characters() {
        SmsMessageSender sms = new SmsMessageSender();

        assertTrue(sms.sendMessage("123", "Test", "A".repeat(160)));
    }

    @Test
    void smsRejectsLongMessage() {
        SmsMessageSender sms = new SmsMessageSender();

        assertFalse(sms.sendMessage("123", "Test", "A".repeat(161)));
    }

    @Test
    void encryptedMessage() {
        EncryptedNotification encrypted =
                new EncryptedNotification(new EmailMessageSender());

        String output = captureOutput(() ->
                encrypted.send("user@mail.com", "Hello"));

        assertTrue(output.contains("SGVsbG8="));
        assertFalse(output.contains("\nHello\n"));
    }

    @Test
    void batchNotification() {
        ScheduledBatchNotification batch =
                new ScheduledBatchNotification(new SmsMessageSender());

        batch.addRecipient("111");
        batch.addRecipient("222");
        batch.addRecipient("333");

        String output = captureOutput(() ->
                batch.send(null, "Test"));

        assertTrue(output.contains("Total: 3"));
        assertTrue(output.contains("Successful: 3"));
        assertTrue(output.contains("Failed: 0"));
    }

    @Test
    void runtimeSwitching() {
        UrgentNotification urgent = new UrgentNotification(
                new TelegramMessageSender(), new SmsMessageSender());

        urgent.setSender(new EmailMessageSender());

        String output = captureOutput(() ->
                urgent.send("user@mail.com", "Test"));

        assertTrue(output.contains("Email sent successfully."));
    }

    @Test
    void pushNotification() {
        PushNotificationSender push = new PushNotificationSender();

        assertTrue(push.sendMessage("device1", "Title", "Message"));
        assertEquals("Push", push.getChannelName());
    }

    @Test
    void marketingNotification() {
        MarketingNotification marketing =
                new MarketingNotification(new PushNotificationSender());

        String output = captureOutput(() ->
                marketing.send("device1", "20% discount"));

        assertTrue(output.contains("Special Offer! 20% discount"));
    }

    @Test
    void fallbackFailure() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        SmsMessageSender sms = new SmsMessageSender();

        telegram.setAvailable(false);
        sms.setAvailable(false);

        UrgentNotification urgent = new UrgentNotification(telegram, sms);

        String output = captureOutput(() ->
                urgent.send("123", "Test"));

        assertTrue(output.contains("Failed to send urgent notification."));
        assertFalse(output.contains("Fallback successful."));
    }
}
