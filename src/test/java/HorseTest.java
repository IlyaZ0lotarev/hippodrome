import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class HorseTest {

    static Stream<Arguments> moveTestParameters() {
        return Stream.of(
                Arguments.of(0.0, 10.0, 0.5, 5.0),      // 0 + 10 * 0.5 = 5.0
                Arguments.of(5.0, 20.0, 0.2, 9.0),      // 5 + 20 * 0.2 = 9.0
                Arguments.of(10.0, 5.0, 0.9, 14.5),     // 10 + 5 * 0.9 = 14.5
                Arguments.of(100.0, 2.5, 0.6, 101.5)    // 100 + 2.5 * 0.6 = 101.5
        );
    }

    @Test
    public void nameNullException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, 3.1, 7.5)
        );

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n", " \t\n "})
    public void nameBlankException(String name) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(name, 3.1, 7.5)
        );

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void speedNegativeException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Breeze", -5.3, 10)
        );

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void distanceNegativeException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse("Breeze", 3.3, -6.4)
        );

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        Horse horse = new Horse("Storm", 2.5, 7.7);
        assertEquals("Storm", horse.getName());
    }

    @Test
    public void getSpeed() {
        Horse horse = new Horse("Storm", 2.5, 7.7);
        assertEquals(2.5, horse.getSpeed());
    }

    @Test
    public void getDistance() {
        Horse horse = new Horse("Storm", 2.5, 7.7);
        assertEquals(7.7, horse.getDistance());
    }

    @Test
    public void getDistanceWithTwoParameterConstructor() {
        Horse horse = new Horse("Storm", 2.5);
        assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveCallsGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Storm", 3.0, 6.2);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @MethodSource("moveTestParameters")
    public void moveCalculatesDistanceCorrectly(double initDistance, double speed, double randomValue, double resultDistance) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            Horse horse = new Horse("Storm", speed, initDistance);
            horse.move();

            assertEquals(resultDistance, horse.getDistance());
        }
    }
}
