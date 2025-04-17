public class DailyTarget {
    private double caloriesTarget;
    private double proteinTarget;
    private double carbsTarget;
    private double fatsTarget;

    public DailyTarget() {
        // Default values based on a common 2000 calorie diet
        this.caloriesTarget = 2000.0;
        this.proteinTarget = 70.0;
        this.carbsTarget = 160.0;
        this.fatsTarget = 40.0;
    }

    public DailyTarget(double caloriesTarget, double proteinTarget, double carbsTarget, double fatsTarget) {
        this.caloriesTarget = caloriesTarget;
        this.proteinTarget = proteinTarget;
        this.carbsTarget = carbsTarget;
        this.fatsTarget = fatsTarget;
    }

    public double getCaloriesTarget() {
        return caloriesTarget;
    }

    public void setCaloriesTarget(double caloriesTarget) {
        this.caloriesTarget = caloriesTarget;
    }

    public double getProteinTarget() {
        return proteinTarget;
    }

    public void setProteinTarget(double proteinTarget) {
        this.proteinTarget = proteinTarget;
    }

    public double getCarbsTarget() {
        return carbsTarget;
    }

    public void setCarbsTarget(double carbsTarget) {
        this.carbsTarget = carbsTarget;
    }

    public double getFatsTarget() {
        return fatsTarget;
    }

    public void setFatsTarget(double fatsTarget) {
        this.fatsTarget = fatsTarget;
    }
}