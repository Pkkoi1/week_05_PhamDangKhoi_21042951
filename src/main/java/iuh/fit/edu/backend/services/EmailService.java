package iuh.fit.edu.backend.services;

import iuh.fit.edu.backend.models.Candidate;
import iuh.fit.edu.backend.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Candidate candidate, Job job) {
        // code to send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(candidate.getEmail());
        message.setSubject("Job Application" + job.getJobName());
        message.setText("Dear " + candidate.getFullName() + ",\n\n" +
                "We are pleased to inform you that your application for the position of " + job.getJobName() + " has been received.\n" +
                "We will review your application and get back to you soon.\n\n" +
                "Best regards,\n" +
                "HR Department");
        javaMailSender.send(message);
    }
}
