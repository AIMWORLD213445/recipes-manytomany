import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Collections;

public abstract class Recipe {
  public int id;
  public String type;
  public String name;
  public int rating;
  public Timestamp created;
  public final static int MAX_NAME_LENGTH = 20;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name.length() > MAX_NAME_LENGTH){
          throw new IllegalArgumentException("Recipe names must be 20 characters or less.");
      }
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public Timestamp getCreated() {
    return created;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipe WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addTag(Tag tag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tag_recipe (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
      con.createQuery(sql)
        .addParameter("recipe_id", this.getId())
        .addParameter("tag_id", tag.getId())
        .executeUpdate();
    }
  }

  public void removeTag(Tag tag){
    try(Connection con = DB.sql2o.open()){
      String joinRemovalQuery = "DELETE FROM tag_recipe WHERE tag_id = :tagId AND recipe_id = :recipeId;";
      con.createQuery(joinRemovalQuery)
        .addParameter("tagId", tag.getId())
        .addParameter("recipeId", this.getId())
        .executeUpdate();
    }
  }

  public List<Tag> getAllTags() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT tag.* FROM tag LEFT JOIN tag_recipe ON tag_recipe.tag_id = tag.id WHERE tag_recipe.recipe_id = :id;";
      return con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeAndFetch(Tag.class);
    }
  }

  public List<RecipeItems> getAllInstructionsAndIngredients() {
    List<RecipeItems> foundObjects = new ArrayList<RecipeItems>();
    try(Connection con = DB.sql2o.open()){
      String sqlInstructions = "SELECT ingredients_instructions.* FROM ingredients_instructions LEFT JOIN recipe ON recipe.id = ingredients_instructions.recipe_id WHERE recipe.Id = :id AND ingredients_instructions.type = ':type';";
      List<Instructions> foundInstructions = con.createQuery(sqlInstructions)
        .addParameter("id", this.getId())
        .addParameter("type", Instructions.DATABASE_TYPE)
        .executeAndFetch(Instructions.class);
      foundObjects.addAll(foundInstructions);

      String sqlIngredients = "SELECT ingredients_instructions.* FROM ingredients_instructions LEFT JOIN recipe ON recipe.id = ingredients_instructions.recipe_id WHERE recipe.Id = :id AND ingredients_instructions.type = ':type';";
      List<Ingredients> foundIngredients = con.createQuery(sqlIngredients)
        .addParameter("id", this.getId())
        .addParameter("type", Ingredients.DATABASE_TYPE)
        .executeAndFetch(Ingredients.class);
      foundObjects.addAll(foundIngredients);
    }
    Collections.sort(foundObjects);
    return foundObjects;
  }

  @Override
   public boolean equals(Object otherRecipe) {
     if (!(otherRecipe instanceof Recipe)) {
       return false;
     } else {
       Recipe newRecipe = (Recipe) otherRecipe;
       return this.getName().equals(newRecipe.getName()) &&
              this.getRating() == newRecipe.getRating();
   }
 }
}
