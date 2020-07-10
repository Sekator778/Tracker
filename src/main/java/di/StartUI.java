package di;

import java.util.List;

/**
 *
 */

public class StartUI {
    private Store store;

    public StartUI(Store store) {
        this.store = store;
    }

    public void add(String value) {
        this.store.add(value);
    }

    public void print() {
        for (String value : store.getAll()
        ) {
            System.out.println(value);
        }
    }

    public List<String> getAll() {
        return store.getAll();
    }
}
