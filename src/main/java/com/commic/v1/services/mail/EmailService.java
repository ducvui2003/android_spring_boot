package com.commic.v1.services.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService implements IEmailService {
    /*
     * Mỗi khi gọi phương thức sendMail, một Runnable được gửi đến ExecutorService
     * để thực thi việc gửi email trên một thread riêng biệt. ExecutorService giúp
     * tạo và quản lý các thread để thực hiện công việc gửi email một cách bất
     * đồng bộ
     */
    private final ExecutorService executorService = Executors.newFixedThreadPool(3); // Số lượng thread tùy chọn

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendMailOTP(String to, String opt) {
        String subject = "Xác nhận Mã OTP cho Tài Khoản của Bạn";
        StringBuilder content = new StringBuilder();
        content.append("<p>Chào bạn,</p>");
        content.append("<p>Bạn đã yêu cầu lấy lại mật khẩu cho tài khoản của mình. Dưới đây là mã OTP để xác nhận:</p>");
        content.append("<p>Mã OTP: <b>");
        content.append(opt);
        content.append("</b></p>");
        content.append("<p>Vui lòng nhập mã này vào ứng dụng để tiếp tục quá trình lấy lại mật khẩu.</p>");
        content.append("<p>Nếu bạn không yêu cầu lấy lại mật khẩu, vui lòng bỏ qua email này.</p>");
        content.append("<p>Trân trọng,</p>");
        sendMail(to, subject, content.toString());
    }


    private void sendMail(String to, String subject, String content) {
        executorService.submit(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

//                helper.addHeader("Context-type", "text/HTML; charset=UTF-8");
                helper.setFrom(new InternetAddress(username));
                helper.setTo(new InternetAddress(to));
                helper.setSubject(subject);
                helper.setText(content, true);
                mailSender.send(message);
                System.out.println("Message sent successfully");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        });
    }


}
