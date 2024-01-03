package reciplopedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class ReaderWriter {

    // reads an ingredient from String and returns Ingredient object
    public static Ingredient loadIngredient(String str) {
	String[] elements = str.split(" ");
	String ingrName = elements[0];
	for (int i = 1; i < elements.length - 2; i++) {
	    ingrName += " " + elements[i];
	}
	elements[elements.length - 1] = elements[elements.length - 1].toUpperCase();
	Ingredient newIngredient = new Ingredient(ingrName, Double.parseDouble(elements[elements.length - 2]),
		Unit.valueOf(elements[elements.length - 1]));
	return newIngredient;
    }

    // gives Unit for display in the preferred way
    public static String getUnitString(Unit unit) {
	if (unit == Unit.GRAMS) {
	    return "grams";
	} else if (unit == Unit.ML) {
	    return "mL";
	} else if (unit == Unit.PIECES) {
	    return "pieces";
	} else if (unit == Unit.TSP) {
	    return "tsp";
	} else if (unit == Unit.TBSP) {
	    return "tbsp";
	} else if (unit == Unit.CLOVES) {
	    return "cloves";
	}
	return null;
    }

    // loads the ingredients from the valid_ingredients.txt file
    public static void loadValidIngredients(List<String> validIngredients) {
	File file = new File("valid_ingredients.txt");
	try (Scanner sc = new Scanner(file)) {
	    while (sc.hasNext()) {
		String line = sc.nextLine();
		String[] elements = line.split(" - ");
		validIngredients.add(elements[0]);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    // gets the unit for a valid ingredient
    public static String getValidIngredientUnit(String str) {
	try {
	    File file = new File("valid_ingredients.txt");
	    Scanner scanner = new Scanner(file);

	    while (scanner.hasNextLine()) {
		String line = scanner.nextLine();
		String[] elements = line.split(" - ");
		if (elements.length == 2 && str.equalsIgnoreCase(elements[0])) {
		    scanner.close();
		    return getUnitString(Unit.valueOf(elements[1]));
		}
	    }
	    scanner.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	return "";
    }

    // Loads all the ingredients from the MyIngredients file
    public static Map<String, Ingredient> loadMyIngredients() {
	Map<String, Ingredient> map = new HashMap<>();
	File file = new File("my_ingredients2.txt");
	try (Scanner sc = new Scanner(file)) {
	    while (sc.hasNext()) {
		Ingredient ingredient = loadIngredient(sc.nextLine());
		map.put(ingredient.getName(), ingredient);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return map;
    }

    // loads the text from the my_ingredients.txtx file
    public static String displayMyIngredients() {
	String result = "";
	File file = new File("my_ingredients2.txt");
	try (Scanner sc = new Scanner(file)) {
	    while (sc.hasNext()) {
		result += sc.nextLine() + "\n";
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return result;
    }

    // from Map.Entry<String, Ingredient> into String
    public static void writeIngredientsInFile(Map<String, Ingredient> map) {
	File file = new File("my_ingredients2.txt");
	try (PrintWriter writer = new PrintWriter(file)) {
	    for (Map.Entry<String, Ingredient> entry : map.entrySet()) {
		Ingredient ingredient = entry.getValue();
		writer.append(ingredient.toString() + "\n");
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    // from map to string generally
    public static String writeIngredients(Map<String, Ingredient> map) {
	String result = "";
	for (Map.Entry<String, Ingredient> entry : map.entrySet()) {
	    Ingredient ingredient = entry.getValue();
	    result += ingredient.toString() + "\n";
	}
	return result;
    }

    // from String to Map<String, Ingredient>
    public static void getIngredientsFromStringToMap(String text, Map<String, Ingredient> ingredients) {
	String[] lines = text.split("\\r?\\n");
	for (String line : lines) {
	    if (!line.isEmpty()) {
		line.trim();
		Ingredient ingredient = ReaderWriter.loadIngredient(line);
		ingredients.put(ingredient.getName(), ingredient);
	    }
	}
    }

    // Gets the recipes from the file
    public static ArrayList<Recipe> loadFileRecipes() {
	ArrayList<Recipe> recipes = new ArrayList<>();
	File file = new File("recipes2.txt");
	try (Scanner sc = new Scanner(file)) {
	    while (sc.hasNext()) {
		if (sc.nextLine().trim().equals("New Recipe")) {
		    String name = sc.nextLine();
		    Type type = Type.valueOf(sc.nextLine());
		    int cookTime = Integer.parseInt(sc.nextLine());
		    String imagePath = sc.nextLine();
		    ImageIcon image = new ImageIcon(imagePath);
		    Map<String, Ingredient> ingredients = new HashMap<>();
		    String ingredientsText = "";
		    String line = "";
		    while (true) {
			line = sc.nextLine();
			if (!line.startsWith("*")) {
			    break;
			} else {
			    ingredientsText += line + "\n";
			}
		    }
		    getIngredientsFromStringToMap(ingredientsText, ingredients);
		    String howTo = line;
		    while (true) {
			line = sc.nextLine();
			if (line.isEmpty()) {
			    break;
			} else {
			    howTo += line;
			}
		    }
		    Recipe newRecipe = new Recipe(name, type, cookTime, image, ingredients, howTo);
		    recipes.add(newRecipe);
		}
	    }
	    sc.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return recipes;
    }

}
