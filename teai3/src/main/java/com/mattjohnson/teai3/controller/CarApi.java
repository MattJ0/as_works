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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/cars",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CarApi {

    private final CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

//    @GetMapping("/all")
//    public ResponseEntity<CollectionModel<EntityModel<Car>>> getCars() {
//        Optional<List<Car>> list = carService.findAll();
//        if (!list.get().isEmpty()) {
//            List<EntityModel<Car>> cars = list.get().stream()
//                    .map(car -> new EntityModel<>(car,
//                            linkTo(methodOn(CarApi.class).getCarById(car.getId())).withSelfRel(),
//                            linkTo(methodOn(CarApi.class).getCars()).withRel("cars")))
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(
//                    new CollectionModel<>(cars,
//                            linkTo(methodOn(CarApi.class).getCars()).withSelfRel()));
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping("/all")
    public List<Car> getCars() {
        return carService.findAll().get();
    }

    @GetMapping("/id")
    public ResponseEntity<EntityModel<Car>> getCarById(@Validated @RequestBody long id) {
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

    @GetMapping("/color")
    public ResponseEntity<CollectionModel<EntityModel<Car>>> getCarByColor(@Validated @RequestBody String color) {
        Optional<List<Car>> list = carService.findByColor(color);
        if (list.isPresent()) {
            List<EntityModel<Car>> cars = list.get().stream()
                    .map(car -> new EntityModel<>(car,
                            linkTo(methodOn(CarApi.class).getCarById(car.getId())).withSelfRel(),
                            linkTo(methodOn(CarApi.class).getCarByColor(color)).withRel(color + "_cars")))
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
            return new ResponseEntity<>(car, HttpStatus.CREATED);
        } else return new ResponseEntity<>("element with that id is already exists", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update")
    public ResponseEntity updateCar(@Validated @RequestBody Car newCar) {
        boolean modify = carService.updateCar(newCar);
        if (modify) {
            return new ResponseEntity<>(newCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/modify")
    public ResponseEntity modifyAttribute(@RequestBody long id, Car patch) {
        boolean modify = carService.modifyCarAttribute(id, patch);
        if (modify) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/remove")
    public ResponseEntity removeCar(@RequestBody long id) {
        Optional<Car> delete = carService.removeCar(id);
        return delete.map(car -> new ResponseEntity(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }


}
