import com.delicious.model.Sandwich;
import com.delicious.model.Topping;
import com.delicious.model.enums.BreadType;
import com.delicious.model.enums.SandwichSize;
import com.delicious.model.enums.ToppingType;

public class Main {
    public static void main(String[] args) {

        Sandwich sandwich = new Sandwich(
                SandwichSize.EIGHT_INCH,
                BreadType.WHITE,
                true
        );

        sandwich.addTopping(new Topping(ToppingType.BACON, true));
        sandwich.addTopping(new Topping(ToppingType.AMERICAN_CHEESE, false));
        sandwich.addTopping(new Topping(ToppingType.LETTUCE, false));

        System.out.println(sandwich);
    }

}
