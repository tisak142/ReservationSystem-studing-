package tisak142.reservation_system;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReservationService {

    private final Map<Long, Reservation> reservationMap;
    private final AtomicLong idCounter;

    public ReservationService() {
        reservationMap = new HashMap<>();
        idCounter = new AtomicLong();
    }

    public Reservation getReservationById(Long id) {
        if (!reservationMap.containsKey(id)) {
            throw new NoSuchElementException("Not found reservation by id = " + id);
        }else {
            return reservationMap.get(id);
        }
    }

    public List<Reservation> findAllReservations() {
        return reservationMap.values().stream().toList();
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation.id() != null) {
            throw new IllegalArgumentException("Id should be empty");
        }
        if (reservation.status() != null) {
            throw new IllegalArgumentException("Status should be empty");
        }
        Reservation newReservation = new Reservation(
                idCounter.incrementAndGet(),
                reservation.userId(),
                reservation.roomId(),
                reservation.startDate(),
                reservation.endDate(),
                ReservationStatus.PENDING
        );
        reservationMap.put(idCounter.get(), newReservation);
        return newReservation;
    }
}
