package MiMiA98.atm.app;

import java.util.HashMap;
import java.util.Map;

public class ListScreen<T> extends Screen {

    private final Map<Integer, Item<T>> optionMap = new HashMap<>();
    private int count = 1;

    public void addItem(Item<T> item) {
        optionMap.put(count, item);
        count++;
    }

    public T chooseItem() {
        viewOptions();
        int option = getInputInt();

        if (hasOption(option)) {
            return getItem(option).item();
        } else {
            display("Invalid option! Try again!");
            throw new IllegalArgumentException("Invalid option!");
        }
    }

    public void viewOptions() {
        for (Map.Entry option : optionMap.entrySet()) {
            Item<T> optionText = (Item) option.getValue();
            display(option.getKey() + " - " + optionText.itemInfo);
        }
    }

    public boolean hasOption(int optionNumber) {
        return optionMap.containsKey(optionNumber);
    }

    public Item<T> getItem(int optionNumber) {
        if (!hasOption(optionNumber)) {
            throw new IllegalArgumentException("Invalid option!");
        }
        return optionMap.get(optionNumber);
    }

    public record Item<T>(String itemInfo, T item) {
    }
}
