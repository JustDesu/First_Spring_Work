package ru.appline.compassLogic;

import ru.appline.logic.Pet;
import ru.appline.logic.PetModel;

import java.util.HashMap;
import java.util.Map;

public class CompassModel {

    private static final CompassModel instance = new CompassModel();

    private final Map<String, Compass> compassModel;

    public static CompassModel getInstance() {
        return instance;
    }

    private CompassModel() {
        compassModel = new HashMap<String, Compass>();
    }

    public void add(Compass compass, String key) {
        compassModel.put(key, compass);
    }

    public Map<String, Compass> getAllCompass() {
        return compassModel;
    }

    public Integer parseDegree(String degree) {
        return (Integer.parseInt(degree.substring(0, degree.indexOf("-"))) + 22) % 360 / 45;
    }

}
