package com.mattjohnson.teai3.service;

import com.mattjohnson.teai3.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<List<Car>> findAll();

    Optional<Car> findById(long id);

    Optional<List<Car>> findByColor(String color);

    boolean addCar(Car car);

    boolean modifyCar(Car car);

    boolean modifyCarAttribute(long id, Car car);

    Optional<Car> removeCar(long id);


}
