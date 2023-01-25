package ru.shanin.myadapter;

import android.app.Application;

import java.util.ArrayList;

public class AppStart extends Application {
    public static ArrayList<People> localData;
    private static int autoIncrementId;

    static {
        autoIncrementId = 0;
        localData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            addNewPeople();
        }
    }

    public static void addNewPeople() {
        People people = NewPeople.newPeople();
        if (people.get_id() == People.UNDEFINED_ID)
            people.set_id(autoIncrementId++);
        localData.add(people);
        localData.sort(People.byFSN);
    }

}
