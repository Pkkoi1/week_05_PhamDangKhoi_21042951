package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(Candidate candidate, Job job) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(candidate.getEmail());
            message.setSubject("Application Received for " + job.getJobName() + " at " + job.getCompany().getCompName());

            // Generate a random interview date and time
            LocalDateTime interviewDateTime = generateRandomInterviewDateTime();

            message.setText("Dear " + candidate.getFullName() + ",\n\n" +
                    "Thank you for applying for the position of " + job.getJobName() + " at " + job.getCompany().getCompName() + ".\n" +
                    "We have received your application and our team will review it shortly. If your qualifications match our requirements, we will contact you to discuss the next steps.\n\n" +
                    "Your interview is scheduled on " + interviewDateTime.toLocalDate() + " at " + interviewDateTime.toLocalTime() + ".\n\n" +
                    "We appreciate your interest in joining our company and wish you the best of luck in your job search.\n\n" +
                    "Best regards,\n" +
                    "HR Department\n" +
                    job.getCompany().getCompName());
            javaMailSender.send(message);
            return "Email sent successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email: " + e.getMessage();
        }
    }

    private LocalDateTime generateRandomInterviewDateTime() {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        LocalDate interviewDate = today.plusDays(random.nextInt(30) + 1); // Random date within the next month

        int hour, minute;
        if (random.nextBoolean()) {
            hour = 7 + random.nextInt(4);
            minute = random.nextInt(60);
            if (hour == 10 && minute > 30) {
                minute = 30;
            }
        } else {
            hour = 13 + random.nextInt(4);
            minute = random.nextInt(60);
        }

        LocalTime interviewTime = LocalTime.of(hour, minute);
        return LocalDateTime.of(interviewDate, interviewTime);
    }
}