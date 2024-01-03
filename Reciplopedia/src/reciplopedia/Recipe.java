package reciplopedia;

import java.util.Map;

import javax.swing.ImageIcon;

enum Type {
    APPETIZER, MAIN, DESSERT
}

public class Recipe {

    private String name;
    private Map<String, Ingredient> ingridients;
    private Type type;
    private int cookingTime; // in minutes
    private String description;
    private ImageIcon image;

    public Recipe(String name, Type type, int cookingTime, ImageIcon image, Map<String, Ingredient> ingridients,
	    String description) {
	this.name = name;
	this.ingridients = ingridients;
	this.type = type;
	this.cookingTime = cookingTime;
	this.description = description;
	this.image = image;
    }

    public String getName() {
	return name;
    }

    public Map<String, Ingredient> getIngridients() {
	return ingridients;
    }

    public Type getType() {
	return type;
    }

    public int getCookingTime() {
	return cookingTime;
    }

    public String getDescription() {
	return description;
    }

    public ImageIcon getImage() {
	return image;
    }

    // return the recipe by model
//    Pancakes
//    DESSERT
//    20
//    images\Pancakes.jpg
//    * eggs 2.0 PIECES
//    * milk 480.0 ML
//    * flour 320.0 GRAMS
//    In a large bowl, mix together the milk and the eggs. Add the flour to the wet ingredients. Mix until
//    incorporated but batter is still slightly lumpy. Batter should be thick.
//    Using a greased or non-stick pan cook about 1/4 or 1/3 cup batter at a time on low-medium heat.
//    This should take a few minutes. Then flip when the edges are golden brown and cook for another
//    minute. Serve with fresh fruit, honey, syrup, chocolate sauce, or anything you like!
    @Override
    public String toString() {
	String resultIngridients = "";
	for (Map.Entry<String, Ingredient> entry : ingridients.entrySet()) {
	    resultIngridients += "\n" + entry.getValue();
	}
	return name + "\n" + type + "\n" + cookingTime + "\n" + image + resultIngridients + "\n" + description + "\n";
    }

}
