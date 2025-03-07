
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import uk.gov.dwp.uc.pairtest.TicketServiceImpl;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    @InjectMocks
    TicketServiceImpl ticketService;

    public TicketServiceTest() {

    }

    @Before
    public void setupBeforeAll() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void shouldThrowExceptionWhenInvalidId() {
        TicketTypeRequest[] ticketTypeRequests = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)

        };
        ticketService.purchaseTickets(0l, ticketTypeRequests);

    }

    @Test(expected = InvalidPurchaseException.class)
    public void shouldReturnFalseWhenAllRequestsInvalid() {

        TicketTypeRequest[] ticketTypeRequests = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)

        };
        ticketService.purchaseTickets(1l, ticketTypeRequests);

    }

    @Test(expected = InvalidPurchaseException.class)
    public void shouldThrowExceptionWhenTooManyTIckets() {
        TicketTypeRequest[] ticketTypeRequests = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 23),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 2)

        };
        ticketService.purchaseTickets(1l, ticketTypeRequests);

    }

    @Test
    public void shouldPassWhenExactly25() {
        TicketTypeRequest[] ticketTypeRequests = new TicketTypeRequest[] {
                new TicketTypeRequest(TicketTypeRequest.Type.ADULT, 23),
                new TicketTypeRequest(TicketTypeRequest.Type.INFANT, 1),
                new TicketTypeRequest(TicketTypeRequest.Type.CHILD, 1)

        };

        assertEquals("Tickets reserved", ticketService.purchaseTickets(1l, ticketTypeRequests));
    }
}
