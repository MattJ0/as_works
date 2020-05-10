package com.mattjohnson.teai3.service;

import com.mattjohnson.teai3.model.Car;
import com.mattjohnson.teai3.model.Color;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();

    Optional<Car> findById(long id);

    List<Car> findByColor(String color);

    boolean addCar(Car car);

    boolean modifyCar(Car car); //by id??

//    boolean modifyCarAttribute(Car car); //by id??

    Optional<Car> removeCar(Long id); //by id??


}
