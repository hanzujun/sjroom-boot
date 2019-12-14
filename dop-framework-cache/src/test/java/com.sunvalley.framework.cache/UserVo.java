package com.sunvalley.framework.cache;

import java.util.List;

public class UserVo {

    public  static final String Table = "t_user";

    private String name;
    private String address;
    private Integer age;

    private List<ProductDetailVo> productDetailVoList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<ProductDetailVo> getProductDetailVoList() {
        return productDetailVoList;
    }

    public void setProductDetailVoList(List<ProductDetailVo> productDetailVoList) {
        this.productDetailVoList = productDetailVoList;
    }



    @Override
    public String toString() {
        return "UserVo{" +
            "name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", age=" + age +
            '}';
    }
}
