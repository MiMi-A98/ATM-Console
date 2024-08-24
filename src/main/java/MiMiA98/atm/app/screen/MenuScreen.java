package MiMiA98.atm.app.screen;

import java.util.HashMap;
import java.util.Map;


public class MenuScreen extends Screen {

    private final Map<Integer, Option> optionMap;
    private int count = 1;

    public MenuScreen(Option... options) {
        optionMap = new HashMap<>();
        for (Option option : options) {
            optionMap.put(count, option);
            count++;
        }
    }

    public void navigate() {
        viewOptions();
        int option = getInputInt();

        if (hasOption(option)) {
            executeAction(option);
        } else {
            display("Invalid option! Try again!");
            throw new IllegalArgumentException("Invalid option!");
        }
    }

    public void viewOptions() {
        for (Map.Entry option : optionMap.entrySet()) {
            Option optionText = (Option) option.getValue();
            display(option.getKey() + " - " + optionText.text);
        }
    }

    public boolean hasOption(int optionNumber) {
        return optionMap.containsKey(optionNumber);
    }

    public void executeAction(int optionNumber) {
        if (!hasOption(optionNumber)) {
            throw new IllegalArgumentException("Invalid option!");
        }
        Option option = optionMap.get(optionNumber);
        option.method.run();
    }


    public record Option(String text, Runnable method) {
    }

}
