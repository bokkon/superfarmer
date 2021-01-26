package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeTable {

    private final Map<String, String> exchanges = new HashMap<>();
    private final Map<String, List<Animal>> exchangesHelp1 = new HashMap<>();
    private final Map<String, Animal> exchangesHelp2 = new HashMap<>();

    public ExchangeTable() {
        fillExchanges();
        fillExchangesHelp1();
        fillExchangesHelp2();
    }

    private void fillExchanges() {
        exchanges.put("Sheep", "Rabbit6,SmallDog");
        exchanges.put("Cow", "Pig3,BigDog");
        exchanges.put("Pig", "Sheep2");
        exchanges.put("Horse", "Cow2");
        exchanges.put("SmallDog", "Sheep");
        exchanges.put("BigDog", "Cow");

        exchanges.put("Rabbit6", "Sheep");
        exchanges.put("Sheep2", "Pig");
        exchanges.put("Pig3", "Cow");
        exchanges.put("Cow2", "Horse");
    }

    private void fillExchangesHelp1() {
        List<Animal> act = Stream.generate(Rabbit::new).limit(6).collect(Collectors.toList());
        exchangesHelp1.put("Rabbit6", act);
        act.clear();
        act = Stream.generate(Sheep::new).limit(2).collect(Collectors.toList());
        exchangesHelp1.put("Sheep2", act);
        act.clear();
        act = Stream.generate(Pig::new).limit(3).collect(Collectors.toList());
        exchangesHelp1.put("Pig3", act);
        act.clear();
        act = Stream.generate(Cow::new).limit(2).collect(Collectors.toList());
        exchangesHelp1.put("Cow2", act);
    }

    private void fillExchangesHelp2() {
        exchangesHelp2.put("Sheep", new Sheep());
        exchangesHelp2.put("Pig", new Pig());
        exchangesHelp2.put("Cow", new Cow());
        exchangesHelp2.put("Horse", new Horse());
        exchangesHelp2.put("SmallDog", new SmallDog());
        exchangesHelp2.put("BigDog", new BigDog());
    }

    public Map<String, String> getExchanges() {
        return exchanges;
    }

    public Map<String, List<Animal>> getExchangesHelp1() {
        return exchangesHelp1;
    }

    public Map<String, Animal> getExchangesHelp2() {
        return exchangesHelp2;
    }
}
