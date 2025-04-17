import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HealthTipProvider implements IHealthTipProvider {
    private Map<String, List<String>> tipsByCategory;
    private Random random;
    private static final String TIPS_FILE = "src/tips.txt";

    public HealthTipProvider() {
        this.tipsByCategory = new HashMap<>();
        this.random = new Random();
        loadTipsFromFile();
    }

    private void loadTipsFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(TIPS_FILE));
            String currentCategory = "";
            List<String> currentTips = new ArrayList<>();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                
                if (line.matches("[A-Z]+")) {
                    if (!currentCategory.isEmpty()) {
                        tipsByCategory.put(currentCategory, new ArrayList<>(currentTips));
                        currentTips.clear();
                    }
                    currentCategory = line.trim();
                } else {
                    currentTips.add(line.trim());
                }
            }
            
            if (!currentCategory.isEmpty() && !currentTips.isEmpty()) {
                tipsByCategory.put(currentCategory, new ArrayList<>(currentTips));
            }
        } catch (IOException e) {
            System.err.println("Error loading tips file: " + e.getMessage());
        }
    }

    @Override
    public String getRandomHealthTip() {
        List<String> allTips = new ArrayList<>();
        tipsByCategory.values().forEach(allTips::addAll);
        
        if (allTips.isEmpty()) return "No health tips available.";
        return allTips.get(random.nextInt(allTips.size()));
    }

    @Override
    public String getHealthTipByCategory(String category) {
        List<String> tips = tipsByCategory.get(category.toUpperCase());
        if (tips == null || tips.isEmpty()) return "No tips available for this category.";
        return tips.get(random.nextInt(tips.size()));
    }

    @Override
    public void addHealthTip(String tip, String category) {
        tipsByCategory.computeIfAbsent(category.toUpperCase(), k -> new ArrayList<>()).add(tip);
    }

    public Map<String, String> getRelevantTips(double proteinPercentage, double carbsPercentage, double fatsPercentage) {
        Map<String, String> relevantTips = new HashMap<>();

        if (proteinPercentage < 90) {
            relevantTips.put("PROTEIN", "💡 " + getHealthTipByCategory("PROTEIN"));
        }
        if (carbsPercentage > 110) {
            relevantTips.put("CARBS", "💡 Watch your carbs: " + getHealthTipByCategory("CARBS"));
        } else if (carbsPercentage >= 90 && carbsPercentage <= 110) {
            relevantTips.put("CARBS", "💡 Great balance on carbs!");
        }
        if (fatsPercentage > 110) {
            relevantTips.put("FATS", "💡 " + getHealthTipByCategory("FATS"));
        }

        if (relevantTips.isEmpty()) {
            relevantTips.put("GENERAL", "💡 " + getHealthTipByCategory("GENERAL"));
        }

        return relevantTips;
    }
}