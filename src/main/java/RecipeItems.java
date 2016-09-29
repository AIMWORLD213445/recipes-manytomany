import org.sql2o.*;
import java.util.List;

public abstract class RecipeItems implements Comparable<RecipeItems> {
  public int id;
  public String name;
  public String type;
  public int recipeId;
  public final static int MAX_NAME_LENGTH = 200;


  public String getName() {
    return name;
  }

  public int getId() {
    return this.id;
  }

  public void setName(String name) {
    if (name.length() > MAX_NAME_LENGTH){
          throw new IllegalArgumentException("Ingredients/Instructions must be 200 characters or less.");
      }
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

  @Override
  public int compareTo(RecipeItems otherItem) {
    return this.getId() - otherItem.getId();
}

  @Override
   public boolean equals(Object otherRecipeItems) {
     if (!(otherRecipeItems instanceof RecipeItems)) {
       return false;
     } else {
       RecipeItems newRecipeItems = (RecipeItems) otherRecipeItems;
       return this.getName().equals(newRecipeItems.getName());
   }
  }
}
