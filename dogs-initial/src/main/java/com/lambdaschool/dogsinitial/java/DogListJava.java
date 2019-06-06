package com.lambdaschool.dogsinitial.java;

import java.util.ArrayList;

public class DogListJava
{
    public ArrayList<DogJava> dogJavaList = new ArrayList<DogJava>();

    public DogListJava()
    {
        dogJavaList.add(new DogJava("Springer", 50, false));
        dogJavaList.add(new DogJava("Bulldog", 50, true));
        dogJavaList.add(new DogJava("Collie", 50, false));
        dogJavaList.add(new DogJava("Boston Terrie", 35, true));
        dogJavaList.add(new DogJava("Corgie", 35, true));
    }

    public DogJava findDog(CheckDogJava tester)
    {
        for (DogJava d : dogJavaList)
        {
            if (tester.test(d))
            {
                return d;
            }
        }
        return null;
    }

    public ArrayList<DogJava> findDogs(CheckDogJava tester)
    {
        ArrayList<DogJava> tempDogJavaList = new ArrayList<>();

        for (DogJava d : dogJavaList)
        {
            if (tester.test(d))
            {
                tempDogJavaList.add(d);
            }
        }

        return tempDogJavaList;
    }
}
