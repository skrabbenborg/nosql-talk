package specialisation.demo.elastic;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import specialisation.demo.elastic.config.ElasticsearchConfig;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@ConditionalOnBean(ElasticsearchConfig.class)
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    ResponseEntity<ProductEntity> createProduct(
        @RequestBody ProductRequest request
    ) {
        return ResponseEntity.ok(repository.store(request));
    }

    @GetMapping
    ResponseEntity<List<ProductEntity>> retrieveProducts(
        @RequestParam String name
    ) {
        return ResponseEntity.ok(repository.fetch(name));
    }

    @DeleteMapping
    ResponseEntity<Void> deleteProduct(
        @RequestParam String name
    ) {
        repository.delete(name);
        return ResponseEntity.ok().build();
    }
}
