import com.delicious.model.Sandwich;
import com.delicious.model.Topping;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;
import com.delicious.service.PriceCalculator;

public class Main {
    public static void main(String[] args) {

        Sandwich sandwich = new Sandwich(SandwichSize.EIGHT_INCH, BreadType.WHITE, false);
        sandwich.addTopping(new Topping(ToppingType.BACON, false));
        System.out.println("Price: $" + PriceCalculator.calculateSandwichPrice(sandwich));
    }

}
