package ru.shanin.myadapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class People {
    public static final Comparator<People> byFSN;
    public static final int UNDEFINED_ID;

    static {
        UNDEFINED_ID = -1;
        byFSN = new ComparatorByFSN();
    }

    private final PeopleInfo peopleInfo;
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public People(PeopleInfo peopleInfo) {
        this.peopleInfo = peopleInfo;
        this._id = UNDEFINED_ID;
    }

    @NonNull
    @Override
    public String toString() {
        return peopleInfo.toString();
    }

    public PeopleInfo getPeopleInfo() {
        return peopleInfo;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        People guest = (People) obj;
        return (
                (_id == guest.get_id())
                        && (
                        peopleInfo == guest.getPeopleInfo()
                                || (peopleInfo != null && peopleInfo.equals(guest.getPeopleInfo())
                        )
                )
        );
    }
}

class PeopleInfo {
    private final String firstName;
    private final String secondName;
    private final int age;
    private final String email;
    private final String phone;
    private final ArrayList<String> listOfKnowledge;
    private final String pathToPhoto;

    public PeopleInfo(
            String firstName,
            String secondName,
            String email,
            String phone,
            ArrayList<String> listOfKnowledge,
            String pathToPhoto
    ) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phone = phone;
        this.listOfKnowledge = listOfKnowledge;
        this.pathToPhoto = pathToPhoto;
        this.age = (int) (Math.random() * 61 + 5);
    }

    @NonNull
    @Override
    public String toString() {
        return firstName + " " + secondName + " " + phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<String> getListOfKnowledge() {
        return listOfKnowledge;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        PeopleInfo guest = (PeopleInfo) obj;
        return
                firstName.equals(guest.getFirstName())
                        && secondName.equals(guest.getSecondName())
                        && phone.equals(guest.getPhone());
    }
}

class ComparatorByFSN implements Comparator<People> {

    @Override
    public int compare(People people1, People people2) {
        String fn1 = people1.getPeopleInfo().getFirstName();
        String fn2 = people2.getPeopleInfo().getFirstName();
        String sn1 = people1.getPeopleInfo().getSecondName();
        String sn2 = people2.getPeopleInfo().getSecondName();
        int result;
        result = fn1.compareTo(fn2);
        if (result != 0) return result;
        else return sn1.compareTo(sn2);
    }
}

class NewPeople {

    public static String genPathToPhoto() {
        Field[] drawablesFields = R.drawable.class.getFields();
        ArrayList<String> imageName = new ArrayList<>();
        for (Field field : drawablesFields)
            if (field.getName().length() == 4)
                imageName.add(field.getName());
        // Из имени ресурса получить идентификатор
        // String mDrawableName = "name"; // файл name.png в папке drawable
        // int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        return imageName.get((int) (Math.random() * imageName.size()));
    }

    public static String genNumber(int length) {
        String digits = "0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append(
                    digits.charAt(
                            (int) (Math.random() * 10)
                    )
            );
        return result.toString();
    }

    public static String genPhoneNumber() {
        return "+79" + genNumber(9);
    }

    public static String genString(int length) {
        String alphabetInUpperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String alphabetInLowerCase = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder result = new StringBuilder();
        result.append(
                alphabetInUpperCase.charAt(
                        (int) (Math.random() * 26)
                )
        );
        for (int i = 0; i < length - 1; i++) {
            result.append(
                    alphabetInLowerCase.charAt(
                            (int) (Math.random() * 26)
                    )
            );
        }
        return result.toString();
    }

    public static String genFirstName() {
        return genString(4);
    }

    public static String genSecondName() {
        return genString(6);
    }

    public static ArrayList<String> genListOfKnowledge() {
        String[] knowledge = {
                " C++/CLI ", " C# ", " Object Pascal ", " Groovy ",
                " Java ", " JavaScript ", " Objective-C ", " Perl ",
                " PHP ", " Python ", " Ruby ", " Swift ",
                " Visual Basic ", " Ada ", " Erlang ", " Gentee ",
                " Haskell ", " Scheme ", " Лисп ", " Kotlin ",
                " Curry ", " Delphi ", " Rust ", " Scala "
        };
        ArrayList<String> listOfKnowledge = new ArrayList<>();
        for (int i = 0; i < (int) (Math.random() * 3 + 1); i++) {
            listOfKnowledge.add(knowledge[(int) (Math.random() * knowledge.length)]);
        }
        Collections.sort(listOfKnowledge);
        return listOfKnowledge;
    }

    public static People newPeople() {
        String fn = genFirstName();
        String sn = genSecondName();
        return
                new People(
                        new PeopleInfo(
                                fn, sn,
                                fn + "_" + sn + "@gmail.com",
                                genPhoneNumber(),
                                genListOfKnowledge(),
                                genPathToPhoto()
                        )
                );
    }
}