import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public class DrinkTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void save_savesIntoDatabase_true() {
    Drink drinkOne = new Drink ("eggnogg", "tumbler");
    drinkOne.save();
    assertEquals(true, Drink.all().get(0).equals(drinkOne));
  }

  @Test
  public void all_returnsAllDrinks_true () {
    Drink drinkOne = new Drink("eggnogg", "tumbler");
    drinkOne.save();
    Drink drinkTwo = new Drink("wine", "wineglass");
    drinkTwo.save();
    assertEquals(true, Drink.all().get(0).equals(drinkOne));
    assertEquals(true, Drink.all().get(1).equals(drinkTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Drink drinkOne = new Drink("eggnogg", "tumbler");
    drinkOne.save();
    Drink savedDrink = Drink.find(drinkOne.getId());
    assertEquals(true, drinkOne.equals(savedDrink));
  }

  @Test
  public void timestamp_matchesSavedTimeStamp_true () {
    Drink drinkOne = new Drink("soda", "bottle");
    drinkOne.save();
    Timestamp savedDrinkOne = Drink.find(drinkOne.getId()).getCreated();
    Timestamp now = new Timestamp (new Date().getTime());
    assertEquals (DateFormat.getDateTimeInstance().format(now), DateFormat.getDateTimeInstance().format(savedDrinkOne));
  }
}
