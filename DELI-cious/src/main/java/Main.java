import com.delicious.model.*;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;
import com.delicious.service.PriceCalculator;

public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        Sandwich sandwich1 = new Sandwich(SandwichSize.TWELVE_INCH, BreadType.WHITE, false);
        sandwich1.addTopping(new Topping(ToppingType.BACON, false));
        order.addDrink(new Drink("MEDIUM", "FANTA"));
        order.addChips(new Chips("BBQ"));
        order.addSandwich(sandwich1);

        System.out.println("Order Total: $" + order.calculateTotal());



    }

}
