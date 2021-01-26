package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeTable {

    private final Map<String, List<Animal>> exchanges1 = new HashMap<>();
    private final Map<String, Animal> exchanges2 = new HashMap<>();

    public ExchangeTable() {
        fillExchanges1();
        fillExchanges2();
    }

    private void fillExchanges1() {
        List<Animal> act = Stream.generate(Rabbit::new).limit(6).collect(Collectors.toList());
        exchanges1.put("Sheep", act);
        act.clear();
        act = Stream.generate(Sheep::new).limit(2).collect(Collectors.toList());
        exchanges1.put("Pig", act);
        act.clear();
        act = Stream.generate(Pig::new).limit(3).collect(Collectors.toList());
        exchanges1.put("Cow", act);
        act.clear();
        act = Stream.generate(Cow::new).limit(2).collect(Collectors.toList());
        exchanges1.put("Horse", act);
    }

    private void fillExchanges2() {
        exchanges2.put("SmallDog", new Sheep());
        exchanges2.put("BigDog", new Cow());
        exchanges2.put("Sheep", new SmallDog());
        exchanges2.put("Cow", new BigDog());
        exchanges2.put("Rabbit6", new Sheep());
        exchanges2.put("Sheep2", new Pig());
        exchanges2.put("Pig3", new Cow());
        exchanges2.put("Cow2", new Horse());
    }

}
