import enums.Action;
import enums.Strategy;
import exception.InvalidInputTypeException;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RockPaperScissorTest {

    @Test
    public void testGetValidIntegerInput() {
        // Arrange
        String input = "8";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scannerMock = new Scanner(in);

        // Act
        int result = RockPaperScissor.getValidIntegerInput(scannerMock, "Enter a number: ");

        // Assert
        assertEquals(8, result);
    }

    @Test
    public void testGetValidIntegerInputInvalidInput() {
        // Arrange
        String input = "invalid";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scannerMock = new Scanner(in);

        // Act & Assert
        assertThrows(InvalidInputTypeException.class, () ->
                RockPaperScissor.getValidIntegerInput(scannerMock, "Enter a number: "));
    }

    @Test
    public void testGetPlayerStrategy() {
        // Arrange
        String input = "1\n2\n3\n4\n5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scannerMock = new Scanner(in);

        // Act & Assert
        assertEquals(Strategy.RANDOM, RockPaperScissor.getPlayerStrategy(scannerMock, 1));
        assertEquals(Strategy.ALWAYS_ROCK, RockPaperScissor.getPlayerStrategy(scannerMock, 2));
        assertEquals(Strategy.ALWAYS_PAPER, RockPaperScissor.getPlayerStrategy(scannerMock, 3));
        assertEquals(Strategy.ALWAYS_SCISSORS, RockPaperScissor.getPlayerStrategy(scannerMock, 4));
        assertEquals(Strategy.ALTERNATE_ROCK_PAPER, RockPaperScissor.getPlayerStrategy(scannerMock, 5));
    }

    @Test
    public void testGetPlayerStrategyInvalidInput() {
        // Arrange
        String input = "invalid\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scannerMock = new Scanner(in);

        // Act & Assert
        assertThrows(InvalidInputTypeException.class, () ->
                RockPaperScissor.getPlayerStrategy(scannerMock, 1));
    }

    @Test
   public void testGetPlayerMove() {
        Strategy randomStrategy = Strategy.RANDOM;
        Strategy alwaysRockStrategy = Strategy.ALWAYS_ROCK;
        Strategy alwaysPaperStrategy = Strategy.ALWAYS_PAPER;
        Strategy alwaysScissorsStrategy = Strategy.ALWAYS_SCISSORS;
        Strategy alternateStrategy = Strategy.ALTERNATE_ROCK_PAPER;

        // Test for RANDOM strategy
        Action randomAction = RockPaperScissor.getPlayerMove(randomStrategy, 0);
        assertNotNull(randomAction);

        // Test for ALWAYS_ROCK strategy
        Action alwaysRockAction = RockPaperScissor.getPlayerMove(alwaysRockStrategy, 0);
        assertEquals(Action.ROCK, alwaysRockAction);

        // Test for ALWAYS_PAPER strategy
        Action alwaysPaperAction = RockPaperScissor.getPlayerMove(alwaysPaperStrategy, 0);
        assertEquals(Action.PAPER, alwaysPaperAction);

        // Test for ALWAYS_SCISSORS strategy
        Action alwaysScissorsAction = RockPaperScissor.getPlayerMove(alwaysScissorsStrategy, 0);
        assertEquals(Action.SCISSORS, alwaysScissorsAction);

        // Test for ALTERNATE_ROCK_PAPER_SCISSORS strategy
        Action alternateAction1 = RockPaperScissor.getPlayerMove(alternateStrategy, 1);
        assertEquals(Action.ROCK, alternateAction1);
        Action alternateAction2 = RockPaperScissor.getPlayerMove(alternateStrategy, 2);
        assertEquals(Action.PAPER, alternateAction2);
    }

    @Test
    public void testGetResult() {
        assertEquals(0, RockPaperScissor.getResult(Action.ROCK, Action.ROCK)); // Tie
        assertEquals(1, RockPaperScissor.getResult(Action.ROCK, Action.SCISSORS)); // Player 1 wins
        assertEquals(-1, RockPaperScissor.getResult(Action.PAPER, Action.SCISSORS)); // Player 2 wins
    }
}

