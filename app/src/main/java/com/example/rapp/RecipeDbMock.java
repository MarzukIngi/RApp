package com.example.rapp;

import com.example.rapp.entities.Recipe;

// Temporary class that contains dummy data for recipes
// We realise that setRecipeDescription doesn't change the recipes
public class RecipeDbMock {
    // Dummy data
    private Recipe[] recipes = new Recipe[]{
            new Recipe("Kjúklinga Tikka Masala", "Þetta er kjúklingur"),
            new Recipe("Cheerios", "Settu morgunkornið fyrst og síðan mjólkina"),
            new Recipe("Spagettí", "Nennir einhver að nota skeið?"),
            new Recipe("Ristað brauð", "Maður segir brauðrist en ekki ristavél")
    };

    public RecipeDbMock() {
    }

    public Recipe getRecipeById(int id) {
        return recipes[id];
    }

    public void setRecipeTitle(int id, String title) {
        recipes[id].setTitle(title);
    }

    public void setRecipeDescription(int id, String description) {
        recipes[id].setDescription(description);
    }
}
