package di;

/**
 *
 */

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(Store.class);
        context.reg(StartUI.class);
        StartUI startUI = context.get(StartUI.class);
        startUI.add("Alex");
        startUI.add("Bob");
        startUI.add("Petr");
        startUI.print();
    }
}
