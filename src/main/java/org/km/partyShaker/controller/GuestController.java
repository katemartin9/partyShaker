package org.km.partyShaker.controller;
import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.orders.GuestManager;
import org.km.partyShaker.party.Party;
import org.km.partyShaker.repository.DynamoGuestRepository;
import org.km.partyShaker.repository.DynamoPartyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

@Controller
public class GuestController {
    @GetMapping(value="/guest")
    public String guestForm(Model model) {
        model.addAttribute("guestManager", new GuestManager());
        return "guest";
    }

    @PostMapping("/guest")
    public String guestSubmit(@ModelAttribute GuestManager guestManager, Model model, HttpServletResponse response) throws ParseException {
        String partyCode= guestManager.getActivationCode();
        DynamoDbEnhancedClient client = createDynamoDBClient();
        DynamoPartyRepository repositoryParty = new DynamoPartyRepository(client);
        Party party = repositoryParty.load(partyCode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestampNow = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        DynamoGuestRepository repositoryGuest = new DynamoGuestRepository(client);
        if (party != null &&
                sdf.parse(party.getPartyStart()).before(sdf.parse(timestampNow)) &&
                sdf.parse(party.getPartyEnd()).after(sdf.parse(timestampNow)) &&
                repositoryGuest.getRegisteredGuests(partyCode) < party.getPartySize()
        ) {
            Guest guest = guestManager.createGuest();

            if (!repositoryGuest.load(guest)) repositoryGuest.save(guest);
            model.addAttribute("guest", guest);
            Cookie cookie = new Cookie("guest", guest.getName());
            response.addCookie(cookie);
            return "redirect:/menu";
        }
        return guestForm(model);
    }
}
