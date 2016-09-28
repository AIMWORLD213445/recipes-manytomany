import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipes_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipeQuery = "DELETE FROM recipe *;";
      String deleteTagQuery = "DELETE FROM tag *;";
      String deleteTagRecipeQuery = "DELETE FROM tag_recipe *;";
      String deleteIngredientsInstructionsQuery = "DELETE FROM ingredients_instructions *;";
      con.createQuery(deleteRecipeQuery).executeUpdate();
      con.createQuery(deleteTagQuery).executeUpdate();
      con.createQuery(deleteTagRecipeQuery).executeUpdate();
      con.createQuery(deleteIngredientsInstructionsQuery).executeUpdate();
    }
  }
}
