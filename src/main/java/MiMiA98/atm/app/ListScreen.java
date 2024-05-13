package MiMiA98.atm.app;

import java.util.HashMap;
import java.util.Map;

public class ListScreen extends Screen {

    private final Map<Integer, Item> optionMap = new HashMap<>();
    private int count = 1;

    public void addItem(Item item) {
        optionMap.put(count, item);
        count++;
    }

    public Object chooseItem() {
        viewOptions();
        int option = getInputInt();

        if (hasOption(option)) {
            return getItem(option);
        } else {
            display("Invalid option! Try again!");
            throw new IllegalArgumentException("Invalid option!");
        }
    }

    public void viewOptions() {
        for (Map.Entry option : optionMap.entrySet()) {
            Item optionText = (Item) option.getValue();
            display(option.getKey() + " - " + optionText.itemInfo);
        }
    }

    public boolean hasOption(int optionNumber) {
        return optionMap.containsKey(optionNumber);
    }

    public Object getItem(int optionNumber) {
        if (!hasOption(optionNumber)) {
            throw new IllegalArgumentException("Invalid option!");
        }
        return optionMap.get(optionNumber);
    }

    public record Item(String itemInfo, Object item) {
    }
}
