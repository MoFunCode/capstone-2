import com.delicious.model.Order;
import com.delicious.model.Sandwich;
import com.delicious.model.Topping;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;
import com.delicious.service.PriceCalculator;

public class Main {
    public static void main(String[] args) {

        Sandwich sandwich1 = new Sandwich(SandwichSize.TWELVE_INCH, BreadType.WHITE, false);
        sandwich1.addTopping(new Topping(ToppingType.BACON, false));
        Order order = new Order();
        order.addSandwich(sandwich1);
        System.out.println("Order Total: $" + order.calculateTotal());

    }

}
