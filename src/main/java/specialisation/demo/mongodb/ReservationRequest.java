package specialisation.demo.mongodb;

import java.time.LocalDate;

record ReservationRequest(
    String name,
    LocalDate date,
    Long price
) {
}
