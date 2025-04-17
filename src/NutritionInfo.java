public class NutritionInfo {
    private double protein;
    private double carbs;
    private double fats;
    private double fiber;

    public NutritionInfo(double protein, double carbs, double fats, double fiber) {
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.fiber = fiber;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFats() {
        return fats;
    }

    public double getFiber() {
        return fiber;
    }

    public double calculateTotalCalories() {
        return (protein * 4) + (carbs * 4) + (fats * 9);
    }

    @Override
    public String toString() {
        return String.format("Nutrition Information:\n" +
                           "Protein: %.2fg\n" +
                           "Carbohydrates: %.2fg\n" +
                           "Fats: %.2fg\n" +
                           "Fiber: %.2fg",
                           protein, carbs, fats, fiber);
    }
}