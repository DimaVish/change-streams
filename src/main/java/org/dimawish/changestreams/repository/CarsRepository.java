package org.dimawish.changestreams.repository;

import org.dimawish.changestreams.repository.entity.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarsRepository extends MongoRepository<Car, String> {
}
