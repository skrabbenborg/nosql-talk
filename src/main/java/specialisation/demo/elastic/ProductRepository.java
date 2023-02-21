package specialisation.demo.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import specialisation.demo.elastic.config.ElasticsearchConfig;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@ConditionalOnBean(ElasticsearchConfig.class)
public class ProductRepository {

    private static final String PRODUCT_INDEX = "products";

    private final ElasticsearchClient elastic;

    public ProductRepository(ElasticsearchClient elastic) {
        this.elastic = elastic;
    }

    @SneakyThrows
    ProductEntity store(ProductRequest request) {
        var entity = ProductEntity.builder()
            .name(request.name())
            .price(request.price())
            .build();

        elastic.index(i -> i
            .index(PRODUCT_INDEX)
            .id(UUID.randomUUID().toString())
            .document(entity)
        );
        log.info("Stored {}", entity);
        return entity;
    }

    // TODO There must be a better way to extract the result
    @SneakyThrows
    List<ProductEntity> fetch(String name) {
        return elastic.search(i -> i
                .index(PRODUCT_INDEX)
                .query(QueryBuilders.match(fn -> fn
                    .field("name")
                    .query(name)
                )),
            ProductEntity.class
        ).hits().hits().stream().map(Hit::source).toList();
    }

    @SneakyThrows
    void delete(String name) {
        elastic.deleteByQuery(i -> i
            .index(PRODUCT_INDEX)
            .query(QueryBuilders.match(fn -> fn
                .field("name")
                .query(name)
            ))
        );
    }
}
