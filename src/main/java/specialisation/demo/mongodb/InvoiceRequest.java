package specialisation.demo.mongodb;

import java.time.LocalDate;

record InvoiceRequest(
    String name,
    LocalDate date,
    Long price
) {
}
