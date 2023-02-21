package specialisation.demo.elastic;

import lombok.Builder;

@Builder
record ProductEntity(
    String name,
    Integer price
) {
}
