package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StartUI {

    public void init(Input input, Store tracker, List<UserAction> actions, Consumer<String> output) {
        boolean run = true;
        while (run) {
            this.showMenu(actions, output);
            int select = input.askInt("Select: ", actions.size());
            UserAction action = actions.get(select);
            tracker.init();
            run = action.execute(input, tracker, output);
        }
    }

    private void showMenu(List<UserAction> actions, Consumer<String> output) {
        output.accept("Menu.");
        for (int index = 0; index < actions.size(); index++) {
            output.accept(index + ". " + actions.get(index).name());
        }
    }

    public static void main(String[] args) {
        Input input = new ConsoleInput();
        Consumer<String> stringConsumer = System.out::println;
        Input validateInput = new ValidateInput(input, stringConsumer);
        try (Store tracker = new SqlTracker()) {
            tracker.init();
            List<UserAction> actions = new ArrayList<>();
            actions.add(new CreateAction());
            actions.add(new ShowAllAction());
            actions.add(new ReplaceItemAction());
            actions.add(new DeleteAction());
            actions.add(new FindItemByIdAction());
            actions.add(new FindByNameAction());
            actions.add(new EndAction());
            new StartUI().init(validateInput, tracker, actions, stringConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        Input validate = new ValidateInput (
//                new ConsoleInput()
//        );
//        try (Store tracker = new SqlTracker()) {
//            tracker.init();
//            UserAction[] actions = {
//                    new CreateAction()
//            };
//            new StartUI().init(validate, tracker, actions);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}