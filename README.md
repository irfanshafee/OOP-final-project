# Savour the Flavour of Health

## Project Overview
It is a comprehensive nutrition and meal tracking application designed to empower users in making informed dietary choices. The project aims to transform the way people approach their daily nutrition by providing detailed insights into their eating habits, helping them maintain a balanced diet, and promoting overall health and wellness through mindful eating.

## Key Features

### 1. Detailed Meal Logging System
- Track meals with comprehensive nutritional information
- Record calories, proteins, carbohydrates, and fats for each meal
- Timestamp-based logging for better meal timing management
- Easy-to-use interface for quick meal entry

### 2. Personalized Daily Targets
- Set and monitor daily nutritional goals
- Customizable targets for calories, proteins, carbs, and fats
- Real-time progress tracking against daily targets
- Visual feedback on goal achievement

### 3. Comprehensive Meal History
- View detailed history of all logged meals
- Organized chronological display of meal entries
- Filter and search capabilities for past meals
- Nutritional breakdown for each meal entry

### 4. Smart Health Tips
- Personalized health recommendations based on eating patterns
- Dynamic tips adjusted to daily nutritional intake
- Educational content about balanced nutrition
- Motivational messages to encourage healthy eating habits

### 5. Data Persistence and Analysis
- Secure storage of meal logs and daily summaries
- Detailed daily summary reports
- Persistent tracking of nutritional goals and progress
- Data-driven insights into eating patterns

## Structural Design

### Core Components

1. **MealLogger (IMealLogger Interface)**
   - Central component for meal tracking functionality
   - Manages meal logging and retrieval
   - Handles daily summary generation
   - Implements data persistence

2. **Meal Class**
   - Represents individual meal entries
   - Contains meal name, timestamp, and nutritional information
   - Links to NutritionInfo for detailed nutrient data

3. **NutritionInfo Class**
   - Stores detailed nutritional information
   - Manages macronutrient data (proteins, carbs, fats)
   - Provides calculation methods for nutritional values

4. **DailyTarget Class**
   - Handles daily nutritional goals
   - Stores target values for all nutrients
   - Provides goal tracking functionality

5. **HealthTipProvider (IHealthTipProvider Interface)**
   - Generates personalized health tips
   - Analyzes nutritional data for recommendations
   - Provides motivational feedback

### Data Management
- Persistent storage using text files
- Separate files for meal logs and daily summaries
- Structured data format for easy parsing and analysis

### SOLID Principles Implementation

1. **Single Responsibility Principle (SRP)**
   - MealLogger class focuses solely on meal logging operations
   - NutritionInfo class handles only nutritional calculations
   - DailyTarget class manages exclusively goal-related functionality

2. **Open-Closed Principle (OCP)**
   - IHealthTipProvider interface allows adding new tip providers without modifying existing code
   - MealType enum enables adding new meal categories without changing core logic
   - Extensible logging system through IMealLogger interface

3. **Liskov Substitution Principle (LSP)**
   - All implementations of IMealLogger can be used interchangeably
   - Different HealthTipProvider implementations maintain consistent behavior
   - Meal subclasses preserve base class contracts

4. **Interface Segregation Principle (ISP)**
   - IMealLogger interface focused on core logging operations
   - IHealthTipProvider contains only tip-related methods
   - Clean separation between meal logging and health tip functionalities

5. **Dependency Inversion Principle (DIP)**
   - High-level modules depend on IMealLogger abstraction
   - FoodDatabase accessed through interfaces rather than concrete implementations
   - HealthTipProvider system depends on abstractions for flexibility

### Design Patterns
- Observer pattern for tracking nutritional goals
- Factory pattern for creating different meal types
- Strategy pattern in health tip generation
- Repository pattern for data persistence