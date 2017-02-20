package com.zhaolinger.camerademo;

/**
 * Created by zhaolinger on 2017/2/19.
 * 自定义recyclerview数据源实体类
 */
public class CustomRecyclerviewDataBean {

    private String name;
    private int age;
    private boolean isChecked;

    public CustomRecyclerviewDataBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public CustomRecyclerviewDataBean(String name, int age, boolean isChecked) {
        this.name = name;
        this.age = age;
        this.isChecked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
