package reciplopedia;

import java.util.ArrayList;

public class RecipeSorting {

    // merge sort for sorting recipes from A to Z
    public static void mergeSortAtoZ(ArrayList<Recipe> recipes) {
	if (recipes == null || recipes.size() <= 1) {
	    return;
	}

	int midIndex = recipes.size() / 2;
	ArrayList<Recipe> left = new ArrayList<>(recipes.subList(0, midIndex));
	ArrayList<Recipe> right = new ArrayList<>(recipes.subList(midIndex, recipes.size()));

	mergeSortAtoZ(left);
	mergeSortAtoZ(right);

	mergeAtoZ(left, right, recipes);
    }

    private static void mergeAtoZ(ArrayList<Recipe> left, ArrayList<Recipe> right, ArrayList<Recipe> recipes) {
	int i = 0;
	int j = 0;
	int k = 0;
	while (i < left.size() && j < right.size()) {
	    Recipe leftRecipe = left.get(i);
	    Recipe rightRecipe = right.get(j);

	    if (leftRecipe.getName().compareToIgnoreCase(rightRecipe.getName()) <= 0) {
		recipes.set(k, leftRecipe);
		k++;
		i++;
	    } else {
		recipes.set(k, rightRecipe);
		k++;
		j++;
	    }
	}
	while (i < left.size()) {
	    recipes.set(k, left.get(i));
	    k++;
	    i++;
	}
	while (j < right.size()) {
	    recipes.set(k, right.get(j));
	    k++;
	    j++;

	}
    }

    // merge sort for sorting recipes from Z to A
    public static void mergeSortZtoA(ArrayList<Recipe> recipes) {
	if (recipes == null || recipes.size() <= 1) {
	    return;
	}

	int midIndex = recipes.size() / 2;
	ArrayList<Recipe> left = new ArrayList<>(recipes.subList(0, midIndex));
	ArrayList<Recipe> right = new ArrayList<>(recipes.subList(midIndex, recipes.size()));

	mergeSortZtoA(left);
	mergeSortZtoA(right);

	mergeZtoA(left, right, recipes);
    }

    private static void mergeZtoA(ArrayList<Recipe> left, ArrayList<Recipe> right, ArrayList<Recipe> recipes) {
	int i = 0;
	int j = 0;
	int k = 0;
	while (i < left.size() && j < right.size()) {
	    Recipe leftRecipe = left.get(i);
	    Recipe rightRecipe = right.get(j);

	    if (leftRecipe.getName().compareToIgnoreCase(rightRecipe.getName()) > 0) {
		recipes.set(k, leftRecipe);
		k++;
		i++;
	    } else {
		recipes.set(k, rightRecipe);
		k++;
		j++;
	    }
	}
	while (i < left.size()) {
	    recipes.set(k, left.get(i));
	    k++;
	    i++;
	}
	while (j < right.size()) {
	    recipes.set(k, right.get(j));
	    k++;
	    j++;

	}
    }

    // merge sort for sorting recipes by cooking time
    public static void mergeSortCookTime(ArrayList<Recipe> recipes) {
	if (recipes == null || recipes.size() <= 1) {
	    return;
	}
	mergeSortAtoZ(recipes);
	int midIndex = recipes.size() / 2;
	ArrayList<Recipe> left = new ArrayList<>(recipes.subList(0, midIndex));
	ArrayList<Recipe> right = new ArrayList<>(recipes.subList(midIndex, recipes.size()));

	mergeSortCookTime(left);
	mergeSortCookTime(right);

	mergeCookTime(left, right, recipes);
    }

    private static void mergeCookTime(ArrayList<Recipe> left, ArrayList<Recipe> right, ArrayList<Recipe> recipes) {
	int i = 0;
	int j = 0;
	int k = 0;
	while (i < left.size() && j < right.size()) {
	    Recipe leftRecipe = left.get(i);
	    Recipe rightRecipe = right.get(j);

	    if (leftRecipe.getCookingTime() <= rightRecipe.getCookingTime()) {
		recipes.set(k, leftRecipe);
		k++;
		i++;
	    } else {
		recipes.set(k, rightRecipe);
		k++;
		j++;
	    }
	}
	while (i < left.size()) {
	    recipes.set(k, left.get(i));
	    k++;
	    i++;
	}
	while (j < right.size()) {
	    recipes.set(k, right.get(j));
	    k++;
	    j++;

	}
    }
}
