package org.dimawish.changestreams.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@Getter
@Setter
@Document("Cars")
public class Car {

    @Id
    private String carId;
    private String manufacturer;
    private String model;
    private Integer age;
    private String color;
}
