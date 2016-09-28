import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class InstructionsTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void save_savesIntoDatabase_true() {
    Instructions instructionOne = new Instructions ("Do a thing");
    instructionOne.save();
    assertEquals(true, Instructions.all().get(0).equals(instructionOne));
  }

  @Test
  public void all_returnsAllInstructionss_true () {
    Instructions instructionOne = new Instructions ("Do a thing");
    instructionOne.save();
    Instructions instructionTwo = new Instructions("Do another thing");
    instructionTwo.save();
    assertEquals(true, Instructions.all().get(0).equals(instructionOne));
    assertEquals(true, Instructions.all().get(1).equals(instructionTwo));
  }

  @Test
  public void equals_recognizesSameValues_true () {
    Instructions instructionOne = new Instructions ("Do a thing");
    instructionOne.save();
    Instructions savedInstruction = Instructions.find(instructionOne.getId());
    assertEquals(true, instructionOne.equals(savedInstruction));
  }
}
