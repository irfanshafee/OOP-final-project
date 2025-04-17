import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MealLogger implements IMealLogger {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public DailyTarget getDailyTarget() {
        return dailyTarget;
    }

    private DailyTarget dailyTarget;

    private static final String MEAL_LOGS_FILE = "data/meal_logs.txt";
    private static final String DAILY_SUMMARY_FILE = "data/daily_summary.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MealLogger() {
        this.meals = new ArrayList<>();
        this.dailyTarget = new DailyTarget();
        loadMealsFromFile();
    }

    private void loadMealsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MEAL_LOGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue; // Skip header lines
                String[] parts = line.split("\\|");
                if (parts.length < 7) continue;

                try {
                    NutritionInfo info = new NutritionInfo(
                        Double.parseDouble(parts[3]),
                        Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]),
                        Double.parseDouble(parts[6])
                    );
                    Meal meal = new Meal(
                        parts[1],  // name
                        Double.parseDouble(parts[2]),  // calories
                        info,  // nutritionInfo
                        parts[0]  // dateTime
                    );
                    meals.add(meal);
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Error parsing meal data: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading meals from file: " + e.getMessage());
        }
    }

    private void saveMealToFile(Meal meal) {
        try (FileWriter writer = new FileWriter(MEAL_LOGS_FILE, true)) {
            NutritionInfo info = meal.getNutritionInfo();
            String mealData = String.format("%-19s | %-20s | %7.1f | %7.1f | %7.1f | %7.1f | %s\n",
                meal.getDateTime(),
                meal.getName(),
                meal.getCalories(),
                info.getProtein(),
                info.getCarbs(),
                info.getFats(),
                "MEAL"
            );
            // Add header if file is empty
            File file = new File(MEAL_LOGS_FILE);
            if (file.length() == 0) {
                writer.write("===========================================\n");
                writer.write("             MEAL LOGS DATABASE\n");
                writer.write("===========================================\n\n");
                writer.write(String.format("%-19s | %-20s | %7s | %7s | %7s | %7s | %s\n",
                    "DateTime", "Food Name", "Calories", "Protein", "Carbs", "Fats", "Type"));
                writer.write("-------------------+----------------------+---------+---------+---------+---------+-------\n");
            }
            writer.write(mealData);
        } catch (IOException e) {
            System.err.println("Error saving meal to file: " + e.getMessage());
        }
    }

    private void saveDailySummary() {
        try (FileWriter writer = new FileWriter(DAILY_SUMMARY_FILE, true)) {
            double totalCalories = 0, totalProtein = 0, totalCarbs = 0, totalFats = 0;
            
            for (Meal meal : meals) {
                if (meal.getDateTime().startsWith(LocalDate.now().format(DATE_FORMATTER))) {
                    totalCalories += meal.getCalories();
                    NutritionInfo info = meal.getNutritionInfo();
                    totalProtein += info.getProtein();
                    totalCarbs += info.getCarbs();
                    totalFats += info.getFats();
                }
            }

            String summaryData = String.format("%s|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f\n",
                LocalDate.now().format(DATE_FORMATTER),
                totalCalories,
                totalProtein,
                totalCarbs,
                totalFats,
                dailyTarget.getCaloriesTarget(),
                dailyTarget.getProteinTarget(),
                dailyTarget.getCarbsTarget(),
                dailyTarget.getFatsTarget()
            );
            writer.write(summaryData);
        } catch (IOException e) {
            System.err.println("Error saving daily summary: " + e.getMessage());
        }
    }

    @Override
    public void logMeal(Meal meal) {
        meals.add(meal);
        saveMealToFile(meal);
        saveDailySummary();
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

        String today = LocalDate.now().format(DATE_FORMATTER);
        for (Meal meal : meals) {
            if (meal.getDateTime().startsWith(today)) {
                totalCalories += meal.getCalories();
                NutritionInfo info = meal.getNutritionInfo();
                totalProtein += info.getProtein();
                totalCarbs += info.getCarbs();
                totalFats += info.getFats();
            }
        }

        // Display to console
        System.out.println("===== Daily Summary =====");
        System.out.printf("Calories: %.0f / %.0f kcal\n", totalCalories, dailyTarget.getCaloriesTarget());
        System.out.printf("Proteins: %.1fg / %.1fg\n", totalProtein, dailyTarget.getProteinTarget());
        System.out.printf("Carbohydrates: %.1fg / %.1fg\n", totalCarbs, dailyTarget.getCarbsTarget());
        System.out.printf("Fats: %.1fg / %.1fg\n", totalFats, dailyTarget.getFatsTarget());

        // Save to file
        try (FileWriter writer = new FileWriter(DAILY_SUMMARY_FILE, false)) {
            // Write header
            writer.write("===========================================\n");
            writer.write("            DAILY SUMMARY REPORT\n");
            writer.write("===========================================\n\n");
            writer.write("# Data Format:\n");
            writer.write("# -----------\n");
            writer.write("# Date            : YYYY-MM-DD\n");
            writer.write("# Total Calories  : kcal\n");
            writer.write("# Total Protein   : grams\n");
            writer.write("# Total Carbs     : grams\n");
            writer.write("# Total Fats      : grams\n");
            writer.write("# Calorie Target  : kcal\n");
            writer.write("# Protein Target  : grams\n");
            writer.write("# Carbs Target    : grams\n");
            writer.write("# Fats Target     : grams\n\n");
            writer.write("===========================================\n");

            // Write data
            String summaryData = String.format("%s|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f|%.1f\n",
                today,
                totalCalories,
                totalProtein,
                totalCarbs,
                totalFats,
                dailyTarget.getCaloriesTarget(),
                dailyTarget.getProteinTarget(),
                dailyTarget.getCarbsTarget(),
                dailyTarget.getFatsTarget()
            );
            writer.write(summaryData);
        } catch (IOException e) {
            System.err.println("Error saving daily summary: " + e.getMessage());
        }
    }

    @Override
    public void displayAllMeals() {
        if (meals.isEmpty()) {
            System.out.println("No meals logged yet.");
            return;
        }

        // Display to console
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

        // Save to file
        try (FileWriter writer = new FileWriter(MEAL_LOGS_FILE, false)) {
            writer.write("===========================================\n");
            writer.write("             MEAL LOGS DATABASE\n");
            writer.write("===========================================\n\n");
            writer.write("# Data Format:\n");
            writer.write("# -----------\n");
            writer.write("# DateTime        : YYYY-MM-DD HH:MM\n");
            writer.write("# Food Name       : Name of the food item\n");
            writer.write("# Calories        : kcal\n");
            writer.write("# Protein         : grams\n");
            writer.write("# Carbs           : grams\n");
            writer.write("# Fats            : grams\n");
            writer.write("# Meal Type       : Category of meal\n\n");
            writer.write("===========================================\n");

            // Write column headers
            writer.write(String.format("%-19s | %-20s | %7s | %7s | %7s | %7s | %s\n",
                "DateTime", "Food Name", "Calories", "Protein", "Carbs", "Fats", "Type"));
            writer.write("-------------------+----------------------+---------+---------+---------+---------+-------\n");

            // Write meal data
            for (Meal meal : meals) {
                NutritionInfo info = meal.getNutritionInfo();
                String mealData = String.format("%-19s | %-20s | %7.1f | %7.1f | %7.1f | %7.1f | %s\n",
                    meal.getDateTime(),
                    meal.getName(),
                    meal.getCalories(),
                    info.getProtein(),
                    info.getCarbs(),
                    info.getFats(),
                    "MEAL"
                );
                writer.write(mealData);
            }
        } catch (IOException e) {
            System.err.println("Error saving meals to file: " + e.getMessage());
        }
    }
}