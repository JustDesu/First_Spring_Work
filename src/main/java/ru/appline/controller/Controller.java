package ru.appline.controller;

import org.springframework.web.bind.annotation.*;
import ru.appline.logic.Crutch;
import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class Controller {
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
        Pet newPet = new Pet();
        newPet.setName(crutch.getName());
        newPet.setType(crutch.getType());
        newPet.setAge(crutch.getAge());
        petModel.update(petModel.getFromList(crutch.getId()), newPet, crutch.getId());
    }
}
