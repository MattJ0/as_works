package com.mattjohnson.teai3.service;

import com.mattjohnson.teai3.model.Car;

import com.mattjohnson.teai3.model.Color;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CarServiceImpl implements CarService {

    public static long countID = 1;

    private final Map<Long, Car> carMap;

    public CarServiceImpl() {
        this.carMap = new HashMap<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addCars() {
        carMap.put(countID, new Car("Pontiac", "Firebird", Color.black));
        carMap.put(countID, new Car("DeLorean", "DMC-12", Color.black));
        carMap.put(countID, new Car("Fiat", "125p", Color.red));
        carMap.put(countID, new Car("Toyota", "Yaris", Color.silver));
        carMap.put(countID, new Car("Opel", "Astra", Color.blue));

    }

    @Override
    public Optional<List<Car>> findAll() {
        List<Car> allCars = new ArrayList<>();
        carMap.forEach((aLong, car) -> allCars.add(car));
        return Optional.of(allCars);
    }

    @Override
    public Optional<Car> findById(long id) {
        return Optional.ofNullable(carMap.get(id));

    }

    @Override
    public Optional<List<Car>> findByColor(String color) {
        List<Car> carsByColor = new ArrayList<>();
        carMap.forEach((aLong, car) -> {
            if (car.getColor().toString().equalsIgnoreCase(color)) {
                carsByColor.add(car);
            }
        });
        if (carsByColor.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carsByColor);
    }

    @Override
    public boolean addCar(Car car) {
        Optional<Car> first = findById(car.getId());
        if(!first.isPresent()) {
            carMap.put(countID, car);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCar(Car newCar) {
        Optional<Car> first = findById(newCar.getId());
        if (first.isPresent()) {
            Car car = first.get();
            car.setMark(newCar.getMark());
            car.setModel(newCar.getModel());
            car.setColor(newCar.getColor());
            return true;
        } else return false;
    }

    @Override
    public boolean modifyCarAttribute(long id, Car patch) {
        Optional<Car> first = findById(id);
        if (first.isPresent()) {
            Car car = first.get();
            if (patch.getColor() != null) {
                car.setColor(patch.getColor());
            }
            if (patch.getMark() != null) {
                car.setMark(patch.getMark());
            }
            if (patch.getModel() != null) {
                car.setModel(patch.getModel());
            }


            return true;
        }
        return false;
    }

    @Override
    public Optional<Car> removeCar(long id) {
        Optional<Car> first = findById(id);
        if (first.isPresent()) {
            carMap.remove(id);
            return first;
        }
        return first;
    }

}
