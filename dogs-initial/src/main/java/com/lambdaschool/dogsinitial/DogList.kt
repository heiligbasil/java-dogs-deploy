package com.lambdaschool.dogsinitial

import com.lambdaschool.dogsinitial.model.Dog

class DogList
{

    val dogList: MutableList<Dog> = mutableListOf<Dog>()

    init
    {
        dogList.add(Dog("Springer", 50, false))
        dogList.add(Dog("Bulldog", 50, true))
        dogList.add(Dog("Collie", 50, false))
        dogList.add(Dog("Boston Terrie", 35, true))
        dogList.add(Dog("Corgie", 35, true))
    }

    fun findDog(tester: CheckDog): Dog?
    {
        for (d: Dog in dogList)
        {
            if (tester.test(d))
            {
                return d
            }
        }
        return null
    }

    fun findDogs(tester: CheckDog): List<Dog>
    {
        val tempDogList: MutableList<Dog> = mutableListOf<Dog>()

        for (d: Dog in dogList)
        {
            if (tester.test(d))
            {
                tempDogList.add(d)
            }
        }

        return tempDogList
    }
}