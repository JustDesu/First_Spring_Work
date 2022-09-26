package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.compassLogic.Compass;
import ru.appline.compassLogic.CompassModel;
import ru.appline.logic.Crutch;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {

    private static final CompassModel compassModel = CompassModel.getInstance();
    private static final PetModel petModel = PetModel.getInstance();
    private static final AtomicInteger newId = new AtomicInteger(1);
    @PostMapping(value = "/createPet", consumes = "application/json", produces = "application/json")
    public String createPet(@RequestBody Pet pet) {
        petModel.add(pet, newId.getAndIncrement());
        return "Вы успешно создали питомца!!!";
    }

    @GetMapping(value = "/getAll", produces = "application/json")
    public Map<Integer, Pet> getAll() {
        return petModel.getAll();
    }

    @GetMapping(value = "/getPet", consumes = "application/json", produces = "application/json")
    public Pet getPet(@RequestBody Map<String, Integer> id) {
        return petModel.getFromList(id.get("id"));
    }
    @DeleteMapping(value = "/deletePet", consumes = "application/json")
    public void deletePet(@RequestBody Map<String, Integer> id) {
        petModel.deleteFromList(id.get("id"));
    }
    @PutMapping(value = "/putPet", consumes = "application/json")
    public void putPet(@RequestBody Crutch crutch) {
        Pet newPet = new Pet(crutch.getName(), crutch.getType(), crutch.getAge());
        petModel.update(petModel.getFromList(crutch.getId()), newPet, crutch.getId());
    }

    /*Компас
    {
        "North":"338-22",
            "North-East":"23-67",
            "East":"68-112",
            "South-East":"113-157",
            "South":"158-202",
            "South-West":"203-247",
            "West":"248-292",
            "North-West":"293-337".
    }*/

    @PostMapping(value = "/createCompass", consumes = "application/json")
    public void createCompass(@RequestBody Map<String, String> compassMap) {
        for (Map.Entry <String, String> entry: compassMap.entrySet()) {
            Compass compass = new Compass(entry.getValue());
            compassModel.add(compass, entry.getKey());
        }

    }

    @GetMapping(value = "/getAllCompass", produces = "application/json")
    public Map<String, Compass> getAllCompass() {
        return compassModel.getAllCompass();
    }

    @GetMapping(value = "/getDegree", consumes = "application/json", produces = "application/json")
    public Map<String, String> getDegree(@RequestBody Map<String, Integer> degree) {
        Map<String, Compass> newMap = compassModel.getAllCompass();
        Map<String, String> temp = new HashMap<>();
        for (Map.Entry <String, Compass> entry: newMap.entrySet()) {
            if (compassModel.parseDegree(entry.getValue().getDegrees()) == (degree.get("Degree") + 22) % 360 / 45) {
                temp.put("Side", entry.getKey());
                return temp;
            }
        }
        return null;
    }


}
