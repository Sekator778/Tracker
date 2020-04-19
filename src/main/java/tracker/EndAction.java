package tracker;

import java.util.function.Consumer;

public class EndAction implements UserAction {
    @Override
    public String name() {
        return "=== END. ===";
    }

    @Override
    public boolean execute(Input input, Store tracker, Consumer<String> output) {
        output.accept("Program close");
        return false;
    }
}
