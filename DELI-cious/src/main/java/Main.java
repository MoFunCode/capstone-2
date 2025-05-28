import com.delicious.model.*;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;
import com.delicious.service.PriceCalculator;
import com.delicious.utils.FileManager;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Order order = new Order();

        Sandwich sandwich = new Sandwich(SandwichSize.EIGHT_INCH, BreadType.WHITE, false);
        sandwich.addTopping(new Topping(ToppingType.BACON, false));
        order.addSandwich(sandwich);

        order.addDrink(new Drink("MEDIUM", "Lemonade"));
        order.addChips(new Chips("BBQ"));
        FileManager.saveReceipt(order);



    }


}

