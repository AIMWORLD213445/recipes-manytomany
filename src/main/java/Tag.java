import org.sql2o.*;
import java.util.List;

public class Tag {
  int id;
  String name;
  private final static int MAX_TAG_LENGTH = 20;


  public Tag(String name) {
    if (name.length() > MAX_TAG_LENGTH){
          throw new IllegalArgumentException("Tags must be 20 characters or less.");
      }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name.length() > MAX_TAG_LENGTH){
          throw new IllegalArgumentException("Tags must be 20 characters or less.");
      }
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tag (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tag SET name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tag";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .throwOnMappingFailure(false)
        .executeAndFetch(Tag.class);
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tag WHERE id = :id";
      Tag tag = con.createQuery(sql)
        .throwOnMappingFailure(false)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tag WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

    @Override
     public boolean equals(Object otherTag) {
       if (!(otherTag instanceof Tag)) {
         return false;
       } else {
        Tag newTag = (Tag) otherTag;
         return this.getName().equals(newTag.getName());
     }
   }

}
