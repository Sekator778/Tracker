package tracker;

import java.util.function.Consumer;

public interface UserAction {
    String name();

    boolean execute(Input input, Store tracker, Consumer<String> output);
}