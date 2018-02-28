package pethoalpar.com.androidrearmexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import pethoalpar.com.androidrearmexample.model.Car;
import pethoalpar.com.androidrearmexample.model.Person;
import pethoalpar.com.androidrearmexample.model.Rocket;

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
