package tisak142.reservation_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ReactiveTypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById( @PathVariable("id") Long id) {
        log.info("called getReservationById");
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(reservationService.getReservationById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404)
                    .build();
        }

    }

    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations() {
        log.info("called getAllReservations");
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.findAllReservations());
    }

    @PostMapping
    public ResponseEntity<Reservation> createReservation(
            @RequestBody Reservation reservationToCreate) {
        log.info("Called createReservation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("test-header", "123")
                .body(reservationService.createReservation(reservationToCreate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable("id") Long id,
            @RequestBody Reservation resevationToUpdate
    ) {
        log.info("called updateReservation id={}, reservationToUpdate={}",
                id, resevationToUpdate);
        try {
            var updated = reservationService.updateReservation(id, resevationToUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(updated);
        } catch (NoSuchElementException | IllegalStateException e) {
            return ResponseEntity.status(404)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable("id") Long id
    ) {
        log.info("called deleteReservation id={}", id);
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404)
                    .build();
        }

    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Reservation> approveReservation(
            @PathVariable("id") Long id
    ) {
        log.info("Called approveReservation: id={}", id);
        var reservation = reservationService.approveReservation(id);
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }
}
