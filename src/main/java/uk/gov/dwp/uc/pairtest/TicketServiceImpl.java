package uk.gov.dwp.uc.pairtest;

import java.util.Arrays;

import thirdparty.paymentgateway.TicketPaymentServiceImpl;
import thirdparty.seatbooking.SeatReservationServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */

    @Override
    public String purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests)
            throws InvalidPurchaseException {

        if (getTotalSeatCount(ticketTypeRequests) > 25) {
            throw new InvalidPurchaseException("You cannot purchase more than 25 tickets at a time");
        }

        if (checkForInvalidTickets(ticketTypeRequests)) {
            throw new InvalidPurchaseException("There needs to be at least 1 adult ticket in any given request");
        }

        if (accountId <= 0) {
            throw new InvalidPurchaseException("The supplied account ID is not valid please try again");
        }

        int[] finalPriceAndSeats = calculateFinalPriceAndSeats(ticketTypeRequests);

        int finalPrice = finalPriceAndSeats[0];
        int noOfSeats = finalPriceAndSeats[1];

        TicketPaymentServiceImpl ticketPaymentService = new TicketPaymentServiceImpl();
        ticketPaymentService.makePayment(accountId, finalPrice);

        SeatReservationServiceImpl seatReservationService = new SeatReservationServiceImpl();
        seatReservationService.reserveSeat(accountId, noOfSeats);

        return "Tickets reserved";

    }

    private boolean checkForInvalidTickets(TicketTypeRequest... ticketTypeRequests) {
        return Arrays.stream(ticketTypeRequests)
                .noneMatch(ticketReq -> ticketReq.getTicketType() == TicketTypeRequest.Type.ADULT);
    }

    private int getTotalSeatCount(TicketTypeRequest... ticketTypeRequests) {
        int ticketTotal = 0;

        for (int i = 0; i < ticketTypeRequests.length; i++) {
            TicketTypeRequest request = ticketTypeRequests[i];

            ticketTotal += request.getNoOfTickets();
        }

        return ticketTotal;
    }

    private int[] calculateFinalPriceAndSeats(TicketTypeRequest... ticketTypeRequests) {
        int finalPrice = 0;
        int seatCount = 0;

        for (TicketTypeRequest request : ticketTypeRequests) {
            if (request.getTicketType() == TicketTypeRequest.Type.ADULT) {
                finalPrice += request.getNoOfTickets() * 25;
                seatCount += request.getNoOfTickets();
            } else if (request.getTicketType() == TicketTypeRequest.Type.CHILD) {
                finalPrice += request.getNoOfTickets() * 15;
                seatCount += request.getNoOfTickets();
            }
        }
        return new int[] { finalPrice, seatCount };
    }

}
