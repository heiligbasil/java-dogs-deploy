package com.lambdaschool.dogsinitial.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DogsinitialApplicationJava
{

    static DogListJava ourDogListJava;
    public static void mainJava(String[] args)
    {
        ourDogListJava = new DogListJava();
        SpringApplication.run(DogsinitialApplicationJava.class, args);
    }

}

