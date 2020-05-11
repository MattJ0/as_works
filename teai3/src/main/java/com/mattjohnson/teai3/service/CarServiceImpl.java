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
public class CarServiceImpl implements CarService {

    private final List<Car> carList;

    public CarServiceImpl() {
        this.carList = new ArrayList<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void addCars() {
        carList.add(new Car(1L, "Pontiac", "Firebird", Color.black));
        carList.add(new Car(2L, "DeLorean", "DMC-12", Color.black));
        carList.add(new Car(3L, "Fiat", "125p", Color.red));
        carList.add(new Car(4L, "Toyota", "Yaris", Color.silver));
        carList.add(new Car(5L, "Opel", "Astra", Color.blue));

    }

    @Override
    public Optional<List<Car>> findAll() {
        if (carList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(carList);
    }

    @Override
    public Optional<Car> findById(long id) {
        return carList.stream().filter(car -> car.getId() == id).findFirst();

    }

    @Override
    public Optional<List<Car>> findByColor(String color) {
        List<Car> carsByColor = new ArrayList<>();
        carList.forEach(car -> {
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
            carList.add(car);
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
            carList.remove(first.get());
            return first;
        }
        return first;
    }

}
