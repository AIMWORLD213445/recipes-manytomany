import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_savesIntoDatabase_true() {
    Tag tagOne = new Tag ("awesome");
    tagOne.save();
    assertEquals(true, Tag.all().get(0).equals(tagOne));
  }

  @Test
  public void all_returnsAllTags_true () {
    Tag tagOne = new Tag("awesome");
    tagOne.save();
    Tag tagTwo = new Tag("awful");
    tagTwo.save();
    assertEquals(true, Tag.all().get(0).equals(tagOne));
    assertEquals(true, Tag.all().get(1).equals(tagTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Tag tagOne = new Tag("awesome");
    tagOne.save();
    Tag savedTag = Tag.find(tagOne.getId());
    assertEquals(true, tagOne.equals(savedTag));
  }

  @Test (expected = IllegalArgumentException.class)
  public void tag_tagNameThrowsExceptionOverMaxChars(){
    Tag tagOne = new Tag("This is a really great recipe for christmas dinner");
  }

  @Test (expected = IllegalArgumentException.class)
  public void tag_setNameThrowsExceptionOverMaxChars(){
    Tag tagOne = new Tag("awesome");
    tagOne.setName("This is a really great recipe for christmas dinner");
  }
}
