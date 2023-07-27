import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class HorseTest {
    @Test
    void whenHorseConstructorFirstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 20, 10));
    }

    @Test
    void whenHorseConstructorFirstNullMessage() {
        try {
            new Horse(null, 20, 10);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "  \t ", "\s"})
    void whenHorseConstructorFirstNullOrSpace(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 20, 10));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    @Test
    void whenHorseConstructorSecondIsNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 10));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    void whenHorseConstructorThirdIsNegative() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("name", 11, -5));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    void getNameReturnParameter() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("fast", 10, 20);
//        Field name = Horse.class.getDeclaredField("name");
//        name.setAccessible(true);
//        String nameValue = (String) name.get(horse);
        assertEquals("fast", horse.getName());
    }

    @Test
    void getSpeedReturnParameter() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("fast", 10, 20);
        assertEquals(10, horse.getSpeed());
    }

    @Test
    void getDistanceReturnParameter() {
        Horse horse = new Horse("fast", 10, 20);
        assertEquals(20, horse.getDistance());
    }

    @Test
    void getDistanceReturnParameterIfNoDistance() {
        Horse horseWithOutDistanceField = new Horse("slow", 15);
        assertEquals(0, horseWithOutDistanceField.getDistance());
    }

    @Test
    void testMoveMethodRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Murcielago", 28, 16);
            horse.move();
        mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.3, 0.4, 0.5, 2.0})
    void methodMoveReturnValidValue(double randomValue) {

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Murcielago", 28, 16);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            horse.move();

            Assertions.assertEquals(16+28*randomValue, horse.getDistance());
        }
    }

//
//            double random = 0.35;
//            double expected = 27.48;
//            mockStatic(Math.class);
//            when(Math.random()).thenReturn(random);
//            horse.move();
//            assertEquals(expected, horse.getDistance());

}



