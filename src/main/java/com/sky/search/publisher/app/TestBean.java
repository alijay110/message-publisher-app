package com.sky.search.publisher.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class TestBean {

//    @Value("${kumar.name}")
    private String name;

    public void printName(){
        System.out.println("NAME FROM ZOOKEEPER : " +name);
    }
}
