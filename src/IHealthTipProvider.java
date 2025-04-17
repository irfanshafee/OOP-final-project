public interface IHealthTipProvider {
    String getRandomHealthTip();
    String getHealthTipByCategory(String category);
    void addHealthTip(String tip, String category);
}