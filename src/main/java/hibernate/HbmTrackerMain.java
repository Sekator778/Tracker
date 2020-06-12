package hibernate;

import tracker.Item;
import tracker.Store;

/**
 *
 */

public class HbmTrackerMain {
    private static final  Store STORE = new HbmTracker();

    private static void getAll() {
        //find all method
        STORE.findAll().forEach(System.out::println);
    }
    public static void main(String[] args) {
        //add method
        Item item = STORE.add(new Item("First"));
        System.out.println(item);
        item.setName("new Name");
        //replace method
        STORE.replace(item.getId(), item);
        getAll();
        //delete method
        STORE.delete(item.getId());
        getAll();
        Item item2 = STORE.add(new Item("Second"));
        getAll();
        Item item3 = STORE.add(new Item("Second"));
        // findByName method
        STORE.findByName(item3.getName()).forEach(System.out::println);
        // findById method
        System.out.println("findByID" + STORE.findById(25));


    }
}
