package tracker;

import java.util.function.Consumer;

public class ReplaceItemAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit item (replace) ===";
    }

    @Override
    public boolean execute(Input input, Store tracker, Consumer<String> output) {
        int idOld = Integer.parseInt(input.askStr("=== Enter id item for replace ==="));
        String nameNew = input.askStr(" === Enter name newItem === ");
        if (tracker.replace(idOld, new Item(nameNew))) {
            output.accept("=== Item replaced ===");
        } else {
            output.accept("=== Error ===");
        }
        return true;
    }
}
