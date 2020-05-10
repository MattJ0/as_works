package com.mattjohnson.teai3.controller;

import com.mattjohnson.teai3.model.Car;
import com.mattjohnson.teai3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/api/cars",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarApi {

    private final CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Car>>> getCars() {
        Optional<List<Car>> list = carService.findAll();
        if (list.isPresent()) {
            List<EntityModel<Car>> cars = list.get().stream()
                    .map(car -> new EntityModel<>(car,
                            linkTo(methodOn(CarApi.class).getCarById(car.getId())).withSelfRel(),
                            linkTo(methodOn(CarApi.class).getCars()).withRel("cars")))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new CollectionModel<>(cars,
                            linkTo(methodOn(CarApi.class).getCars()).withSelfRel()));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Car>> getCarById(@PathVariable long id) {
        Optional<Car> first = carService.findById(id);
        if (first.isPresent()) {
            return first
                    .map(car -> new EntityModel<>(car,
                            linkTo(methodOn(CarApi.class).getCarById(car.getId())).withSelfRel(),
                            linkTo(methodOn(CarApi.class).getCars()).withRel("cars")))
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<CollectionModel<EntityModel<Car>>> getCarByColor(@PathVariable String color) {
        Optional<List<Car>> list = carService.findByColor(color);
        if (list.isPresent()) {
            List<EntityModel<Car>> cars = list.get().stream()
                    .map(car -> new EntityModel<>(car,
                            linkTo(methodOn(CarApi.class).getCarById(car.getId())).withSelfRel(),
                            linkTo(methodOn(CarApi.class).getCarByColor(color)).withRel(color + "cars")))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new CollectionModel<>(cars,
                            linkTo(methodOn(CarApi.class).getCars()).withSelfRel()));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/add")
    public ResponseEntity addCar(@Validated @RequestBody Car car) {
        boolean add = carService.addCar(car);
        if (add) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity modifyCar(@Validated @RequestBody Car newCar) {
        boolean modify = carService.modifyCar(newCar);
        if (modify) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/modify/{id}")
    public ResponseEntity modifyAttribute(@PathVariable long id,
                                          @RequestBody Car patch) {
        boolean modify = carService.modifyCarAttribute(id, patch);
        if (modify) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity removeCar(@PathVariable long id) {
        Optional<Car> first = carService.findById(id);
        if (first.isPresent()) {
            return new ResponseEntity<>(carService.removeCar(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
