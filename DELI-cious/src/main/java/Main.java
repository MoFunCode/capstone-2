import com.delicious.model.*;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;
import com.delicious.service.PriceCalculator;

public class Main {
    public static void main(String[] args) {

        Order order = new Order();

        Sandwich sandwich = new Sandwich(SandwichSize.EIGHT_INCH, BreadType.WHITE, false);
        sandwich.addTopping(new Topping(ToppingType.BACON, false));
        order.addSandwich(sandwich);

        order.addDrink(new Drink("MEDIUM", "Lemonade"));
        order.addChips(new Chips("BBQ"));

        System.out.println("Order Total: $" + order.calculateTotal());
    }


}

