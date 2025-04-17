import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealLogger implements IMealLogger {
    private List<Meal> meals;
    private DailyTarget dailyTarget;

    public MealLogger() {
        this.meals = new ArrayList<>();
        this.dailyTarget = new DailyTarget();
    }

    @Override
    public void logMeal(Meal meal) {
        meals.add(meal);
        System.out.println("Meal logged successfully!");
    }

    @Override
    public void displayDailySummary() {
        if (meals.isEmpty()) {
            System.out.println("No meals logged yet.");
            return;
        }

        double totalCalories = 0;
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;

        for (Meal meal : meals) {
            totalCalories += meal.getCalories();
            NutritionInfo info = meal.getNutritionInfo();
            totalProtein += info.getProtein();
            totalCarbs += info.getCarbs();
            totalFats += info.getFats();
        }

        System.out.println("===== Daily Summary =====");
        System.out.printf("Calories: %.0f / %.0f kcal\n", totalCalories, dailyTarget.getCaloriesTarget());
        System.out.printf("Proteins: %.1fg / %.1fg\n", totalProtein, dailyTarget.getProteinTarget());
        System.out.printf("Carbohydrates: %.1fg / %.1fg\n", totalCarbs, dailyTarget.getCarbsTarget());
        System.out.printf("Fats: %.1fg / %.1fg\n", totalFats, dailyTarget.getFatsTarget());
    }

    @Override
    public void displayAllMeals() {
        if (meals.isEmpty()) {
            System.out.println("No meals logged yet.");
            return;
        }

        System.out.println("=== Logged Meals ===");
        int mealNumber = 1;
        for (Meal meal : meals) {
            System.out.printf("%d. %s - %s - %s - %.0f kcal\n",
                mealNumber++,
                meal.getName(),
                "100g", // This should be replaced with actual portion size when available
                meal.getDateTime(),
                meal.getCalories());
        }
    }
}