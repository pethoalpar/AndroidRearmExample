<h1>Android rearm(database) example</h1>

<h3>Add in application gradle new dependency</h3>

```gradle
classpath 'io.realm:realm-gradle-plugin:4.3.3'
```

<h3>Add in your module gradle build the new plugin</h3>

```gradle
classpath 'io.realm:realm-gradle-plugin:4.3.3'
```

<h3>Declare rearm objects. This objects will saves in the database</h3>

```java
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
```

```java
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
```

```java
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
```

<h3>Implements business logic</h3>

```java
public class MainActivity extends AppCompatActivity {

    private Realm realm;

    private TextView textView;

    private int counter =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        realm = Realm.getDefaultInstance();

        textView = (TextView) this.findViewById(R.id.textView);

        this.findViewById(R.id.buttonAddPerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Person person = realm.createObject(Person.class);
                        person.setAge(counter);
                        person.setName("Alpar"+counter);
                        person.setId(getNextKey());
                        counter++;
                    }
                });
                actualizeData();
            }
        });

        this.findViewById(R.id.buttonAddCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Person person = realm.where(Person.class).findFirst();
                if(person != null){
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Car car = realm.createObject(Car.class);
                            car.setAge(counter);
                            car.setModel("Golf"+counter);
                            car.setPower(100+counter);
                            counter++;
                            if(person.getCars() == null){
                                person.setCars(new RealmList<Car>());
                            }
                            person.getCars().add(car);
                        }
                    });
                }
                actualizeData();
            }
        });

        this.findViewById(R.id.buttonAddRocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmResults<Person> results = realm.where(Person.class).findAll();
                if(results != null && !results.isEmpty()){
                    final Person person = results.last();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Rocket rocket = realm.createObject(Rocket.class);
                            rocket.setModel("Heavy falcon" + counter);
                            rocket.setPower(100000 + counter);
                            counter++;
                            person.setRocket(rocket);
                        }
                    });
                }
                actualizeData();
            }
        });

        this.findViewById(R.id.buttonDeletePerson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmResults<Person> results = realm.where(Person.class).findAll();
                        if(results != null && !results.isEmpty()){
                            results.last().deleteFromRealm();
                        }
                    }
                });
                actualizeData();
            }
        });
        actualizeData();
    }

    private void actualizeData(){
        RealmResults<Person> results = realm.where(Person.class).findAll();
        StringBuilder sb = new StringBuilder("{");
        if(!results.isEmpty()){
            for(int i =0; i< results.size(); ++i){
                sb.append(results.get(i));
            }
        }
        sb.append("}");
        textView.setText(sb.toString());
    }

    public int getNextKey(){
        try{
            Number number = realm.where(Person.class).max("id");
            if(number != null){
                return number.intValue()+1;
            }else{
                return 0;
            }
        }catch (ArrayIndexOutOfBoundsException e){
            return 0;
        }
    }
}
```