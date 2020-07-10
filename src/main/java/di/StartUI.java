package di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
@Scope("prototype")
public class StartUI {
    @Autowired
    private Store store;

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
