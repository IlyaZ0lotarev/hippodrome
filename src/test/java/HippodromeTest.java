import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    @Test
    public void nullHorsesException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );

        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void emptyHorsesException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(Collections.emptyList())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorsesReturns() {
        List<Horse> listHorses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            listHorses.add(new Horse("Horse #" + i, 2.0, 0.5));
        }
        Hippodrome hippodrome = new Hippodrome(listHorses);
        List<Horse> returnedHorses = hippodrome.getHorses();

        assertEquals(30, returnedHorses.size());

        for (int i = 0; i < 30; i++) {
            assertEquals(listHorses.get(i), returnedHorses.get(i));
        }
    }

    @Test
    public void moveForAllHorses() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinnerReturnsHorseWithMaxDistance() {
        Horse horse1 = new Horse("Spirit", 5.0, 10.0);
        Horse horse2 = new Horse("Breeze", 3.0, 30.0);
        Horse horse3 = new Horse("Storm", 7.0, 20.0);

        List<Horse> horses = List.of(horse1, horse2, horse3);

        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        assertSame(horse2, winner);
    }


}
