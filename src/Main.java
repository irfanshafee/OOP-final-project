import java.util.Map;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MealLogger mealLogger = new MealLogger();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final HealthTipProvider healthTipProvider = new HealthTipProvider();

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    logNewMeal();
                    break;
                case 2:
                    mealLogger.displayDailySummary();
                    break;
                case 3:
                    mealLogger.displayAllMeals();
                    break;
                case 4:
                    showHealthTips();
                    break;
                case 5:
                    System.out.println("Thank you for using Calorie and Nutrition Tracker!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n====== Calorie and Nutrition Tracker ======");
        System.out.println("1. Log a New Meal");
        System.out.println("2. View Daily Summary");
        System.out.println("3. View All Logged Meals");
        System.out.println("4. Show Health Tips");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void logNewMeal() {
        scanner.nextLine(); // Clear the buffer
        System.out.println("\n=== Log a New Meal ===");

        // Display available foods
        FoodDatabase.displayAvailableFoods();

        // Get food name
        System.out.print("\nEnter food name: ");
        String foodName = scanner.nextLine().trim().toLowerCase();

        // Validate food exists in database
        if (!FoodDatabase.containsFood(foodName)) {
            System.out.println("Error: Food not found in database.");
            return;
        }

        // Get quantity
        System.out.print("Enter quantity (grams): ");
        double grams;
        try {
            grams = Double.parseDouble(scanner.nextLine());
            if (grams <= 0) {
                System.out.println("Error: Quantity must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
            return;
        }

        // Get meal type
        System.out.print("Enter meal type (Breakfast/Lunch/Dinner/Snack): ");
        String mealTypeInput = scanner.nextLine().trim();
        
        MealType mealType;
        try {
            mealType = MealType.fromString(mealTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid meal type.");
            return;
        }

        try {
            // Get nutrition info based on quantity
            NutritionInfo nutritionInfo = FoodDatabase.getNutritionInfo(foodName, grams);
            
            // Create and log the meal
            Meal meal = new Meal(
                foodName,
                nutritionInfo.calculateTotalCalories(),
                nutritionInfo,
                LocalDateTime.now().format(formatter)
            );
            
            mealLogger.logMeal(meal);
        } catch (Exception e) {
            System.out.println("Error occurred while logging meal: " + e.getMessage());
        }
    }

    private static void showHealthTips() {
        System.out.println("\n=== Health Tips ===");
        
        // Get daily nutritional data
        double totalProtein = 0;
        double totalCarbs = 0;
        double totalFats = 0;
        
        for (Meal meal : mealLogger.getMeals()) {
            NutritionInfo info = meal.getNutritionInfo();
            totalProtein += info.getProtein();
            totalCarbs += info.getCarbs();
            totalFats += info.getFats();
        }
        
        // Calculate percentages against daily targets
        DailyTarget target = new DailyTarget();
        double proteinPercentage = (totalProtein / target.getProteinTarget()) * 100;
        double carbsPercentage = (totalCarbs / target.getCarbsTarget()) * 100;
        double fatsPercentage = (totalFats / target.getFatsTarget()) * 100;
        
        // Get and display relevant tips
        Map<String, String> tips = healthTipProvider.getRelevantTips(
            proteinPercentage,
            carbsPercentage,
            fatsPercentage
        );
        
        tips.values().forEach(System.out::println);
    }
}