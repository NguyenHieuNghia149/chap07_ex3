package murach.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cart implements Serializable {
    private ArrayList<LineItem> items;
    private List<LineItem> list ;
    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }

    public void addItem(LineItem item) {
        String code = item.getProduct().getCode();
        int quantity = item.getQuantity();

        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);

            if (lineItem.getProduct().getCode().equals(code)) {
                lineItem.setQuantity(quantity);  // Update quantity if item exists
                return;
            }
        }

        // If not found, add a new item
        items.add(item);
    }

    public void removeItem(LineItem item) {
        String code = item.getProduct().getCode();

        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);

            if (lineItem.getProduct().getCode().equals(code)) {
                items.remove(i);  // Remove the item if found
                return;
            }
        }
    }

    public void removeOneItem(String code) {
        Iterator<LineItem> it = items.iterator();
        while (it.hasNext()) {
            LineItem item = it.next();
            if (item.getProduct().getCode().equals(code)) {
                it.remove();  // Remove the item from the cart
                return;
            }
        }
    }
}
