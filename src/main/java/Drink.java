import org.sql2o.*;
import java.util.List;

public class Drink extends Recipe {
  private String glassType;
  public static final String DATABASE_TYPE = "Drink";

  public Drink (String name) {
    this.name = name;
    type = DATABASE_TYPE;
  }

  public String getGlassType() {
    return this.glassType;
  }

  public void setGlassType(String glassType) {
    this.glassType = glassType;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipe (name, rating, glassType, type) VALUES (:name, :rating, :glassType, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipe SET name = :name, rating = :rating, glassType = :glassType WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("rating", this.rating)
        .addParameter("glassType", this.glassType);
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List <Drink> all() {
    String sql = "SELECT * FROM recipe WHERE type = :type ORDER BY rating";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addParameter("type", this.type)
        .executeAndFetch(Drink.class);
    }
  }

  public static Drink find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipe WHERE id = :id";
      Drink drink = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Drink.class);
      return drink;
    }
  }

  public static List<Drink> searchByIngredient(String search) {
    String sql = "SELECT recipe.* FROM recipe LEFT JOIN ingredients_instructions ON recipe.Id = ingredients_instructions.recipe_Id WHERE recipe.type = :type AND ingredients_instructions.name ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("search", ".*" + search ".*")
        .addParameter("type", this.type)
        .executeAndFetch(Drink.class);
    }
  }

  public static List<Drink> searchByTag(String search) {
    String sql = "SELECT recipe.* FROM recipe LEFT JOIN tag_recipe ON recipe.Id = tag_recipe.recipe_Id WHERE recipe.type = :type AND tag.name ~* :search";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("search", ".*" + search ".*")
        .addParameter("type", this.type)
        .executeAndFetch(Drink.class);
    }
  }


}
