import org.sql2o.*;
import java.util.List;

public class Food extends Recipe {
  public static final String DATABASE_TYPE = "Food";

  public Food (String name) {
    this.name = name;
    type = DATABASE_TYPE;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipe (name, rating, type) VALUES (:name, :rating, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("rating", this.rating)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipe SET name = :name, rating = :rating WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("rating", this.rating)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Food> all() {
    String sql = "SELECT * FROM recipe WHERE type = :type ORDER BY rating";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(Food.class);
    }
  }

  public static Food find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipe WHERE id = :id";
      Food food = con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(Food.class);
      return food;
    }
  }

  public static List<Food> searchByIngredient(String search) {
    String sql = "SELECT recipe.* FROM recipe " +
    "LEFT JOIN ingredients_instructions ON recipe.Id = ingredients_instructions.recipe_Id " +
    "WHERE recipe.type = :type AND ingredients_instructions.name ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("search", ".*" + search + ".*")
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(Food.class);
    }
  }

  public static List<Food> searchByTag(String search) {
    String sql = "SELECT recipe.* FROM recipe " +
      "LEFT JOIN tag_recipe ON recipe.Id = tag_recipe.recipe_Id " +
      "LEFT JOIN tag ON tag_recipe.tag_id = tag.id " +
      "WHERE recipe.type = :type AND tag.name ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("search", ".*" + search + ".*")
        .addParameter("type", DATABASE_TYPE)
        .executeAndFetch(Food.class);
    }
  }

}
