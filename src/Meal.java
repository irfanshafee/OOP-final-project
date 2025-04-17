public class Meal {
    private String name;
    private double calories;
    private NutritionInfo nutritionInfo;
    private String dateTime;

    public Meal(String name, double calories, NutritionInfo nutritionInfo, String dateTime) {
        this.name = name;
        this.calories = calories;
        this.nutritionInfo = nutritionInfo;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public NutritionInfo getNutritionInfo() {
        return nutritionInfo;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Meal: " + name + 
               "\nCalories: " + calories +
               "\nDate/Time: " + dateTime +
               "\n" + nutritionInfo.toString();
    }
}