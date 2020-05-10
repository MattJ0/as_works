package com.mattjohnson.teai3.service;

import com.mattjohnson.teai3.model.Car;
import com.mattjohnson.teai3.model.Color;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService{

    private List<Car> carList;


    public CarServiceImpl() {
        this.carList = new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addCars() {
        carList.add(new Car(1L, "Pontiac", "Firebird", Color.BLACK));
        carList.add(new Car(2L, "DeLorean", "DMC-12", Color.SILVER));
        carList.add(new Car(3L, "Fiat", "125p", Color.RED));
    }


    @Override
    public List<Car> findAll() {
        return carList;
    }

    @Override
    public Optional<Car> findById(long id) {
        return carList.stream().filter(car -> car.getId() == id).findFirst();

    }

    @Override
    public List<Car> findByColor(String color) {
        List<Car> carsByColor = new ArrayList<>();
        carList.forEach(car -> {
            if(car.getColor().toString().equalsIgnoreCase(color)) {
                carsByColor.add(car);
            }
        });
        return carsByColor;
    }

    @Override
    public boolean addCar(Car car) {
        return carList.add(car);
    }

    @Override
    public boolean modifyCar(Car newCar) {
        Optional<Car> first = carList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (first.isPresent()) {
            carList.remove(first.get());
            carList.add(newCar);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean modifyCarAttribute(Car car) {
//
//    }

    @Override
    public Optional<Car> removeCar(Long id) {
       Optional<Car> first = findById(id);
        if (first.isPresent()) {
            carList.remove(first.get());
            return first;
        }
        return first;
    }
}
