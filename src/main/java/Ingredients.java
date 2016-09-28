import org.sql2o.*;
import java.util.List;

public class Ingredients extends RecipeItems {
  private float qty;
  public static final String DATABASE_TYPE = "Ingredient";

  public Ingredients (String name, float qty) {
    this.name = name;
    this.qty = qty;
    type = DATABASE_TYPE;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients_instructions (name, qty, type) VALUES (:name, :qty, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("qty", this.qty)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE ingredients_instructions SET name = :name, qty = :qty WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("qty", this.qty)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List <Ingredients> all() {
    String sql = "SELECT * FROM ingredients_instructions WHERE type = :type";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("type", this.type)
        .executeAndFetch(Ingredients.class);
    }
  }

  public static Ingredients find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ingredients_instructions WHERE id = :id";
      Ingredients ingredients = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Ingredients.class);
      return ingredients;
    }
  }


}
