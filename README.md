# Reciplopedia

A desktop Java application for managing recipes and ingredients — built with Swing and MigLayout. Reciplopedia helps you track the ingredients you have on hand, browse and sort recipes, and figure out what you can actually cook right now.

## Features

- **Ingredient tracking** — log the ingredients you've bought and edit your pantry at any time
- **Recipe database** — browse all saved recipes, each with a name, category, cooking time, description, and image
- **"Let's Cook"** — automatically shows which recipes you can make with the ingredients you currently have
- **Add your own recipes** — enter new recipes along with their required ingredients
- **Sorting** — sort recipes alphabetically (A–Z or Z–A) or by cooking time
- **Recipe categories** — recipes are organized as Appetizer, Main, or Dessert

## How It Works

The app is built around a simple loop: **stock your ingredients → browse or add recipes → see what you can cook.**

- From the **Home Page**, you can navigate to any of the main sections of the app.
- **Adding Groceries** lets you log ingredients you've bought, either by picking them from a searchable list or typing a new one in — you then specify the quantity for each.
- Everything you've added shows up in **My Ingredients**, where you can review or edit your current stock at any time.
- **My Recipes** lists every recipe saved in the database. You can sort the list alphabetically or by cooking time to find something quickly.
- Want to contribute a new recipe? **Add New Recipe** walks you through entering its name, category, cooking time, and description, then adding its ingredients one by one.
- Selecting a recipe opens **Cook Recipe**, showing the full details and image for preparing it.
- The standout feature is **Let's Cook** — it cross-references your current ingredients against the recipe database and shows you exactly what you're able to make right now, no manual checking required.

## Screenshots

| | |
|---|---|
| **Home Page** — main navigation | ![Home Page](screenshots/HomePage.png) |
| **Adding Groceries** — searching for an ingredient | ![Adding Groceries](screenshots/AddingGroceries.png) |
| **Adding Eggs** — picked from the search bar | ![Adding Eggs Search](screenshots/AddingEggsInSearchbar.png) |
| **Adding Eggs** — specifying quantity | ![Adding Eggs Quantity](screenshots/AddingEggsWithQuantity.png) |
| **My Ingredients** — current stock | ![My Ingredients](screenshots/MyIngredients.png) |
| **My Recipes** — unsorted list | ![My Recipes Unsorted](screenshots/MyRecipesUnsorted.png) |
| **My Recipes** — sorting options | ![My Recipes Sorting Options](screenshots/MyRecipesWithSortingOptions.png) |
| **My Recipes** — sorted by cooking time | ![My Recipes Sorted](screenshots/MyRecipesSortedByCookingTime.png) |
| **Add New Recipe** — entering a recipe | ![Add New Recipe](screenshots/AddNewRecipe.png) |
| **Cook Recipe** — recipe detail view | ![Cook Recipe](screenshots/CookRecipe.png) |
| **Let's Cook** — recipes you can make right now | ![Let's Cook](screenshots/LetsCook.png) |

## Tech Stack

- **Java** (Swing for the UI)
- **MigLayout** — layout manager for the Swing components
- Simple text-file persistence for ingredients and recipes (no external database required)

This project uses plain text files (`my_ingredients.txt`, `recipes.txt`) as lightweight data storage instead of a database — `ReaderWriter.java` handles reading and writing to them. Note that cloning this repo will include whatever sample ingredient/recipe data is currently saved in these files, since they're tracked in git alongside the code.

## Project Structure

```
Reciplopedia/
├── src/reciplopedia/       # All source code
│   ├── HomePageUI          # Main entry point / home screen
│   ├── EnterIngredientsUI  # Add ingredients you've purchased
│   ├── MyIngredientsUI     # View/edit your current ingredients
│   ├── LetsCookUI          # Shows recipes you can cook right now
│   ├── MyRecipesUI         # Browse all saved recipes
│   ├── EnterRecipeUI       # Add a new recipe
│   ├── AddIngredientsToRecipeUI  # Add ingredients to a new recipe
│   ├── CookRecipeUI        # View details for preparing a recipe
│   ├── RecipeSorting       # Sorting logic (A–Z, Z–A, cook time)
│   ├── ReaderWriter        # Reads/writes recipe and ingredient data files
│   ├── Recipe / Ingredient # Core data models
│   └── ...
├── images/                 # Recipe images
├── screenshots/            # App screenshots used in this README
├── my_ingredients.txt       # Saved ingredient data
├── recipes.txt              # Saved recipe data
└── miglayout15-swing.jar    # MigLayout dependency
```

## Getting Started

### Requirements
- JDK 8+ 
- An IDE that supports Java (e.g. IntelliJ IDEA or Eclipse)

Clone the repo, open it in your IDE, and run the app starting from `HomePageUI`.
