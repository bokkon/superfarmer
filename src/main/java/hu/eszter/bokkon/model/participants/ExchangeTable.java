package hu.eszter.bokkon.model.participants;

import hu.eszter.bokkon.model.animal.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExchangeTable {

    private final Map<String, String> exchanges = new HashMap<>();
    private final Map<String, List<Animal>> exchangesHelp1 = new HashMap<>();
    private final Map<String, Animal> exchangesHelp2 = new HashMap<>();

    public ExchangeTable() {
        fillExchanges();
    }

    private void fillExchanges() {
        exchanges.put("Sheep", "SmallDog,6 Rabbit");
        exchanges.put("Cow", "BigDog,3 Pig");
        exchanges.put("Pig", "2 Sheep");
        exchanges.put("Horse", "2 Cow");
        exchanges.put("SmallDog", "Sheep");
        exchanges.put("BigDog", "Cow");

        exchanges.put("6 Rabbit", "Sheep");
        exchanges.put("2 Sheep", "Pig");
        exchanges.put("3 Pig", "Cow");
        exchanges.put("2 Cow", "Horse");
    }

//    private void fillExchangesHelp1() {
//        List<Animal> act = Stream.generate(Rabbit::new).limit(6).collect(Collectors.toList());
//        exchangesHelp1.put("Rabbit6", act);
//        act.clear();
//        act = Stream.generate(Sheep::new).limit(2).collect(Collectors.toList());
//        exchangesHelp1.put("Sheep2", act);
//        act.clear();
//        act = Stream.generate(Pig::new).limit(3).collect(Collectors.toList());
//        exchangesHelp1.put("Pig3", act);
//        act.clear();
//        act = Stream.generate(Cow::new).limit(2).collect(Collectors.toList());
//        exchangesHelp1.put("Cow2", act);
//    }
//
//    private void fillExchangesHelp2() {
//        exchangesHelp2.put("Sheep", new Sheep());
//        exchangesHelp2.put("Pig", new Pig());
//        exchangesHelp2.put("Cow", new Cow());
//        exchangesHelp2.put("Horse", new Horse());
//        exchangesHelp2.put("SmallDog", new SmallDog());
//        exchangesHelp2.put("BigDog", new BigDog());
//    }

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
