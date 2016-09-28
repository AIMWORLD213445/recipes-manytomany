import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class IngredientsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_savesIntoDatabase_true() {
    Food testRecipe = new Food("Eggs");
    testRecipe.save();
    Ingredients ingredientOne = new Ingredients ("egg", 1, testRecipe.getId());
    ingredientOne.save();
    assertEquals(true, Ingredients.all().get(0).equals(ingredientOne));
  }

  @Test
  public void all_returnsAllIngredients_true () {
    Food testRecipe = new Food("Eggs");
    testRecipe.save();
    Ingredients ingredientOne = new Ingredients ("egg", 1, testRecipe.getId());
    ingredientOne.save();
    Ingredients ingredientTwo = new Ingredients("cheese", 0.5, testRecipe.getId());
    ingredientTwo.save();
    assertEquals(true, Ingredients.all().get(0).equals(ingredientOne));
    assertEquals(true, Ingredients.all().get(1).equals(ingredientTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Food testRecipe = new Food("Eggs");
    testRecipe.save();
    Ingredients ingredientOne = new Ingredients ("egg", 1, testRecipe.getId());
    ingredientOne.save();
    Ingredients savedIngredient = Ingredients.find(ingredientOne.getId());
    assertEquals(true, ingredientOne.equals(savedIngredient));
  }
}
