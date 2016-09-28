import org.sql2o.*;
import java.util.List;

public class Ingredients extends RecipeItems {
  public static final String DATABASE_TYPE = "Instructions";

  public Instructions (String name) {
    this.name = name;
    type = DATABASE_TYPE;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients_instructions (name, type) VALUES (:name, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE ingredients_instructions SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List <Instructions> all() {
    String sql = "SELECT * FROM ingredients_instructions WHERE type = :type";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("type", this.type)
        .executeAndFetch(Instructions.class);
    }
  }

  public static Instructions find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM ingredients_instructions WHERE id = :id";
      Instructions instructions = con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(Instructions.class);
      return instructions;
    }
  }


}
