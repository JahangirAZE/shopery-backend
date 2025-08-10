package az.shopery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.shopery.service.impl.EmailServiceImpl;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;
    @Mock
    private TemplateEngine templateEngine;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "frontendBaseUrl", "https://test-frontend.com");
    }

    @Test
    void sendVerificationCode_shouldSendEmailSuccessfully() throws Exception {
        String to = "user@example.com";
        String name = "Test User";
        String code = "123456";
        String expectedHtmlContent = "<html><body>Verification code: 123456</body></html>";
        MimeMessage realMimeMessage = new MimeMessage((jakarta.mail.Session) null);

        when(javaMailSender.createMimeMessage()).thenReturn(realMimeMessage);
        when(templateEngine.process(eq("verification-email"),
                any(Context.class))).thenReturn(expectedHtmlContent);

        emailService.sendVerificationCode(to, name, code);

        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(templateEngine).process(eq("verification-email"), contextCaptor.capture());
        Context capturedContext = contextCaptor.getValue();
        assertEquals(name, capturedContext.getVariable("userName"));
        assertEquals(code, capturedContext.getVariable("verificationCode"));

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(javaMailSender).send(mimeMessageCaptor.capture());
        MimeMessage sentMessage = mimeMessageCaptor.getValue();

        assertEquals(to, sentMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("Your Shopery Verification Code", sentMessage.getSubject());
    }

    @Test
    void sendVerificationCode_shouldLogAnErrorWhenExceptionIsThrown() {
        String to = "user@example.com";
        String name = "Test User";
        String code = "123456";

        when(templateEngine.process(eq("verification-email"), any(Context.class))).thenThrow(new RuntimeException("Test Exception"));

        emailService.sendVerificationCode(to, name, code);

        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }


    @Test
    void sendPasswordResetLink_shouldSendEmailSuccessfully() throws Exception {
        String to = "user@example.com";
        String name = "Test User";
        String token = "a-very-secure-token";
        String expectedResetUrl = "https://test-frontend.com/reset-password?token=" + token;
        String expectedHtmlContent = "<html><body>Reset Link</body></html>";
        MimeMessage realMimeMessage = new MimeMessage((jakarta.mail.Session) null);

        when(javaMailSender.createMimeMessage()).thenReturn(realMimeMessage);
        when(templateEngine.process(eq("password-reset-email"),
                any(Context.class))).thenReturn(expectedHtmlContent);

        emailService.sendPasswordResetLink(to, name, token);

        ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
        verify(templateEngine).process(eq("password-reset-email"), contextCaptor.capture());
        Context capturedContext = contextCaptor.getValue();
        assertEquals(name, capturedContext.getVariable("userName"));
        assertEquals(expectedResetUrl, capturedContext.getVariable("resetUrl"));

        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(javaMailSender).send(mimeMessageCaptor.capture());
        MimeMessage sentMessage = mimeMessageCaptor.getValue();

        assertEquals(to, sentMessage.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
        assertEquals("Your Shopery Password Reset Link", sentMessage.getSubject());
    }

    @Test
    void sendPasswordResetLink_shouldLogAnErrorWhenExceptionIsThrown() {
        String to = "user@example.com";
        String name = "Test User";
        String token = "a-very-secure-token";

        when(templateEngine.process(eq("password-reset-email"),
                any(Context.class))).thenThrow(new RuntimeException("Something went wrong"));

        emailService.sendPasswordResetLink(to, name, token);

        verify(javaMailSender, never()).send(any(MimeMessage.class));
    }
}
