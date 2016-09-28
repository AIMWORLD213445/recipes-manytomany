import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.Arrays;

public class FoodTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_savesIntoDatabase_true() {
    Food foodOne = new Food ("pizza");
    foodOne.save();
    assertEquals(true, Food.all().get(0).equals(foodOne));
  }

  @Test
  public void all_returnsAllFoods_true () {
    Food foodOne = new Food("pizza");
    foodOne.save();
    Food foodTwo = new Food("steak");
    foodTwo.save();
    assertEquals(true, Food.all().get(0).equals(foodOne));
    assertEquals(true, Food.all().get(1).equals(foodTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Food foodOne = new Food("pizza");
    foodOne.save();
    Food savedFood = Food.find(foodOne.getId());
    assertEquals(true, foodOne.equals(savedFood));
  }

  @Test
  public void searchByIngredient_returnsAllRecipes_true () {
    Food foodOne = new Food("pizza");
    foodOne.save();
    Food foodTwo = new Food("fish");
    foodTwo.save();
    Food foodThree = new Food("Steak");
    foodThree.save();
    Ingredients pepperoni = new Ingredients("Pepperoni", 1, foodOne.getId());
    pepperoni.save();
    Ingredients pepper = new Ingredients("Pepper", 1, foodTwo.getId());
    pepper.save();
    List<Food> foundFood = Food.searchByIngredient("pep");
    assertEquals(true, foundFood.contains(foodOne));
    assertEquals(true, foundFood.contains(foodTwo));
    assertEquals(false, foundFood.contains(foodThree));
  }

  @Test
  public void searchByTag_returnsAllTaggedRecipes_true () {
    Food foodOne = new Food("pizza");
    foodOne.save();
    Tag testTag = new Tag("Awesome food");
    testTag.save();
    foodOne.addTag(testTag);
    List<Food> foundFood = Food.searchByTag("Awesome");
    assertEquals(true, foundFood.contains(foodOne));
  }
}
