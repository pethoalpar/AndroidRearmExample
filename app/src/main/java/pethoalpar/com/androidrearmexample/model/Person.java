package pethoalpar.com.androidrearmexample.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by petho on 2018-02-28.
 */

public class Person extends RealmObject {

    private String name;
    private int age;

    private RealmList<Car> cars;

    private Rocket rocket;

    private Integer id;

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

    public RealmList<Car> getCars() {
        return cars;
    }

    public void setCars(RealmList<Car> cars) {
        this.cars = cars;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if(this.cars != null && !this.cars.isEmpty()){
            for(int i = 0; i< cars.size(); ++i){
                sb.append(cars.get(i));
            }
        }
        sb.append("}");
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cars=" + sb.toString() +
                ", rocket=" + rocket +
                ", id=" + id +
                '}';
    }
}
