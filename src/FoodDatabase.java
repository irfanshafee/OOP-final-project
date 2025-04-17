import java.util.HashMap;
import java.util.Map;

public class FoodDatabase {
    private static final Map<String, NutritionInfo> foods = new HashMap<>();

    static {
        // Pre-populated food database with nutritional values per 100g
        foods.put("rice", new NutritionInfo(2.7, 28.0, 0.3, 0.4));
        foods.put("chicken breast", new NutritionInfo(31.0, 0.0, 3.6, 0.0));
        foods.put("salmon", new NutritionInfo(22.0, 0.0, 13.0, 0.0));
        foods.put("apple", new NutritionInfo(0.3, 13.8, 0.2, 2.4));
        foods.put("banana", new NutritionInfo(1.1, 22.8, 0.3, 2.6));
        foods.put("egg", new NutritionInfo(13.0, 1.1, 11.0, 0.0));
    }

    public static boolean containsFood(String foodName) {
        return foods.containsKey(foodName.toLowerCase());
    }

    public static NutritionInfo getNutritionInfo(String foodName, double grams) {
        NutritionInfo baseInfo = foods.get(foodName.toLowerCase());
        if (baseInfo == null) {
            throw new IllegalArgumentException("Food not found in database");
        }

        // Calculate nutrition based on the specified grams (converting from per 100g)
        double factor = grams / 100.0;
        return new NutritionInfo(
            baseInfo.getProtein() * factor,
            baseInfo.getCarbs() * factor,
            baseInfo.getFats() * factor,
            baseInfo.getFiber() * factor
        );
    }

    public static void displayAvailableFoods() {
        System.out.println("Available foods in database:");
        for (String food : foods.keySet()) {
            System.out.println("- " + food);
        }
    }
}