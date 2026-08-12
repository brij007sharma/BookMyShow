package com.cfs.bookmyshow.service;

import com.cfs.bookmyshow.dto.BookingRequest;
import com.cfs.bookmyshow.entity.*;
import com.cfs.bookmyshow.enums.BookingStatus;
import com.cfs.bookmyshow.repository.BookingRepository;
import com.cfs.bookmyshow.repository.SeatRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final EmailService emailService;

    private final BookingRepository bookingRepository;

    private final SeatRepository seatRepository;

    private final UserService userService;

    private final ShowService showService;


    // ==========================================
    // CREATE BOOKING
    // ==========================================

    @Transactional
    public Booking createBooking(BookingRequest request) {

        // Get currently logged-in user
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user =
                userService.getUserByEmail(email);


        Show show =
                showService.getShowById(request.getShowId());


        // Check if requested seats are already booked
        List<Long> alreadyBookedSeats =
                bookingRepository
                        .findBookedSeatIdsByShowId(show.getId());


        for (Long seatId : request.getSeatIds()) {

            if (alreadyBookedSeats.contains(seatId)) {

                throw new RuntimeException(
                        "Seat with id " +
                                seatId +
                                " is already booked"
                );
            }
        }


        // Get requested seats
        List<Seat> seats =
                seatRepository.findAllById(
                        request.getSeatIds()
                );


        if (seats.size() != request.getSeatIds().size()) {

            throw new RuntimeException(
                    "Some Seats Are Invalid"
            );
        }


        // Calculate price
        double totalPrice =
                seats.size() *
                        show.getTicketPrice();


        // Create booking
        Booking booking =
                Booking.builder()
                        .user(user)
                        .show(show)
                        .seats(seats)
                        .totalPrice(totalPrice)
                        .status(
                                BookingStatus.CONFIRMED
                        )
                        .build();

        Booking savedBooking=bookingRepository.save(booking);

        emailService.sendBookingConfirmation(
                savedBooking
        );


        return savedBooking;
    }


    // ==========================================
    // GET BOOKING BY ID
    // ==========================================

    public Booking getBookingById(Long id) {

        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found with id: " + id
                                )
                        );

        // Check ownership
        validateBookingOwnership(booking);

        return booking;
    }


    // ==========================================
    // GET USER BOOKINGS
    // ==========================================

    public List<Booking> getMyBookings() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        User user =
                userService.getUserByEmail(email);

        return bookingRepository
                .findByUserId(user.getId());
    }


    // ==========================================
    // CANCEL BOOKING
    // ==========================================

    @Transactional
    public Booking cancelBooking(Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found with id: " +
                                                bookingId
                                )
                        );


        validateBookingOwnership(booking);


        booking.setStatus(
                BookingStatus.CANCELLED
        );


        return bookingRepository.save(booking);
    }


    // ==========================================
    // AVAILABLE SEATS
    // ==========================================

    public List<Seat> getAvailableSeats(Long showId) {

        Show show =
                showService.getShowById(showId);


        List<Seat> allSeats =
                seatRepository.findByScreenId(
                        show.getScreen().getId()
                );


        List<Long> bookingSeatIds =
                bookingRepository
                        .findBookedSeatIdsByShowId(
                                showId
                        );


        return allSeats.stream()
                .filter(seat ->
                        !bookingSeatIds.contains(
                                seat.getId()
                        )
                )
                .toList();
    }


    // ==========================================
    // OWNERSHIP CHECK
    // ==========================================

    private void validateBookingOwnership(
            Booking booking
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        String email =
                authentication.getName();


        // ADMIN can access any booking
        boolean isAdmin =
                authentication.getAuthorities()
                        .stream()
                        .anyMatch(
                                authority ->
                                        authority
                                                .getAuthority()
                                                .equals("ROLE_ADMIN")
                        );


        if (isAdmin) {
            return;
        }


        // Normal USER can access only their own booking
        if (!booking.getUser()
                .getEmail()
                .equals(email)) {

            throw new RuntimeException(
                    "You are not authorized to access this booking"
            );
        }
    }
}