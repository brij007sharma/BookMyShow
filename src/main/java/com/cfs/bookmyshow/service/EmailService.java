package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.entity.Booking;
import com.cfs.bookmyshow.entity.Seat;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendBookingConfirmation(Booking booking) {

        try {

            String toEmail =
                    booking.getUser().getEmail();

            System.out.println("--------------------------------");
            System.out.println("EMAIL DEBUG");
            System.out.println("From: " + fromEmail);
            System.out.println("To: " + toEmail);
            System.out.println("Booking ID: " + booking.getId());
            System.out.println("--------------------------------");


            if (fromEmail == null || fromEmail.isBlank()) {
                throw new RuntimeException(
                        "MAIL_USERNAME is missing"
                );
            }


            if (toEmail == null || toEmail.isBlank()) {
                throw new RuntimeException(
                        "User email is missing"
                );
            }


            StringBuilder seats =
                    new StringBuilder();

            for (Seat seat : booking.getSeats()) {

                seats.append(
                        seat.getSeatNumber()
                ).append(" ");
            }


            String subject =
                    "BookMyShow - Booking Confirmation #"
                            + booking.getId();


            String body =
                    "Hello "
                            + booking.getUser().getName()
                            + ",\n\n"

                            + "Your movie booking has been confirmed!\n\n"

                            + "========== BOOKING DETAILS ==========\n\n"

                            + "Booking ID: "
                            + booking.getId()
                            + "\n\n"

                            + "Movie: "
                            + booking.getShow()
                            .getMovie()
                            .getTitle()
                            + "\n\n"

                            + "Show Date: "
                            + booking.getShow()
                            .getShowDate()
                            + "\n\n"

                            + "Show Time: "
                            + booking.getShow()
                            .getStartTime()
                            + "\n\n"

                            + "Theatre: "
                            + booking.getShow()
                            .getScreen()
                            .getTheater()
                            .getName()
                            + "\n\n"

                            + "Seats: "
                            + seats
                            + "\n\n"

                            + "Total Amount: ₹"
                            + booking.getTotalPrice()
                            + "\n\n"

                            + "Status: "
                            + booking.getStatus()
                            + "\n\n"

                            + "======================================\n\n"

                            + "Thank you for booking with BookMyShow!\n\n"

                            + "Enjoy your movie!";


            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setFrom(fromEmail);

            message.setTo(toEmail);

            message.setSubject(subject);

            message.setText(body);


            mailSender.send(message);


            System.out.println(
                    "Booking confirmation email sent successfully!"
            );


        } catch (Exception e) {

            System.out.println(
                    "Failed to send booking confirmation Email"
            );

            e.printStackTrace();
        }
    }
}