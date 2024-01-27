package org.dimawish.changestreams.repository;


import lombok.extern.slf4j.Slf4j;
import org.dimawish.changestreams.repository.entity.Car;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Component
public class CarsMongoChangeStreamListener {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Value("${mongo-change-stream.collectionName}")
    private String collectionName;
    @Value("${mongo-change-stream.operation-types}")
    private List<String> operationTypes;

    public CarsMongoChangeStreamListener(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initChangeStream() {
        ChangeStreamOptions options = buildChangeStreamOptions();
        reactiveMongoTemplate.changeStream(collectionName, options, Car.class)
                .doOnNext(this::handleChangeEvent)
                .subscribe();
    }

    private void handleChangeEvent(ChangeStreamEvent<Car> carChangeStreamEvent) {
        Car carFromChangeEvent = carChangeStreamEvent.getBody();
        log.info("Processing {} event for car with ID: {}",
                carChangeStreamEvent.getOperationType().getValue(),
                carFromChangeEvent.getCarId());
    }

    private ChangeStreamOptions buildChangeStreamOptions() {
        return ChangeStreamOptions.builder()
                .returnFullDocumentOnUpdate()
                .filter(newAggregation(match(where("operationType").in(operationTypes))))
                .build();
    }
}
