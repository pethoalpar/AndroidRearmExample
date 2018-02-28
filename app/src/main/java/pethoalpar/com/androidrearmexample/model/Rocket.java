package pethoalpar.com.androidrearmexample.model;

import io.realm.RealmObject;

/**
 * Created by petho on 2018-02-28.
 */

public class Rocket extends RealmObject {

    private Integer power;
    private String model;

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Rocket{" +
                "power=" + power +
                ", model='" + model + '\'' +
                '}';
    }
}
