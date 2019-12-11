package com.github.sjroom.cache.redis.domain;

import java.io.Serializable;

/**
 * @author: DanielLi
 * @date: 2018/1/13
 * @description:
 */
public class Person implements Serializable{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    private double age;

    @Override
    public boolean equals(Object obj) {
        if(null == obj){
            return false;
        }
        Person tmp = (Person) obj;
        return this.name.equals(tmp.name) &&
                this.age == tmp.age;
    }
}
