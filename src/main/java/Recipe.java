import org.sql2o.*;
import java.util.List;

public abstract class Recipe {
  public int id;
  public String type;
  public String name;
  public int rating;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
      con.createQuery(sql)
        .addParameter("id", this.getId())
        .executeUpdate();
    }
  }

  public List<Object> getAllInstructionsAndIngredients() {
    List<Object> foundObjects = new List<Object>();
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
    foundObjects.sort();
    return foundObjects;
  }

}
