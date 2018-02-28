package pethoalpar.com.androidrearmexample.model;

import io.realm.RealmObject;

/**
 * Created by petho on 2018-02-28.
 */

public class Car extends RealmObject{

    private String model;
    private Integer age;
    private Integer power;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", age=" + age +
                ", power=" + power +
                '}';
    }
}
