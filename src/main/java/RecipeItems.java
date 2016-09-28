import org.sql2o.*;
import java.util.List;

public abstract class RecipeItems implements Comparable<Name> {
  public int id;
  public String name;
  public String type;
  public int recipeId;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM ingredients_instructions WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public int compareTo(RecipeItem otherItem) {
    return this.getId().compareTo(otherItem.getId());
}

}
